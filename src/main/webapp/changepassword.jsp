<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<%!
  String SENHA                  = "senha";
  String NOVA_SENHA             = "novaSenha";
  String CONFIRMACAO_NOVA_SENHA = "confirmacaoNovaSenha";
%>

<%
  // pega os parâmetros esperados
  String senha     = request.getParameter(SENHA);
  if (senha == null)
    senha = "";
  String novaSenha = request.getParameter(NOVA_SENHA);
  if (novaSenha == null)
    novaSenha = "";

  // nossa senha foi alterada?
  boolean passwordChanged = false;
  // se recebemos os parâmetros...tenta trocar a senha
  if (!senha.equals("") && !novaSenha.equals("")) {
    // altera a senha
    facade.usuario().changePassword(facade.getLoggedUser().getNome(), senha, novaSenha);
    // a senha foi alterada
    passwordChanged = true;
  }; // if
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Alterar Senha</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  function formSubmit() {
    // controles
    senha     = formChangePassword.<%=SENHA%>;
    novaSenha = formChangePassword.<%=NOVA_SENHA%>;
    confirmacaoNovaSenha = formChangePassword.<%=CONFIRMACAO_NOVA_SENHA%>;
    // se não informou a senha atual...avisa
    if (senha.value == "") {
      alert("Informe a senha atual.");
      senha.focus();
      return;
    }
    // se não informou a nova senha...avisa
    if (novaSenha.value == "") {
      alert("Informe a nova senha.");
      novaSenha.focus();
      return;
    }
    // se não confirmou a nova senha...avisa
    if (confirmacaoNovaSenha.value != novaSenha.value) {
      alert("Confirme a nova senha.");
      confirmacaoNovaSenha.value = "";
      confirmacaoNovaSenha.focus();
      return;
    }
    // submete o formulário
    formChangePassword.submit();
  }

</script>

<!--conteúdo-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
    <h2>Alterar Senha</h2>
      <%// se alteramos a senha
        if (passwordChanged) {
      %>
        <b>Sua senha foi alterada com sucesso!</b>
      <%}
        // se não alteramos a senha
        else {%>
        <!-- formulário de alteração de senha -->
        <form id="formChangePassword" action="controller" method="POST">
          <input type="hidden" name="<%=Controller.ACTION%>" value="<%=Controller.ACTION_CHANGE_PASSWORD%>">
          <b>Informe a senha atual e confirme sua nova senha.</b>
          <br>
          <br>
          <table class="BtnFace">
            <tr>
              <td>Senha atual</td>
              <td><input type="password" maxchars="8" name="<%=SENHA%>" value=""></td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td>Nova senha</td>
              <td><input type="password" maxchars="8" name="<%=NOVA_SENHA%>" value=""></td>
            </tr>
            <tr>
              <td>Confirmação</td>
              <td><input type="password" maxchars="8" name="<%=CONFIRMACAO_NOVA_SENHA%>" value=""></td>
            </tr>
          </table>
          <script>formChangePassword.<%=SENHA%>.focus()</script>
        </form>
        <button onclick="formSubmit()">Alterar senha</button>&nbsp;&nbsp;&nbsp;
      <%}%>
    </td>
  </tr>
</table>

</body>
</html>
