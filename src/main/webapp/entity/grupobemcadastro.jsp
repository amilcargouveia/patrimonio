
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
  // nossa instância de Grupo Bem
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
    // info preenchido na página
    grupobemInfo = new GrupoBemInfo(
                            Integer.parseInt(request.getParameter(GrupoBem.FIELD_CODIGO)),
                            request.getParameter(GrupoBem.FIELD_DESCRICAO)
          		);
    // se está salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
      grupobem.insert(grupobemInfo);
    // se não está salvando um novo...atualiza
    else
      grupobem.update(grupobemInfo);
    // volta para a página de consulta
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
    if (!confirm("Deseja mesmo salvar o(a) Grupo Bem?"))
      return;
    // valida os dados
    if (!validate(formGrupoBem.<%=GrupoBem.FIELD_CODIGO%>, "Código"))
       return;
    // altera o comando
    formGrupoBem.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formulário
    formSubmited = true;
    formGrupoBem.submit();
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
          <!-- campo Descrição -->
          <tr>
            <td>Descrição</td>
            <td>
              <input type="text"
                     name="<%=GrupoBem.FIELD_DESCRICAO%>"
                     value="<%=grupobemInfo.getDescricao()%>"
                     style="width:250px;">
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
