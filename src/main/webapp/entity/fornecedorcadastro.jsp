
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_FORNECEDOR = "fornecedor";
  String ACTION_FORNECEDOR_CADASTRO = "fornecedorCadastro";
  String CLASS_FORNECEDOR = "multiwork.entity.Fornecedor";
  String INSERT = "INSERT";
  String POST   = "POST";
%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa inst�ncia de Fornecedor
  Fornecedor fornecedor = (Fornecedor)facade.getEntity(CLASS_FORNECEDOR);
  // FornecedorInfo para editar
  FornecedorInfo fornecedorInfo = null;
  // se quer incluir...
  if (command.equals(INSERT))
    // info em branco
    fornecedorInfo =
      new FornecedorInfo(
            0,
            "",
            "",
            "",
            "",
            "",
            "",
            ""
          );
  // se quer salvar...
  else if (command.equals(POST)) {
    // info preenchido na p�gina
    fornecedorInfo =
      new FornecedorInfo(
            Integer.parseInt(request.getParameter(Fornecedor.FIELD_CODIGO)),
            request.getParameter(Fornecedor.FIELD_NOME),
            request.getParameter(Fornecedor.FIELD_ENDERECO),
            request.getParameter(Fornecedor.FIELD_MUNICIPIO),
            request.getParameter(Fornecedor.FIELD_ESTADO),
            request.getParameter(Fornecedor.FIELD_CNPJ),
            request.getParameter(Fornecedor.FIELD_INSCRICAO_ESTADUAL),
            request.getParameter(Fornecedor.FIELD_INSCRICAO_MUNCIPAL)
          );
    // se est� salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
      fornecedor.insert(fornecedorInfo);
    // se n�o est� salvando um novo...atualiza
    else
      fornecedor.update(fornecedorInfo);
    // volta para a p�gina de consulta
    response.sendRedirect("controller?" + Controller.ACTION + "=" + ACTION_FORNECEDOR);
    return;
  }
  // se quer editar...
  else {
    // info do registro para editar
    fornecedorInfo = fornecedor.getByCodigo(Integer.parseInt(request.getParameter(Fornecedor.FIELD_CODIGO)));
  } // if
%>

<html>
<head>
<title>Fornecedor</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  // controla a submiss�o do form para evitar
  // multiplicidade de requisi��es
  formSubmited = false;

  function post() {
    // se o form j� foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a opera��o � conclu�da.");
      return;
    } // if
    // se n�o confirmou...dispara
    if (!confirm("Deseja mesmo salvar o(a) Fornecedor?"))
      return;
    // valida os dados
    if (!validate(formFornecedor.<%=Fornecedor.FIELD_CODIGO%>, "C�digo"))
       return;
    // altera o comando
    formFornecedor.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formul�rio
    formSubmited = true;
    formFornecedor.submit();
  }

  function validate(input, title) {
    // se n�o informou o valor
    if (input.value == "") {
      // p�e o foco
      input.focus();
      // avisa
      alert("Informe o valor para " + title);
      // retorna falso
      return false;
    }
    // se informou...retorna ok
    else
      return true;
  }

</script>

<!--conte�do-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- t�tulo -->
      <h2>Fornecedor</h2>
      <!-- form -->
      <form name="formFornecedor" action="controller">
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_FORNECEDOR_CADASTRO%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <input type="hidden"
               name="<%=Fornecedor.FIELD_CODIGO%>"
               value="<%=fornecedorInfo.getCodigo()%>">
        <%if (command.equals(INSERT)) {%>
          <input type="hidden"
                 name="<%=INSERT%>"
                 value="true">
        <%} // if%>
        <!-- dados de Fornecedor -->
        <table class="BtnFace">
          <!-- campo Nome -->
          <tr>
            <td>Nome</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_NOME%>"
                     value="<%=fornecedorInfo.getNome()%>"
                     style="width:250px;">
            </td>
          </tr>
          <!-- campo Endere�o -->
          <tr>
            <td>Endere�o</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_ENDERECO%>"
                     value="<%=fornecedorInfo.getEndereco()%>"
                     style="width:250px;">
            </td>
          </tr>
          <!-- campo Munic�pio -->
          <tr>
            <td>Munic�pio</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_MUNICIPIO%>"
                     value="<%=fornecedorInfo.getMunicipio()%>"
                     style="width:185px;">
                <!-- campo Estado -->
                Estado
                <input type="text"
                     name="<%=Fornecedor.FIELD_ESTADO%>"
                     value="<%=fornecedorInfo.getEstado()%>"
                     style="width:25;">
            </td>
          <!-- campo CNPJ -->
          <tr>
            <td>CNPJ</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_CNPJ%>"
                     value="<%=fornecedorInfo.getCnpj()%>">
            </td>
          </tr>
          <!-- campo Inscri��o Estadual -->
          <tr>
            <td>Inscri��o Estadual</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_INSCRICAO_ESTADUAL%>"
                     value="<%=fornecedorInfo.getInscricaoEstadual()%>">
            </td>
          </tr>
          <!-- campo Inscri��o Muncipal -->
          <tr>
            <td>Inscri��o Muncipal</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_INSCRICAO_MUNCIPAL%>"
                     value="<%=fornecedorInfo.getInscricaoMuncipal()%>">
            </td>
          </tr>
        </table>
        <br>
        <!--bot�es de comando-->
        <button onclick="post();">Salvar</button>
      </form>
      <button id="buttonVoltar"
              onclick="javascript:history.back();">Voltar</button>
    </td>
  </tr>
</table>

</body>
</html>
