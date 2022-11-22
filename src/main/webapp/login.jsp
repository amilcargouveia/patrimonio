<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<%!
  String LOGOFF             = "logoff";
  String MATRICULA_USUARIO  = "matriculaUsuario";
  String SENHA_USUARIO      = "senhaUsuario";
%>

<%
  // devemos efetuar logoff?
  if (request.getParameter(LOGOFF) != null)
    facade.setLoggedUser(null);

  // pega os parâmetros esperados
  String matriculaUsuario = request.getParameter(MATRICULA_USUARIO);
  if (matriculaUsuario == null)
    matriculaUsuario = "";
  String senhaUsuario = request.getParameter(SENHA_USUARIO);
  if (senhaUsuario == null)
    senhaUsuario = "";
  // se temos nome e senha...
  if (!matriculaUsuario.equals("") && !senhaUsuario.equals("")) {
    // tenta efetuar o logon
    UsuarioInfo loggedUser = facade.usuario().logon(matriculaUsuario, senhaUsuario);
    // guarda o usuário na fachada
    facade.setLoggedUser(loggedUser);
    // deixa o Controller redirecionar para o lugar certo
    forward("controller?" + Controller.ACTION + "=" + Controller.ACTION_HOME, pageContext);
  } // if
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Efetuar Logon</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body class="Background">

<table style="width:100%; height:100%;">
  <tr>
    <td>
      <form name="formLogin" action="controller" method="POST">
        <input type="hidden" name="<%=Controller.ACTION%>" value="<%=Controller.ACTION_LOGIN%>">
        <!--janela de login-->
        <table class="BtnFrame" style="width:400px;" align="center">
          <tr>
            <td>
              <table style="width:100%;">
                <tr>
                  <!--logomarca-->
                  <td class="Window" style="padding:8px 6px 8px 12px;" align="left">
                    <img src="images/login30x46.gif" align="absmiddle"><span style="font-size:26pt;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=facade.applicationInformation().getTitle()%></span>
                  </td>
                </tr>
                <tr>
                  <!--controles de dados-->
                  <td class="BtnFace" style="padding:14px 14px 14px 14px;">
                    <table class="BtnFace">
                      <tr>
                        <td>Nome do usuário</td>
                        <td><input type="text" name="<%=MATRICULA_USUARIO%>" value="<%=matriculaUsuario%>" style="width:240px;"></td>
                      </tr>
                      <tr>
                        <td>Senha</td>
                        <td><input type="password" name="<%=SENHA_USUARIO%>" style="width:240px;"></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td></td>
                        <td><input type="submit" value="OK" style="width:75px">
                            <input type="reset"  value="Limpar" style="width:75px"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
      </form>
    </td>
  </tr>
</table>

<script language="javascript">
  formLogin.<%=MATRICULA_USUARIO%>.focus();
</script>

</body>
</html>
