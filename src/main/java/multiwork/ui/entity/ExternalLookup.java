package multiwork.ui.entity;

import java.net.*;

import multiwork.servlet.*;

/**
 * Implementa um mecanismo de pesquisa em tabelas. Seu princ�pio b�sico
 * � realizar consultas de registros em uma tabela, exibindo uma interface
 * de sele��o, �nica ou m�ltipla, para o usu�rio e retornando tal sele��o em
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
   * Construtor padr�o, n�o pode ser chamado.
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
   * Retorna o script contendo o bot�o de consulta de dados e o elemento HTML
   * respons�vel pela exibi��o dos dados selecionados.
   * @param tableName Nome da tabela de pesquisa.
   * @param searchFieldNames Nomes dos campos que possibilitar�o ao usu�rio
   *                         realizar pesquisas interativas.
   * @param searchFieldTitles T�tulos dos campos de pesquisa.
   * @param displayFieldNames Nomes dos campos que ser�o exibidos na listagem.
   * @param displayFieldTitles T�tulos dos campos que ser�o exibidos na listagem.
   * @param displayFieldWidths Larguras, em pixel, das colunas dos campos que
   *                           ser�o exibidos na listagem.
   * @param returnFieldNames Nomes dos campos cujos valores ser�o retornados para
   *                         serem utilizados como chave.
   * @param returnFieldValues Valores iniciais para 'returnFieldNames'.
   * @param returnUserFieldNames Nomes dos campos cujos valores ser�o retornados
   *                             para serem exibidos ao usu�rio.
   * @param returnUserFieldValues Valores iniciais para 'returnUserFieldNames'.
   * @param orderFieldNames Nomes dos campos para ordena��o da listagem.
   * @param filterExpression Express�o SQL para filtro padr�o da listagem.
   * @param selectType Tipo de sele��o que o usu�rio poder� realizar.
   * @param windowTitle T�tulo da janela de pesquisa.
   * @param externalLookupId Nome do elemento HTML de exibi��o para refer�ncias
   *                         em scripts.
   * @param externalLookupStyle Estilo HTML para modifica��o do elemento HTML
   *                            de exibi��o.
   * @param onChangeScript C�digo JavaScript para ser executado quando o usu�rio
   *                       alterar o valor do elemento HTML.
   * @throws Exception Em caso de exce��o na tentativa de geraro script.
   * @return Retorna o script contendo o bot�o de consulta de dados e o elemento
   *         HTML respons�vel pela exibi��o dos dados selecionados.
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
               +        "title=\"Clique no bot�o ao lado para pesquisar.\" \r\n"
               +        "style=\"" + externalLookupStyle + "\"\r\n"
               +        "readonly>\r\n";
        deleteScript = externalLookupId + ".value='';" + externalLookupId + USER + ".value='';";
        break;
      }
      case SELECT_TYPE_MULTIPLE: {
        result = "<select size=\"2\" \r\n"
               +         "name=\"" + externalLookupId + "\" \r\n"
               +         "title=\"Clique no bot�o ao lado para pesquisar.\" \r\n"
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
    // bot�o de pesquisa
    result += "<button title=\"Pesquisar...\" "
            +         "onclick=\"javascript:window.open('" + url + "',"
            +                                           "'externalLookup',"
            +                                           "'top=0,left=0,width=600,height=480,toolbar=no,location=no,status=no,menu=no');\">"
            + "<img src=\"images/externallookup16x16.gif\">"
            + "</button>\r\n";
    // bot�o apagar
    result += "<button title=\"Apagar\" "
            +         "onclick=\"javascript:" + deleteScript + "\">"
            + "<img src=\"images/externallookupapagar16x16.gif\">"
            + "</button>\r\n";
    // retorna o Script
    return result;
  }

}
