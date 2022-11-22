<%@include file="../include/beans.jsp"%>
<%@include file="../include/utils.jsp"%>

<%!
  // constantes
  String DELETE = "DELETE";
  String INSERT = "INSERT";
  // fun��es
  public String actionCadastro(int codigoUsuario) {
    return "controller?" + Controller.ACTION + "=" + Controller.ACTION_USUARIO_CADASTRO
         + "&" + Usuario.FIELD_CODIGO + "=" + codigoUsuario;
  }

  public String getRowClass(int rowIndex) {
    if (rowIndex % 2 > 0)
      return "GridRowEven";
    else
      return "GridRowOdd";
  }

  public String nivel(int nivel) {
    switch (nivel) {
      case 1: return "B�sico";
      case 2: return "Avan�ado";
      case 3: return "Administrador";
    } // swtich
    return "";
  }
%>

<%

  // comando para realizar
  String command = request.getParameter(Controller.COMMAND);
  if (command == null)
    command = "";
  // quer excluir?
  if (command.equals(DELETE)) {
    // chave do registro para excluir
    int codigo = Integer.parseInt(request.getParameter(Usuario.FIELD_CODIGO));
    // info do registro que iremos excluir
    UsuarioInfo usuarioInfo = facade.usuario().getByCodigo(codigo);
    // se achamos...exclui
    if (usuarioInfo != null)
      facade.usuario().delete(usuarioInfo);
  } // if
  // pega a lista para exibir
  UsuarioInfo[] usuarios = facade.usuario().get(facade.usuario().get(""));
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Usu�rios</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<script language="javascript">

  function deleteUsuario() {
    // chave do registro
    codigoUsuario = formUsuario.<%=Usuario.FIELD_CODIGO%>;
    // se n�o selecionou nada...dispara
    if (codigoUsuario.value == 0) {
      alert("Selecione o Usu�rio para ser exclu�do.");
      return;
    }
    // se n�o quer excluir...dispara
    if (!confirm("Deseja realmente excluir o Usu�rio selecionado?"))
      return;
    // informa que o comando ser� exclus�o
    formUsuario.<%=Controller.COMMAND%>.value = "<%=DELETE%>";
    // submete o form
    formUsuario.submit();
  }

  function insertUsuario() {
    window.location.href = "controller?<%=Controller.ACTION%>=<%=Controller.ACTION_USUARIO_CADASTRO%>"
                         + "&<%=Controller.COMMAND%>=<%=INSERT%>";
  }

</script>

<!--conte�do-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- t�tutlo -->
      <h2>Usu�rio</h2>
      <!-- form -->
      <form name="formUsuario" action="controller" method="POST">
        <input type="hidden" name="<%=Controller.ACTION%>" value="<%=Controller.ACTION_USUARIO%>">
        <input type="hidden" name="<%=Controller.COMMAND%>" value="">
        <input type="hidden" name="<%=Usuario.FIELD_CODIGO%>" value="0">
        <!-- lista -->
        <table class="Grid" style="width:550px;">
          <tr class="GridHeader">
            <td style="width:20px;"  align="center">!</td>
            <td style="width:450px;" align="left">Nome</td>
            <td style="width:80px;"  align="left">N�vel</td>
          </tr>
        <% // loop
           for (int i=0; i<usuarios.length; i++) {
           // info da vez
           UsuarioInfo usuario = usuarios[i];
           %>
          <tr class="<%=getRowClass(i)%>">
            <td align="center"><input type="radio" name="radioDelete" onclick="javascript:<%=Usuario.FIELD_CODIGO%>.value=<%=usuario.getCodigo()%>;"></td>
            <td align="left"><a href="<%=actionCadastro(usuario.getCodigo())%>"
                                title="Editar..."><%=usuario.getNome()%></a></td>
            <td align="left"><%=nivel(usuario.getNivel())%></td>
          </tr>
        <%}%>
        </table>
        <br>
        <!--bot�es de comando-->
        <button onclick="insertUsuario();">Incluir</button>&nbsp;&nbsp;&nbsp;
        <button onclick="deleteUsuario();">Excluir</button>
      </form>
    </td>
  </tr>
</table>

</body>
</html>
