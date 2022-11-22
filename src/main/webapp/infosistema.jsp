<%@include file="include/beans.jsp"%>
<%@include file="include/utils.jsp"%>

<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>

<%!
  public String getRowClass(int index) {
    return index % 2 > 0 ? "GridRowEven" : "GridRowOdd";
  }
%>

<%
  // informações de banco de dados
  DatabaseMetaData databaseMetaData = facade.connection().getMetaData();
  // informações do sistema
  Properties properties = System.getProperties();
%>

<html>
<head>
<title><%=facade.applicationInformation().getName()%> - Informações do Sistema</title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body leftmargin="5" topmargin="5" rightmargin="5" bottommargin="5">

<!--conteúdo-->
<table class="BtnFace" cellpadding="5" style="width:100%; height:100%; border:1px solid;">
  <tr>
    <td valign="top">
      <!-- informações do sistema -->
      <h2>Informações do Sistema</h2>

      <!-- variáveis de ambiente -->
      <table class="Grid">
        <tr class="GridHeader">
          <td colspan="2"><b>Variáveis de Ambiente</b></td>
        </tr>
        <%Enumeration keys = properties.keys();
          int i=0;
          while (keys.hasMoreElements()) {
            String name = (String)keys.nextElement();
            String value = properties.getProperty(name);%>
        <tr class="<%=getRowClass(i)%>">
          <td width="150px"><%=name%></td>
          <td><%=value%></td>
        </tr>
        <%i++;
          } // while%>
      </table>

      <br/>
      <br/>

      <!-- informações do banco -->
      <table class="Grid">
        <tr class="GridHeader">
          <td colspan="2"><b>Banco de Dados</b></td>
        </tr>
        <tr class="GridRowOdd">
          <td width="150px">Driver</td>
          <td><%=databaseMetaData.getDriverName() + " " + databaseMetaData.getDriverVersion()%></td>
        </tr>
        <tr class="GridRowEven">
          <td>Classe</td>
          <td><%=databaseMetaData.getConnection().getClass().getName()%></td>
        </tr>
        <tr class="GridRowOdd">
          <td width="130px">URL</td>
          <td><%=databaseMetaData.getURL()%></td>
        </tr>
        <tr class="GridRowEven">
          <td width="130px">Usuário</td>
          <td><%=databaseMetaData.getUserName()%></td>
        </tr>
        <tr class="GridRowOdd">
          <td>Máximo de conexões</td>
          <td><%=databaseMetaData.getMaxConnections()%></td>
        </tr>
        <tr class="GridRowEven">
          <td>Máximo de Statements</td>
          <td><%=databaseMetaData.getMaxStatements()%></td>
        </tr>
        <tr class="GridRowOdd">
          <td>Produto</td>
          <td><%=databaseMetaData.getDatabaseProductVersion()%></td>
        </tr>
        <tr class="GridRowEven">
          <td>Palavras chaves SQL</td>
          <td><%=databaseMetaData.getSQLKeywords()%></td>
        </tr>
        <tr class="GridRowOdd">
          <td>Funções SQL</td>
          <td><%=databaseMetaData.getStringFunctions()%></td>
        </tr>
        <tr class="GridRowEven">
          <td>Funções de sistema</td>
          <td><%=databaseMetaData.getSystemFunctions()%></td>
        </tr>
        <tr class="GridRowOdd">
          <td>ANSI SQL/92 básico</td>
          <td><%=databaseMetaData.supportsANSI92EntryLevelSQL() ? "Sim" : "Não"%></td>
        </tr>
        <tr class="GridRowEven">
          <td>ANSI SQL/92 total</td>
          <td><%=databaseMetaData.supportsANSI92FullSQL() ? "Sim" : "Não"%></td>
        </tr>
        <tr class="GridRowOdd">
          <td>Atualizações em lote</td>
          <td><%=databaseMetaData.supportsBatchUpdates() ? "Sim" : "Não"%></td>
        </tr>
      </table>
      <!--  -->
    </td>
  </tr>
</table>

</body>
</html>
