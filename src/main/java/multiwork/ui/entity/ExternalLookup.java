package multiwork.ui.entity;

import java.net.*;

import multiwork.servlet.*;

/**
 * Implementa um mecanismo de pesquisa em tabelas. Seu princípio básico
 * é realizar consultas de registros em uma tabela, exibindo uma interface
 * de seleção, única ou múltipla, para o usuário e retornando tal seleção em
 * um controle HTML gerado.
 */
public class ExternalLookup {

  static public final byte SELECT_TYPE_SINGLE   = 1;
  static public final byte SELECT_TYPE_MULTIPLE = 2;

  static public final String TABLE_NAME              = "tableName";
  static public final String SEARCH_FIELD_NAMES      = "searchFieldNames";
  static public final String SEARCH_FIELD_TITLES     = "searchFieldTitles";
  static public final String DISPLAY_FIELD_NAMES     = "displayFieldNames";
  static public final String DISPLAY_FIELD_TITLES    = "displayFieldTitles";
  static public final String DISPLAY_FIELD_WIDTHS    = "displayFieldWidths";
  static public final String RETURN_FIELD_NAMES      = "returnFieldNames";
  static public final String RETURN_USER_FIELD_NAMES = "returnUserFieldNames";
  static public final String ORDER_FIELD_NAMES       = "orderFieldNames";
  static public final String FILTER_EXPRESSION       = "filterExpression";
  static public final String SELECT_TYPE             = "selectType";
  static public final String WINDOW_TITLE            = "windowTitle";
  static public final String USER                    = "user";
  static public final String EXTERNAL_LOOKUP_ID      = "externalLookupId";

  /**
   * Construtor padrão, não pode ser chamado.
   */
  private ExternalLookup() {
  }

  static private String getInputTextValue(String[] values) {
    String result = "";
    for (int i=0; i<values.length; i++) {
      if (!result.equals(""))
        result += ";";
      result += values[i];
    } // for
    return result;
  }

  static private String getSelectOptions(String[] values, String[] texts) {
    String result = "";
    for (int i=0; i<values.length; i++) {
      result += "<option value=\"" + values[i] + "\">" + texts[i] + "</option>\r\n";
    } // for
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

  /**
   * Retorna o script contendo o botão de consulta de dados e o elemento HTML
   * responsável pela exibição dos dados selecionados.
   * @param tableName Nome da tabela de pesquisa.
   * @param searchFieldNames Nomes dos campos que possibilitarão ao usuário
   *                         realizar pesquisas interativas.
   * @param searchFieldTitles Títulos dos campos de pesquisa.
   * @param displayFieldNames Nomes dos campos que serão exibidos na listagem.
   * @param displayFieldTitles Títulos dos campos que serão exibidos na listagem.
   * @param displayFieldWidths Larguras, em pixel, das colunas dos campos que
   *                           serão exibidos na listagem.
   * @param returnFieldNames Nomes dos campos cujos valores serão retornados para
   *                         serem utilizados como chave.
   * @param returnFieldValues Valores iniciais para 'returnFieldNames'.
   * @param returnUserFieldNames Nomes dos campos cujos valores serão retornados
   *                             para serem exibidos ao usuário.
   * @param returnUserFieldValues Valores iniciais para 'returnUserFieldNames'.
   * @param orderFieldNames Nomes dos campos para ordenação da listagem.
   * @param filterExpression Expressão SQL para filtro padrão da listagem.
   * @param selectType Tipo de seleção que o usuário poderá realizar.
   * @param windowTitle Título da janela de pesquisa.
   * @param externalLookupId Nome do elemento HTML de exibição para referências
   *                         em scripts.
   * @param externalLookupStyle Estilo HTML para modificação do elemento HTML
   *                            de exibição.
   * @param onChangeScript Código JavaScript para ser executado quando o usuário
   *                       alterar o valor do elemento HTML.
   * @throws Exception Em caso de exceção na tentativa de geraro script.
   * @return Retorna o script contendo o botão de consulta de dados e o elemento
   *         HTML responsável pela exibição dos dados selecionados.
   */
  static public String script(String     tableName,
                              String[]   searchFieldNames,
                              String[]   searchFieldTitles,
                              String[]   displayFieldNames,
                              String[]   displayFieldTitles,
                              short[]    displayFieldWidths,
                              String[]   returnFieldNames,
                              String[]   returnFieldValues,
                              String[]   returnUserFieldNames,
                              String[]   returnUserFieldValues,
                              String[]   orderFieldNames,
                              String     filterExpression,
                              byte       selectType,
                              String     windowTitle,
                              String     externalLookupId,
                              String     externalLookupStyle,
                              String     onChangeScript) throws Exception {
    // nosso resultado
    String result = "";
    // script para apagar valores
    String deleteScript = "";
    // tipo de elemento HTML que iremos utilizar
    switch (selectType) {
      case SELECT_TYPE_SINGLE: {
        result = "<input type=\"hidden\" \r\n"
               +        "name=\"" + externalLookupId + "\"\r\n"
               +        "value=\"" + getInputTextValue(returnFieldValues) + "\">\r\n"
               + "<input type=\"text\" \r\n"
               +        "name=\"" + externalLookupId + USER + "\" \r\n"
               +        "value=\"" + getInputTextValue(returnUserFieldValues) + "\"\r\n"
               +        "title=\"Clique no botão ao lado para pesquisar.\" \r\n"
               +        "style=\"" + externalLookupStyle + "\"\r\n"
               +        "readonly>\r\n";
        deleteScript = externalLookupId + ".value='';" + externalLookupId + USER + ".value='';";
        break;
      }
      case SELECT_TYPE_MULTIPLE: {
        result = "<select size=\"2\" \r\n"
               +         "name=\"" + externalLookupId + "\" \r\n"
               +         "title=\"Clique no botão ao lado para pesquisar.\" \r\n"
               +         "style=\"" + externalLookupStyle + "\"\r\n "
               +         "multiple>\r\n"
               + getSelectOptions(returnFieldValues, returnUserFieldValues) + "\r\n"
               + "</select>\r\n";
        deleteScript = "for (i=" + externalLookupId + ".options.length-1; i>=0; i--) {"
                       + "if (" + externalLookupId + ".options[i].selected) "
                         + externalLookupId + ".options.remove(i);"
                     + "};";
        break;
      }
    } // swtich
    // URL do external lookup
    String url = "controller?" + Controller.ACTION + "=" + Controller.ACTION_EXTERNAL_LOOKUP
               + "&" + TABLE_NAME + "=" + tableName
               + "&" + SEARCH_FIELD_NAMES + "=" + getValuesFromArray(searchFieldNames)
               + "&" + SEARCH_FIELD_TITLES + "=" + getValuesFromArray(searchFieldTitles)
               + "&" + DISPLAY_FIELD_NAMES + "=" + getValuesFromArray(displayFieldNames)
               + "&" + DISPLAY_FIELD_TITLES + "=" + getValuesFromArray(displayFieldTitles)
               + "&" + DISPLAY_FIELD_WIDTHS + "=" + getValuesFromArray(displayFieldWidths)
               + "&" + RETURN_FIELD_NAMES + "=" + getValuesFromArray(returnFieldNames)
               + "&" + RETURN_USER_FIELD_NAMES + "=" + getValuesFromArray(returnUserFieldNames)
               + "&" + ORDER_FIELD_NAMES + "=" + getValuesFromArray(orderFieldNames)
               + "&" + FILTER_EXPRESSION + "=" + URLEncoder.encode(filterExpression, "UTF-8")
               + "&" + SELECT_TYPE + "=" + selectType
               + "&" + EXTERNAL_LOOKUP_ID + "=" + externalLookupId
               + "&" + WINDOW_TITLE + "=" + windowTitle;
    // script ao alterar
    result += "<script language=\"javascript\">\r\n"
            + "  function " + externalLookupId + "OnChange() {\r\n"
            +      onChangeScript + "\r\n"
            +    "}\r\n"
            + "</script>\r\n";
    // botão de pesquisa
    result += "<button title=\"Pesquisar...\" "
            +         "onclick=\"javascript:window.open('" + url + "',"
            +                                           "'externalLookup',"
            +                                           "'top=0,left=0,width=600,height=480,toolbar=no,location=no,status=no,menu=no');\">"
            + "<img src=\"images/externallookup16x16.gif\">"
            + "</button>\r\n";
    // botão apagar
    result += "<button title=\"Apagar\" "
            +         "onclick=\"javascript:" + deleteScript + "\">"
            + "<img src=\"images/externallookupapagar16x16.gif\">"
            + "</button>\r\n";
    // retorna o Script
    return result;
  }

}
