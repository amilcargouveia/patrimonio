
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
  // nossa instância de Fornecedor
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
    // info preenchido na página
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
    // se está salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
      fornecedor.insert(fornecedorInfo);
    // se não está salvando um novo...atualiza
    else
      fornecedor.update(fornecedorInfo);
    // volta para a página de consulta
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

  // controla a submissão do form para evitar
  // multiplicidade de requisições
  formSubmited = false;

  function post() {
    // se o form já foi submetido...aguarde
    if (formSubmited) {
      alert("Aguarde enquanto a operação é concluída.");
      return;
    } // if
    // se não confirmou...dispara
    if (!confirm("Deseja mesmo salvar o(a) Fornecedor?"))
      return;
    // valida os dados
    if (!validate(formFornecedor.<%=Fornecedor.FIELD_CODIGO%>, "Código"))
       return;
    // altera o comando
    formFornecedor.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formulário
    formSubmited = true;
    formFornecedor.submit();
  }

  function validate(input, title) {
    // se não informou o valor
    if (input.value == "") {
      // põe o foco
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

<!--conteúdo-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- título -->
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
          <!-- campo Endereço -->
          <tr>
            <td>Endereço</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_ENDERECO%>"
                     value="<%=fornecedorInfo.getEndereco()%>"
                     style="width:250px;">
            </td>
          </tr>
          <!-- campo Município -->
          <tr>
            <td>Município</td>
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
          <!-- campo Inscrição Estadual -->
          <tr>
            <td>Inscrição Estadual</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_INSCRICAO_ESTADUAL%>"
                     value="<%=fornecedorInfo.getInscricaoEstadual()%>">
            </td>
          </tr>
          <!-- campo Inscrição Muncipal -->
          <tr>
            <td>Inscrição Muncipal</td>
            <td>
              <input type="text"
                     name="<%=Fornecedor.FIELD_INSCRICAO_MUNCIPAL%>"
                     value="<%=fornecedorInfo.getInscricaoMuncipal()%>">
            </td>
          </tr>
        </table>
        <br>
        <!--botões de comando-->
        <button onclick="post();">Salvar</button>
      </form>
      <button id="buttonVoltar"
              onclick="javascript:history.back();">Voltar</button>
    </td>
  </tr>
</table>

</body>
</html>
