
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_SETOR = "setor";
  String ACTION_SETOR_CADASTRO = "setorCadastro";
  String CLASS_SETOR = "multiwork.entity.Setor";
  String INSERT = "INSERT";
  String POST   = "POST";
%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa inst�ncia de Setor
  Setor setor = (Setor)facade.getEntity(CLASS_SETOR);
  // SetorInfo para editar
  SetorInfo setorInfo = null;
  // se quer incluir...
  if (command.equals(INSERT))
    // info em branco
    setorInfo =
      new SetorInfo(
            0,
            ""
          );
  // se quer salvar...
  else if (command.equals(POST)) {
    // info preenchido na p�gina
    setorInfo =
      new SetorInfo(
            Integer.parseInt(request.getParameter(Setor.FIELD_CODIGO)),
            request.getParameter(Setor.FIELD_DESCRICAO)
          );
    // se est� salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
      setor.insert(setorInfo);
    // se n�o est� salvando um novo...atualiza
    else
      setor.update(setorInfo);
    // volta para a p�gina de consulta
    response.sendRedirect("controller?" + Controller.ACTION + "=" + ACTION_SETOR);
    return;
  }
  // se quer editar...
  else {
    // info do registro para editar
    setorInfo = setor.getByCodigo(Integer.parseInt(request.getParameter(Setor.FIELD_CODIGO)));
  } // if
%>

<html>
<head>
<title>Setor</title>
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
    if (!confirm("Deseja mesmo salvar o(a) Setor?"))
      return;
    // valida os dados
    if (!validate(formSetor.<%=Setor.FIELD_CODIGO%>, "C�digo"))
       return;
    // altera o comando
    formSetor.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formul�rio
    formSubmited = true;
    formSetor.submit();
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
      <h2>Setor</h2>
      <!-- form -->
      <form name="formSetor" action="controller">
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_SETOR_CADASTRO%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <input type="hidden"
               name="<%=Setor.FIELD_CODIGO%>"
               value="<%=setorInfo.getCodigo()%>">
        <%if (command.equals(INSERT)) {%>
          <input type="hidden"
                 name="<%=INSERT%>"
                 value="true">
        <%} // if%>
        <!-- dados de Setor -->
        <table class="BtnFace">
          <!-- campo Descri��o -->
          <tr>
            <td>Descri��o</td>
            <td>
              <input type="text"
                     name="<%=Setor.FIELD_DESCRICAO%>"
                     value="<%=setorInfo.getDescricao()%>"
                     style="width:250px;">
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
