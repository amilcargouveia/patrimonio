package multiwork.ui.entity;

import java.sql.*;

import multiwork.util.*;

/**
 * Implementa um mecanismo de pesquisa em tabelas. Seu princípio básico
 * é gerar um controle HTML para que o usuário selecione um registro de uma
 * tabela e seja retornado o valor chave do registro selecionado.
 * <br/>
 * Exemplo: <br/>
 * <pre>
 * <%
 *   String[] estadoDisplayFieldNames = {Estado.FIELD_DESCRICAO};
 *   String[] estadoReturnFieldNames  = {Estado.FIELD_CODIGO};
 *   String[] estadoReturnFieldValues = {""};
 *   String[] estadoOrderFieldNames   = {Estado.FIELD_DESCRICAO};
 * %>
 * <%=LookupList.script(facade.connection(),
 *                      Estado.TABLE_ESTADO,
 *                      estadoDisplayFieldNames,
 *                      estadoReturnFieldNames,
 *                      estadoReturnFieldValues,
 *                      estadoOrderFieldNames,
 *                      "",
 *                      LookupList.SELECT_TYPE_SINGLE,
 *                      Cliente.FIELD_CODIGO_ESTADO,
 *                      "width:122px;")%>
 * </pre>
 */
public class LookupList {

  static public final byte SELECT_TYPE_SINGLE   = 1;
  static public final byte SELECT_TYPE_MULTIPLE = 2;

  /**
   * Construtor padrão, não pode ser chamado.
   */
  private LookupList() {
  }

  static private String getValues(String[] values) {
    String result = "";
    for (int i=0; i<values.length; i++) {
      if (!result.equals(""))
        result += ";";
      result += values[i];
    }
    return result;
  }

  static private String getFieldValues(ResultSet resultSet,
                                       String[] fieldNames) throws Exception {
    String result = "";
    for (int i=0; i<fieldNames.length; i++) {
      if (!result.equals(""))
        result += ";";
      result += resultSet.getString(fieldNames[i]);
    } // for
    return result;
  }

  static private String getSelectOptions(Connection connection,
                                         String     tableName,
                                         String[]   displayFieldNames,
                                         String[]   returnFieldNames,
                                         String[]   returnFieldValues,
                                         String[]   orderFieldNames,
                                         String     filterExpression,
                                         boolean    includeBlank) throws Exception {
    // nossos campos
    String[] fields = new String[displayFieldNames.length + returnFieldNames.length];
    for (int i=0; i<displayFieldNames.length; i++)
      fields[i] = displayFieldNames[i];
    for (int i=0; i<returnFieldNames.length; i++)
      fields[displayFieldNames.length+i] = returnFieldNames[i];
    // dados para exibir
    PreparedStatement preparedSelect = SqlTools.prepareSelect(connection,
                                                              tableName,
                                                              fields,
                                                              orderFieldNames,
                                                              filterExpression);
    try {
      // nossos dados
      preparedSelect.execute();
      ResultSet resultSet = preparedSelect.getResultSet();
      // nosso resultado
      String result = "";
      // option em branco
      if (includeBlank)
        result += "<option value=\"\"></option>\r\n";
      // loop nos nossos dados
      while (resultSet.next()) {
        String optionValue = getFieldValues(resultSet, returnFieldNames);
        result += "<option value=\"" + optionValue + "\"" + (optionValue.equals(getValues(returnFieldValues)) ? "selected" : "") + ">" + getFieldValues(resultSet, displayFieldNames) + "</option>\r\n";
      } // for
      return result;
    }
    finally {
      // libera recursos
      preparedSelect.getResultSet().close();
      preparedSelect.close();
    }
  }

  /**
   * Retorna o script contendo o elemento HTML com a lista de valores obtidos
   * de 'tableName' para seleção.
   * @param connection Connection para ser utilizado na conexão com o banco de dados.
   * @param tableName Nome da tabela de pesquisa.
   * @param displayFieldNames Nomes dos campos que serão exibidos na listagem.
   * @param returnFieldNames Nomes dos campos cujos valores serão retornados para
   *                         serem utilizados como chave.
   * @param returnFieldValues Valores iniciais para 'returnFieldNames'.
   * @param orderFieldNames Nomes dos campos para ordenação da listagem.
   * @param filterExpression Expressão SQL para filtro padrão da listagem.
   * @param selectType Tipo de seleção que o usuário poderá realizar.
   * @param lookupId Nome do elemento HTML de exibição para referências
   *                 em scripts.
   * @param lookupStyle Estilo HTML para modificação do elemento HTML
   *                    de exibição.
   * @param onChangeScript Código JavaScript para ser executado quando o usuário
   *                       alterar o valor do elemento HTML.
   * @return Retorna o script contendo o elemento HTML com a lista de valores
   *         obtidos de 'tableName' para seleção.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  static public String script(Connection connection,
                              String     tableName,
                              String[]   displayFieldNames,
                              String[]   returnFieldNames,
                              String[]   returnFieldValues,
                              String[]   orderFieldNames,
                              String     filterExpression,
                              byte       selectType,
                              String     lookupId,
                              String     lookupStyle,
                              String     onChangeScript) throws Exception {
    // nosso resultado
    String result = "<select size=\"" + (selectType == SELECT_TYPE_SINGLE ? 1 : 2) + "\" \r\n"
                  +         "name=\"" + lookupId + "\" \r\n"
                  +         "style=\"" + lookupStyle + "\" " + (selectType == SELECT_TYPE_MULTIPLE ? "multiple" : "") + " \r\n"
                  +         "onchange=\"" + onChangeScript + "\" >\r\n"
                  + getSelectOptions(connection,
                                     tableName,
                                     displayFieldNames,
                                     returnFieldNames,
                                     returnFieldValues,
                                     orderFieldNames,
                                     filterExpression,
                                     selectType == SELECT_TYPE_SINGLE) + "\r\n"
                  + "</select>\r\n";
    // retorna o Script
    return result;
  }

}
