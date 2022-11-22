
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
  // nossa instância de Setor
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
    // info preenchido na página
    setorInfo =
      new SetorInfo(
            Integer.parseInt(request.getParameter(Setor.FIELD_CODIGO)),
            request.getParameter(Setor.FIELD_DESCRICAO)
          );
    // se está salvando um novo...inclui
    if (request.getParameter(INSERT) != null)
      setor.insert(setorInfo);
    // se não está salvando um novo...atualiza
    else
      setor.update(setorInfo);
    // volta para a página de consulta
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
    if (!confirm("Deseja mesmo salvar o(a) Setor?"))
      return;
    // valida os dados
    if (!validate(formSetor.<%=Setor.FIELD_CODIGO%>, "Código"))
       return;
    // altera o comando
    formSetor.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formulário
    formSubmited = true;
    formSetor.submit();
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
          <!-- campo Descrição -->
          <tr>
            <td>Descrição</td>
            <td>
              <input type="text"
                     name="<%=Setor.FIELD_DESCRICAO%>"
                     value="<%=setorInfo.getDescricao()%>"
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
