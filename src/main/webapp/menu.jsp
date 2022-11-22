<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<%@page import="java.util.*"%>

<%!
  public String[] getMenuNames(Action[] actions) {
    // lista de nomes dos menus
    Vector vector = new Vector();
    // loop nos actions
    for (int i=0; i<actions.length; i++) {
      // Action da vez
      Action action = actions[i];
      // se ainda n�o temos o nome na lista...adiciona
      if ((!action.getMenu().equals("")) && (vector.indexOf(action.getMenu()) < 0))
        vector.add(action.getMenu());
    } // for
    // retorna
    String[] result = new String[vector.size()];
    vector.copyInto(result);
    return result;
  }
%>

<%
  // pega a lista de menus que ser�o criados
  String[] menuNames = getMenuNames(facade.actions());
  // n�vel do usu�rio que efetuou logon
  int userLevel = facade.getLoggedUser() != null ? facade.getLoggedUser().getNivel() : -1;
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Menu</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body class="Background" leftmargin="6" topmargin="6" rightmargin="6" bottommargin="6">

<script src="scripts/tools.js"></script>
<script src="scripts/mousetools.js"></script>

<script language="javascript">

  imageButtonShow = new Image();
  imageButtonShow.src = "images/expandir18x18.gif";
  imageButtonHidden = new Image();
  imageButtonHidden.src = "images/recolher18x18.gif";

  function readShowHideWindow(name) {
    // l� o valor do cookie
    return readCookieValue(name) == "true";
  }

  function showHideWindow(name, imageButton, row, show) {
    // se a janela est� oculta ou devemos for�ar a exibi��o...exibe
    if ((imageButton.src == imageButtonShow.src) || show) {
      // transforma o bot�o em ocultar
      imageButton.src   = imageButtonHidden.src;
      imageButton.title = "Ocultar";
      // exibe a linha
      row.style.display = "inline";
      // estamos exibindo
      show = true;
    }
    // se n�o...oculta
    else {
      // transforma o bot�o em exibir
      imageButton.src   = imageButtonShow.src;
      imageButton.title = "Exibir";
      // exibe a linha
      row.style.display = "none";
      // estamos ocultando
      show = false;
    } // if
    // grava o estado de exibi��o no cookie
    writeShowHideWindow(name, show);
  }

  function writeShowHideWindow(name, show) {
    // grava no cookie se a janela est� exibida ou ocultada
    writeCookieValue(name, show);
  }

</script>

<%// loop nos nomes de menus
  for (int i=0; i<menuNames.length; i++) {
    // nome do menu da vez
    String menuName = menuNames[i];
    String menuId   = StringTools.spacesToUnderline(StringTools.removeAccents(menuName));%>
  <!-- <%=menuName%> -->
  <table class="Window" style="width:100%;">
    <tr>
      <td style="padding:3px;"><b><%=menuName%></b></td>
      <td align="right"><img id="imageButton<%=menuId%>"
                             style="cursor:hand;"
                             onclick="javascript:showHideWindow('menu<%=menuId%>', imageButton<%=menuId%>, row<%=menuId%>);"></tb>
    </tr>
    <tr id="row<%=menuId%>">
      <td colspan="2" class="WindowFrame" style="padding:5px;">
      <!-- links das a��es do menu -->
      <%for (int w=0; w<facade.actions().length; w++) {
          // Action da vez
          Action action = facade.actions()[w];
          // se o Action � desse menu e o usu�rio tem acesso...
          if (action.getMenu().equals(menuName) && (userLevel >= action.getUserLevel())) {%>
          <a href="controller?<%=Controller.ACTION%>=<%=action.getName()%>"
             title="<%=action.getTitle()%>"
             target="frameContent"><%=action.getCaption()%></a><br/>
          <%} // if%>
      <%} // for%>
      </td>
    </tr>
  </table>
  <script>
    showHideWindow("menu<%=menuId%>",
                   imageButton<%=menuId%>,
                   row<%=menuId%>,
                   readShowHideWindow("menu<%=menuId%>"));
  </script>
  <br/>
<%} // for%>

<!-- Utilit�rios -->
<table class="Window" style="width:100%;">
  <tr>
    <td style="padding:3px;"><b>Utilit�rios</b></td>
    <td align="right"><img id="imageButtonUtilitarios"
                           style="cursor:hand;"
                           onclick="javascript:showHideWindow('menuUtilitarios', imageButtonUtilitarios, rowUtilitarios);"></tb>
  </tr>
  <tr id="rowUtilitarios">
    <td colspan="2" class="WindowFrame" style="padding:5px;">
    <%if (userLevel >= Usuario.NIVEL_BASICO) {%>
      <a href="controller?<%=Controller.ACTION%>=<%=Controller.ACTION_CHANGE_PASSWORD%>"
         title="Alterar senha de acesso."
         target="frameContent">Alterar Senha</a> <br/>
    <%} // if%>
    <%if (userLevel >= Usuario.NIVEL_ADMINISTRADOR) {%>
      <a href="controller?<%=Controller.ACTION%>=<%=Controller.ACTION_USUARIO%>"
         title="Cadastro de usu�rios do sistema."
         target="frameContent">Usu�rios</a>
    <%} // if%>
    </td>
  </tr>
</table>
<script>
  showHideWindow("menuUtilitarios",
                 imageButtonUtilitarios,
                 rowUtilitarios,
                 readShowHideWindow("menuUtilitarios"));
</script>
<br/>

</body>

</html>
