<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<%
  // par�metros esperados
  String exception = request.getParameter(Controller.PARAM_EXCEPTION);
  if (exception == null)
    exception = "<desconhecida>";
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Exce��o</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body class="AppWorkSpace" leftmargin="0" topmargin="0">

<table class="AppWorkSpace" style="width:100%; height:100%;">
  <tr>
    <td align="center">
      <!--janela de exce��o-->
      <table class="MsgBox" style="width:50%; padding:8px;">
        <tr class="ActiveCaption">
          <td style="padding:3px;"><b>Exce��o</b></td>
        </tr>
        <tr>
          <td class="BtnFace" style="font-weight:normal; padding:8px;">
            <table width="100%" class="BtnFace">
              <tr>
                <td valign="top"><img src="images/erro32x32.gif"></td>
                <td class="BtnFace">
                    Ocorreu a seguinte exce��o na tentativa de
                    realizar a opera��o desejada: <br /><br />
                    <%=exception%>
                </td>
              </tr>
              <tr>
                <td colspan="2">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="2" align="center">
                  <button id="buttonVoltar" class="BtnFace" onclick="javascript:history.back();">Voltar</button>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<script language="javascript">
  buttonVoltar.focus();
</script>

</body>
</html>
