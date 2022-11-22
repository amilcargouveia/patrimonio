<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<%@page import="java.text.*"%>
<%@page import="java.util.*"%>

<%!
  String LOGOFF = "logoff";
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%></title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body class="BackGround" leftmargin="0" topmargin="0">

<script language="javascript">

  function logoff() {
     if (!confirm("Tem certeza que deseja fazer logoff?"))
       return;
     top.location.href = "controller?<%=Controller.ACTION%>=<%=Controller.ACTION_LOGIN%>&<%=LOGOFF%>=true";
  }

</script>

<!--logomarca-->
<table class="Background" style="width=100%; height=70px;">
  <tr>
    <td width="50%">
      <span style="font-size:38pt"><%=facade.applicationInformation().getTitle()%></span>
    </td>
    <td width="50%" style="" align="right">
      <%Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
      %>
      <%=dateFormat.format(date)%>
      <%if (facade.getLoggedUser() != null) {%>
        <br/>
        <br/>
        <table class="Background">
          <tr>
            <td align="right">Usuário:</td>
            <td align="right"><b><%=facade.getLoggedUser().getNome()%></b></td>
          </tr>
        </table>
      <%} // if%>
    </td>
  </tr>
</table>

<!-- barra de ferramentas -->
<table class="BtnFace" style="width:100%; border-style:none; border-width:1;">
  <tr>
    <td>
      <button class="FlatButton"
              title="Fazer Logoff"
              style="height=22px; width=100px"
              onclick="javascript:logoff();"
              onFocus="blur()"
              onMouseEnter="className='FlatButtonOver'"
              onMouseLeave="className='FlatButton'"
              onMouseDown="className='FlatButtonDown'"
              onMouseUp="className='FlatButton'">
        <img src="images/logoff16x16.gif" align="absmiddle" />
        Fazer Logoff
      </button>
      <button class="FlatButton"
              title="Informações do sistema"
              style="height=22px; width=100px"
              onclick="javascript:top.frameContent.location.href='controller?<%=Controller.ACTION%>=<%=Controller.ACTION_SYSTEM_INFORMATION%>'"
              onFocus="blur()"
              onMouseEnter="className='FlatButtonOver'"
              onMouseLeave="className='FlatButton'"
              onMouseDown="className='FlatButtonDown'"
              onMouseUp="className='FlatButton'">
        <img src="images/infosistema16x16.gif" align="absmiddle" />
        Info do Sistema
      </button>
    </td>
    <td align="right">
      <button class="FlatButton"
              title="Fechar janela"
              style="height=22px; width=100px"
              onclick="javascript:top.frameContent.location.href='content.jsp'"
              onFocus="blur()"
              onMouseEnter="className='FlatButtonOver'"
              onMouseLeave="className='FlatButton'"
              onMouseDown="className='FlatButtonDown'"
              onMouseUp="className='FlatButton'">
        <img src="images/fechar18x18.gif" align="absmiddle" />
        Fechar Janela
      </button>
    </td>
  </tr>
</table>
