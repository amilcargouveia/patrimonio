<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%></title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<frameset rows="98,*" framespacing="0" frameborder="NO" border="0">
  <frame src="top.jsp" name="frameTop" scrolling="no" noresize>
  <frameset cols="200,*" framespacing="0" frameborder="NO" border="0">
    <frame src="menu.jsp"    name="frameMenu"    scrolling="no" noresize>
    <frame src="content.jsp" name="frameContent" scrolling="yes" noresize>
  </frameset>
</frameset>

<noframes>
<body>
  Este serviço utiliza frames mas seu browser não tem suporte a este recurso.
</body>
</noframes>
</html>
