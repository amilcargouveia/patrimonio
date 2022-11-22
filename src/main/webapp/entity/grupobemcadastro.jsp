
<%@include file="../include/beans.jsp"%>
<%@page import="java.sql.*"%>

<%!
  // constantes
  String ACTION_GRUPO_BEM = "grupobem";
  String ACTION_GRUPO_BEM_CADASTRO = "grupobemCadastro";
  String CLASS_GRUPO_BEM = "multiwork.entity.GrupoBem";
  String INSERT = "INSERT";
  String POST   = "POST";
%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // nossa inst�ncia de Grupo Bem
  GrupoBem grupobem = (GrupoBem)facade.getEntity(CLASS_GRUPO_BEM);
  // GrupoBemInfo para editar
  GrupoBemInfo grupobemInfo = null;
  // se quer incluir...
  if (command.equals(INSERT))
    // info em branco
    grupobemInfo =
      new GrupoBemInfo(
        0,
        ""
      );
  // se quer salvar...
  else if (command.equals(POST)) {
    // info preenchido na p�gina
    grupobemInfo = new GrupoBemInfo(
                            Integer.parseInt(request.getParameter(GrupoBem.FIELD_CODIGO)),
                            request.getParameter(GrupoBem.FIELD_DESCRICAO)
          		);
    // se est� salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
      grupobem.insert(grupobemInfo);
    // se n�o est� salvando um novo...atualiza
    else
      grupobem.update(grupobemInfo);
    // volta para a p�gina de consulta
    response.sendRedirect("controller?" + Controller.ACTION + "=" + ACTION_GRUPO_BEM);
    return;
  }
  // se quer editar...
  else {
    // info do registro para editar
    grupobemInfo = grupobem.getByCodigo(Integer.parseInt(request.getParameter(GrupoBem.FIELD_CODIGO)));
  } // if
%>

<html>
<head>
<title>Grupo Bem</title>
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
    if (!confirm("Deseja mesmo salvar o(a) Grupo Bem?"))
      return;
    // valida os dados
    if (!validate(formGrupoBem.<%=GrupoBem.FIELD_CODIGO%>, "C�digo"))
       return;
    // altera o comando
    formGrupoBem.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formul�rio
    formSubmited = true;
    formGrupoBem.submit();
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
      <h2>Grupo Bem</h2>
      <!-- form -->
      <form name="formGrupoBem" action="controller">
        <input type="hidden"
               name="<%=Controller.ACTION%>"
               value="<%=ACTION_GRUPO_BEM_CADASTRO%>">
        <input type="hidden"
               name="<%=Controller.COMMAND%>"
               value="">
        <input type="hidden"
               name="<%=GrupoBem.FIELD_CODIGO%>"
               value="<%=grupobemInfo.getCodigo()%>">
        <%if (command.equals(INSERT)) {%>
          <input type="hidden"
                 name="<%=INSERT%>"
                 value="true">
        <%} // if%>
        <!-- dados de Grupo Bem -->
        <table class="BtnFace">
          <!-- campo Descri��o -->
          <tr>
            <td>Descri��o</td>
            <td>
              <input type="text"
                     name="<%=GrupoBem.FIELD_DESCRICAO%>"
                     value="<%=grupobemInfo.getDescricao()%>"
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
