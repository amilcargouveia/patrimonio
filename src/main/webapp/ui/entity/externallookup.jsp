<%@include file="../../include/beans.jsp"%>

<%@page import="java.net.*"%>
<%@page import="java.sql.*"%>
<%@page import="java.util.*"%>
<%@page import="multiwork.util.*"%>
<%@page import="multiwork.ui.entity.*"%>

<%!
  public final String SELECTOR        = "selector";
  public final String SEARCH_FIELD    = "searchField";
  public final String SEARCH_VALUE    = "searchValue";

  public ResultSet getResultSet(Connection connection,
                                String     tableName,
                                String[]   displayFieldNames,
                                String[]   returnFieldNames,
                                String[]   orderFieldNames,
                                String     filterExpression,
                                String     extraFilterExpression) throws Exception {
    // campos que iremos utilizar
    String[] fields = sumStringArrays(displayFieldNames, returnFieldNames);
    // cláusula where
    String where = filterExpression;
    if (!extraFilterExpression.equals(""))
      where = where.equals("") ? extraFilterExpression : "(" + where + ") AND (" + extraFilterExpression + ")";
    // entidade de consulta
    PreparedStatement preparedStatement = SqlTools.prepareSelect(connection,
                                                                 tableName,
                                                                 fields,
                                                                 orderFieldNames,
                                                                 where);
    preparedStatement.execute();
    return preparedStatement.getResultSet();
  }

  public String getRowClass(int rowIndex) {
    if (rowIndex % 2 > 0)
      return "GridRowEven";
    else
      return "GridRowOdd";
  }

  public String getSelector(byte      selectType,
                            ResultSet resultSet,
                            String[]  returnFieldNames,
                            String[]  returnUserFieldNames) throws Exception {
    // tipo da seleção: radio ou checkbox?
    String type = selectType == ExternalLookup.SELECT_TYPE_SINGLE ? "radio" : "checkbox";
    // valores do selecionador
    String userValue = "";
    String value     = "";
    for (int i=0; i<returnFieldNames.length; i++) {
      if (value.equals("")) {
        value     = resultSet.getString(returnFieldNames[i]);
        userValue = resultSet.getString(returnUserFieldNames[i]);
      }
      else {
        value     += ";" + resultSet.getString(returnFieldNames[i]);
        userValue += ";" + resultSet.getString(returnUserFieldNames[i]);
      }
    } // for
    // retorna o selecionador
    return "<input type=\"hidden\" name=\"" + SELECTOR + ExternalLookup.USER + "\" value=\"" + userValue + "\">\r\n"
         + "<input type=\"" + type + "\" name=\"" + SELECTOR + "\" value=\"" + value + "\">";
  }

  public short[] getShortArray(String value) {
    StringTokenizer tokenizer = new StringTokenizer(value, ";", false);
    short[] result = new short[tokenizer.countTokens()];
    for (int i=0; i<result.length; i++)
      result[i] = Short.parseShort(tokenizer.nextToken());
    return result;
  }

  public String[] getStringArray(String value) {
    StringTokenizer tokenizer = new StringTokenizer(value, ";", false);
    String[] result = new String[tokenizer.countTokens()];
    for (int i=0; i<result.length; i++)
      result[i] = tokenizer.nextToken();
    return result;
  }

  public String[] sumStringArrays(String[] array1, String[] array2) {
    List list1 = Arrays.asList(array1);
    List list2 = Arrays.asList(array2);
    HashSet hashSet = new HashSet(list1.size() + list2.size());
    hashSet.addAll(list1);
    hashSet.addAll(list2);
    String[] result = {};
    result = (String[])hashSet.toArray(result);
    return result;
  }

  static private String getValuesFromArray(String[] array) {
    String result = "";
    for (int i=0; i<array.length; i++)
      if (result.equals(""))
        result = array[i];
      else
        result += ";" + array[i];
    return result;
  }

  static private String getValuesFromArray(short[] array) {
    String result = "";
    for (int i=0; i<array.length; i++)
      if (result.equals(""))
        result = array[i] + "";
      else
        result += ";" + array[i];
    return result;
  }

%>

<%
  // nossos parâmetros
  String     tableName            = request.getParameter(ExternalLookup.TABLE_NAME);
  String[]   searchFieldNames     = getStringArray(request.getParameter(ExternalLookup.SEARCH_FIELD_NAMES));
  String[]   searchFieldTitles    = getStringArray(request.getParameter(ExternalLookup.SEARCH_FIELD_TITLES));
  String[]   displayFieldNames    = getStringArray(request.getParameter(ExternalLookup.DISPLAY_FIELD_NAMES));
  String[]   displayFieldTitles   = getStringArray(request.getParameter(ExternalLookup.DISPLAY_FIELD_TITLES));
  short[]    displayFieldWidths   = getShortArray(request.getParameter(ExternalLookup.DISPLAY_FIELD_WIDTHS));
  String[]   returnFieldNames     = getStringArray(request.getParameter(ExternalLookup.RETURN_FIELD_NAMES));
  String[]   returnUserFieldNames = getStringArray(request.getParameter(ExternalLookup.RETURN_USER_FIELD_NAMES));
  String[]   orderFieldNames      = getStringArray(request.getParameter(ExternalLookup.ORDER_FIELD_NAMES));
  String     filterExpression     = URLDecoder.decode(request.getParameter(ExternalLookup.FILTER_EXPRESSION), "UTF-8");
  byte       selectType           = Byte.parseByte(request.getParameter(ExternalLookup.SELECT_TYPE));
  String     windowTitle          = request.getParameter(ExternalLookup.WINDOW_TITLE);
  String     externalLookupId     = request.getParameter(ExternalLookup.EXTERNAL_LOOKUP_ID);
  // nosso conjunto de dados
  ResultSet resultSet = null;
  boolean   hasRecords = false;
  // valor de pesquisa
  String searchField = request.getParameter(SEARCH_FIELD);
  if (searchField == null)
    searchField = "";
  String searchValue = request.getParameter(SEARCH_VALUE);
  if (searchValue == null)
    searchValue = "";
  // se não temos campos de pesquisa...
  if (searchFieldNames.length == 0) {
    resultSet = getResultSet(facade.connection(),
                             tableName,
                             displayFieldNames,
                             returnFieldNames,
                             orderFieldNames,
                             filterExpression,
                             "");
  }
  // se temos campos de pesquisa e um valor para pesquisar...
  else if (!searchValue.equals("")) {
    // filtro extra baseado no valor informado
    String extraFilterExpression = searchField + " LIKE '" + searchValue + "%'";
    // pega o ResultSet
    resultSet = getResultSet(facade.connection(),
                             tableName,
                             displayFieldNames,
                             returnFieldNames,
                             orderFieldNames,
                             filterExpression,
                             extraFilterExpression);
  } // if
%>

<html>
<head>
<title><%=windowTitle%></title>
<link href="css/default.css" rel="stylesheet" type="text/css">
</head>

<body>

<script language="javascript">

  <%// se o tipo de seleção é múltipla...
    if (selectType == ExternalLookup.SELECT_TYPE_MULTIPLE) {%>
  function optionExists(optionValue) {
    // loop nos options locais
    for (w=0; w<localSelect.options.length; w++) {
      // se tem o valor procurado...retorna ok
      if (localSelect.options[w].value == optionValue)
        return true;
    } // for
    // se chegou até aqui...não achamos nada
    return false;
  }

  function populateLocalOptions() {
    // select da página que nos abriu
    externalSelect = window.opener.document.all.<%=externalLookupId%>;
    // loop nos options do select recebido
    for (i=0; i<externalSelect.options.length; i++) {
      // option externo da vez
      externalOption = externalSelect.options[i];
      // novo option local
      localOption = window.document.createElement("OPTION");
      localOption.value = externalOption.value;
      localOption.text  = externalOption.text;
      // adiciona na lista local
      localSelect.add(localOption);
    } // for
  }
  <%}// if%>

  function insertSelection() {
    // externalLookup na janela que nos abriu
    externalLookup = window.opener.document.all.<%=externalLookupId%>;
  <%// se o tipo de seleção é simples...
    if (selectType == ExternalLookup.SELECT_TYPE_SINGLE) {%>
    // input para o usuário
    externalLookupUser = window.opener.document.all.<%=externalLookupId + ExternalLookup.USER%>;
    // se só temos 1 selector
    if (<%=SELECTOR%>.length == undefined) {
      // único selector
      selectorItem     = <%=SELECTOR%>;
      userSelectorItem = <%=SELECTOR + ExternalLookup.USER%>;
      // se está selecionado...usa seu valor
      if (selectorItem.checked) {
        externalLookup.value = selectorItem.value;
        externalLookupUser.value = userSelectorItem.value;
      } // if
    }
    // se tem vários selectors
    else {
      // loop nos selectors
      for (i=0; i<<%=SELECTOR%>.length; i++) {
        // selector da vez
        selectorItem     = <%=SELECTOR%>[i];
        userSelectorItem = <%=SELECTOR + ExternalLookup.USER%>[i];
        // se está selecionado...usa seu valor
        if (selectorItem.checked) {
          externalLookup.value = selectorItem.value;
          externalLookupUser.value = userSelectorItem.value;
          break;
        } // if
      } // for
    } // if
  <%}
    // se o tipo de seleção é múltipla...
    else {%>
    // pega as opções atuais para pesquisa
    populateLocalOptions();
    // se só temos 1 selector
    if (<%=SELECTOR%>.length == undefined) {
      // único selector
      selectorItem     = <%=SELECTOR%>;
      userSelectorItem = <%=SELECTOR + ExternalLookup.USER%>;
      // se está selecionado...usa seu valor
      if (selectorItem.checked) {
        // se o item ainda não existe...
        if (!optionExists(selectorItem.value)) {
          newOption = window.opener.document.createElement("OPTION");
          newOption.text = userSelectorItem.value;
          newOption.value = selectorItem.value;
          externalLookup.options.add(newOption);
        } // if
      } // if
    }
    // se tem vários selectors
    else {
      // loop nos selectors
      for (i=0; i<<%=SELECTOR%>.length; i++) {
        // selector da vez
        selectorItem     = <%=SELECTOR%>[i];
        userSelectorItem = <%=SELECTOR + ExternalLookup.USER%>[i];
        // se está selecionado...usa seu valor
        if (selectorItem.checked) {
          // se o item ainda não existe...
          if (!optionExists(selectorItem.value)) {
            newOption = window.opener.document.createElement("OPTION");
            newOption.text = userSelectorItem.value;
            newOption.value = selectorItem.value;
            externalLookup.options.add(newOption);
          } // if
        } // if
      } // for
    } // if
    // seleciona todos os options do ExternalLookp
    for (i=0; i<externalLookup.options.length; i++) {
      externalLookup.options[i].selected = true;
    } // for
  <%} // if%>
    // fecha nossa janela
    close();
  }

</script>

<!-- títutlo -->
<h2><%=windowTitle%></h2>

<!-- área de pesquisa -->
<%if (searchFieldNames.length > 0) {%>
<form id="formPesquisa">
  <input type="hidden" name="<%=Controller.ACTION%>" value="<%=Controller.ACTION_EXTERNAL_LOOKUP%>"/>
  <input type="hidden" name="<%=ExternalLookup.TABLE_NAME%>" value="<%=tableName%>"/>
  <input type="hidden" name="<%=ExternalLookup.SEARCH_FIELD_NAMES%>" value="<%=getValuesFromArray(searchFieldNames)%>"/>
  <input type="hidden" name="<%=ExternalLookup.SEARCH_FIELD_TITLES%>" value="<%=getValuesFromArray(searchFieldTitles)%>"/>
  <input type="hidden" name="<%=ExternalLookup.DISPLAY_FIELD_NAMES%>" value="<%=getValuesFromArray(displayFieldNames)%>"/>
  <input type="hidden" name="<%=ExternalLookup.DISPLAY_FIELD_TITLES%>" value="<%=getValuesFromArray(displayFieldTitles)%>"/>
  <input type="hidden" name="<%=ExternalLookup.DISPLAY_FIELD_WIDTHS%>" value="<%=getValuesFromArray(displayFieldWidths)%>"/>
  <input type="hidden" name="<%=ExternalLookup.RETURN_FIELD_NAMES%>" value="<%=getValuesFromArray(returnFieldNames)%>"/>
  <input type="hidden" name="<%=ExternalLookup.RETURN_USER_FIELD_NAMES%>" value="<%=getValuesFromArray(returnUserFieldNames)%>"/>
  <input type="hidden" name="<%=ExternalLookup.ORDER_FIELD_NAMES%>" value="<%=getValuesFromArray(orderFieldNames)%>"/>
  <input type="hidden" name="<%=ExternalLookup.FILTER_EXPRESSION%>" value="<%=filterExpression%>"/>
  <input type="hidden" name="<%=ExternalLookup.SELECT_TYPE%>" value="<%=selectType%>"/>
  <input type="hidden" name="<%=ExternalLookup.WINDOW_TITLE%>" value="<%=windowTitle%>"/>
  <input type="hidden" name="<%=ExternalLookup.EXTERNAL_LOOKUP_ID%>" value="<%=externalLookupId%>"/>

  <table class="BtnFace" padding="4" style="width:100%; border:1px solid;">
    <tr>
      <td>Campo</td>
      <td>Valor</td>
      <td></td>
    </tr>
    <tr>
      <td>
        <select name="<%=SEARCH_FIELD%>" style="width:150px;">
        <%for (int i=0; i<searchFieldNames.length; i++) {
            String searchFieldName = searchFieldNames[i];%>
          <option value="<%=searchFieldName%>" <%=searchFieldName.equals(searchField) ? "selected" : ""%>><%=searchFieldTitles[i]%></option>
        <%} // for%>
        </select>
      </td>
      <td>
        <input type="text" name="<%=SEARCH_VALUE%>" value="<%=searchValue%>" style="width:340px;">
        <script language="javascript">
          formPesquisa.<%=SEARCH_VALUE%>.focus();
        </script>
      </td>
      <td>
        <button style="width:75px;" onclick="javascript:formPesquisa.submit();">Pesquisar</button>
      </td>
    </tr>
  </table>
</form>
<br/>
<%} // if%>

<!-- lista  -->
<div class="Window" style="width:580px; height:<%=searchFieldNames.length > 0 ? 280 : 360%>px; overflow:scroll;">
  <input type="hidden" name="<%=""%>" value="0">
  <table class="Grid">
    <tr class="GridHeader">
      <td style="width:20px;" align="center">!</td>
      <%for (int i=0; i<displayFieldNames.length; i++) {%>
      <td style="width:<%=displayFieldWidths[i]%>px;"><%=displayFieldTitles[i]%></td>
      <%} // for%>
    </tr>
  <% // loop no ResultSet
     if (resultSet != null) {
       int w = 0;
       while (resultSet.next()) {
         hasRecords = true;%>
    <tr class="<%=getRowClass(w)%>">
      <td align="center"><%=getSelector(selectType, resultSet, returnFieldNames, returnUserFieldNames)%></td>
      <%for (int i=0; i<displayFieldNames.length; i++) {%>
      <td><%=resultSet.getString(displayFieldNames[i])%></td>
      <%} // for%>
    </tr>
  <%   w++;
       } // while
       // fecha o ResultSet e o Statement
       resultSet.getStatement().close();
       resultSet.close();
     } // if%>
  </table>
</div>
<br>
<!--botões de comando-->
<table style="width:100%;">
  <tr>
    <td align="right">
      <button onclick="insertSelection();" style="width:75px;" <%=hasRecords ? "" : "disabled"%>>OK</button>&nbsp;
      <button onclick="window.close();" style="width:75px;">Cancelar</button>
    </td>
  </tr>
</table>

<%// se o tipo de seleção é múltipla...
  if (selectType == ExternalLookup.SELECT_TYPE_MULTIPLE) {%>
  <!-- select local temporário para as opções da página que contém o Lookup -->
  <select id="localSelect" style="width:0px; height:0px; visibility:hidden;">
  </select>
<% }// if%>

</body>
</html>
