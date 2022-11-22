<%@include file="../include/beans.jsp"%>
<%@include file="../include/utils.jsp"%>

<%!
  // constantes
  String INSERT = "INSERT";
  String POST   = "POST";
%>

<%
  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";

  // Usuário
  UsuarioInfo usuario = null;
  // se quer incluir...
  if (command.equals(INSERT))
    // info em branco
    usuario = new UsuarioInfo(0, "", "", 0);
  // se quer salvar...
  else if (command.equals(POST)) {
    // info preenchido na página
    int    codigo = Integer.parseInt(request.getParameter(Usuario.FIELD_CODIGO));
    String nome   = request.getParameter(Usuario.FIELD_NOME);
    String senha  = request.getParameter(Usuario.FIELD_SENHA);
    int    nivel  = Integer.parseInt(request.getParameter(Usuario.FIELD_NIVEL));
    // *
    usuario = new UsuarioInfo(codigo,
                              nome,
                              senha,
                              nivel);
    // se esta inserindo...inclui
    if (request.getParameter(INSERT) != null)
      facade.usuario().insert(usuario);
    // se não...atualiza
    else
      facade.usuario().update(usuario);
    // volta para a página de consulta
    response.sendRedirect("controller?" + Controller.ACTION + "=" + Controller.ACTION_USUARIO);
    // dispara
    return;
  }
  // se quer editar...
  else {
    // chave
    int codigo = Integer.parseInt(request.getParameter(Usuario.FIELD_CODIGO));
    // pega o info
    usuario = facade.usuario().getByCodigo(codigo);
  } // if
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Usuário</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  function post() {
    // se não confirmou...dispara
    if (!confirm("Deseja mesmo salvar os dados informados?"))
      return;
    // valida os dados
    if (!validate(formUsuario.<%=Usuario.FIELD_NOME%>, "Nome"))
      return;
    if (!validate(formUsuario.<%=Usuario.FIELD_NIVEL%>, "Nível"))
      return;
    if (!validate(formUsuario.<%=Usuario.FIELD_SENHA%>, "Senha"))
      return;
    // altera o comando
    formUsuario.<%=Controller.COMMAND%>.value = "<%=POST%>";
    // envia o formulário
    formUsuario.submit();
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
      <!-- títutlo -->
      <h2>Usuário</h2>
      <!-- form -->
      <form name="formUsuario" action="controller" method="POST">
        <input type="hidden" name="<%=Controller.ACTION%>" value="<%=Controller.ACTION_USUARIO_CADASTRO%>">
        <input type="hidden" name="<%=Controller.COMMAND%>" value="">
        <%if (command.equals(INSERT)) {%>
        <input type="hidden" name="<%=INSERT%>" value="true">
        <%} // if%>
        <!-- dados do usuário -->
        <input type="hidden" name="<%=Usuario.FIELD_CODIGO%>" value="<%=usuario.getCodigo()%>">
        <table class="BtnFace">
          <tr>
            <td>Nome</td>
            <td><input type="text" name="<%=Usuario.FIELD_NOME%>" value="<%=usuario.getNome()%>"></td>
            <script>formUsuario.<%=Usuario.FIELD_NOME%>.focus();</script>
          </tr>
          <tr>
            <td>Nível</td>
            <td><select name="<%=Usuario.FIELD_NIVEL%>">
                  <option value="<%=Usuario.NIVEL_BASICO%>" <%=usuario.getNivel() == Usuario.NIVEL_BASICO ? "selected" : ""%>>Básico</option>
                  <option value="<%=Usuario.NIVEL_AVANCADO%>" <%=usuario.getNivel() == Usuario.NIVEL_AVANCADO ? "selected" : ""%>>Avançado</option>
                  <option value="<%=Usuario.NIVEL_ADMINISTRADOR%>" <%=usuario.getNivel() == Usuario.NIVEL_ADMINISTRADOR ? "selected" : ""%>>Administrador</option>
                </select>
            </td>
          </tr>
          <tr>
            <td>Senha</td>
            <td><input type="password" name="<%=Usuario.FIELD_SENHA%>" value="<%=usuario.getSenha()%>"></td>
          </tr>
        </table>
        <br>
        <!--botões de comando-->
        <button onclick="post();">Salvar</button>
      </form>
      <button id="buttonVoltar" onclick="javascript:history.back();">Voltar</button>
    </td>
  </tr>
</table>

</body>
</html>
