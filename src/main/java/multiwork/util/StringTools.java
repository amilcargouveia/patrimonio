package multiwork.util;

import java.util.*;

/**
 * Ferramentas de formatação e trabalho com Strings.
 */
public class StringTools {

  /**
   * Retorna true se 'array' contém 'value', desprezando letras maiúsculas e
   * minúsculas.
   * @param array String[] de valores para pesquisa.
   * @param value String para pesquisar em 'array'.
   * @return Retorna true se 'array' contém 'value', desprezando letras
   *         maiúsculas e minúsculas.
   */
  static public boolean arrayContains(String[] array, String value) {
    // retorna true se o índice for válido
    return arrayIndexOf(array, value) >= 0;
  }

  /**
   * Retorna true se 'array' contém todos os elementos de 'subArray' informados,
   * desprezando letras maiúsculas e minúsculas.
   * @param array String[] de valores para pesquisa.
   * @param subArray String[] cujos valores se deseja pesquisar em 'array'.
   * @return Retorna true se 'array' contém todos os elementos de 'subArray'
   *         informados, desprezando letras maiúsculas e minúsculas.
   */
  static public boolean arrayContains(String[] array, String[] subArray) {
    // retorna falso para todo caso
    boolean result = array.length == 0;
    // verifica todos os valores de subArray
    for (int i=0; i<subArray.length; i++) {
      // valor da vez
      result = arrayContains(array, subArray[i]);
      // se não bateu...já podemos disparar
      if (!result)
        return result;
    } // for
    // retorna o que achamos
    return result;
  }

  /**
   * Retorna a quantidade de elementos que array contém de subArray,
   * desprezando letras maiúsculas e minúsculas.
   * @param array String[] de valores para pesquisa.
   * @param subArray String[] cujos valores se deseja pesquisar em 'array'.
   * @return Retorna a quantidade de elementos que array contém de subArray,
   *         desprezando letras maiúsculas e minúsculas.
   */
  static public int arrayContainsCount(String[] array, String[] subArray) {
    // nosso retorno
    int result = 0;
    // verifica todos os valores de subArray
    for (int i=0; i<subArray.length; i++) {
      // valor da vez
      String[] value = {subArray[i]};
      if (arrayContains(array, value))
        result++;
    } // for
    // retorna o que achamos
    return result;
  }

  /**
   * Retorna o índice de 'element' em 'array'. Retorna -1 se 'element' não for
   * encontrado.
   * @param array String[] contendo os elementos onde se deseja pesquisar.
   * @param element String que se deseja localizar em 'array'.
   * @return Retorna o índice de 'element' em 'array'. Retorna -1 se 'element'
   *         não for encontrado.
   */
  static public int arrayIndexOf(String[] array, String element) {
    for (int i=0; i<array.length; i++) {
      if (array[i].equals(element))
        return i;
    }
    return -1;
  }

  /**
   * Retorna 'array' com seus elementos ordenados alfabeticamente.
   * @param array Array cujos elementos se deseja ordenar.
   * @return Retorna 'array' com seus elementos ordenados alfabeticamente.
   */
  public static String[] arraySort(String[] array) {
    // TreeSet para ordenar
    TreeSet treeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
    // inclui os itens do Array recebido
    for (int i=0; i<array.length; i++)
      treeSet.add(array[i]);
    // cria e preenche o Array de retorno
    Object[] sorted = treeSet.toArray();
    String[] result = new String[array.length];
    for (int i=0; i<result.length; i++)
      result[i] = (String)sorted[i];
    // retorna
    return result;
  }

  /**
   * Retorna true se os dois Stirng[]´s informados contiverem valores iguais
   * desprezando letras maiúsculas e minúsculas.
   * @param array1 Primeiro String[] de valores para comparar.
   * @param array2 Segundo String[] de valores para comparar.
   * @return Retorna true se os dois Stirng[]´s informados contiverem valores iguais
   * desprezando letras maiúsculas e minúsculas.
   */
  static public boolean compareArrays(String[] array1, String[] array2) {
    // retorna falso para todo caso
    boolean result = false;
    // se não temos a mesma quantidade de valores nas duas listas...dispara
    if (array1.length != array2.length)
      return result;
    // verifica todos os valores
    for (int i=0; i<array1.length; i++) {
      // valor da vez
      result = array1[i].equalsIgnoreCase(array2[i]);
      // se não bateu...já podemos disparar
      if (!result)
        return result;
    } // for
    // retorna o que achamos
    return result;
  }

  /**
   * Retorna 'value' com a máscara de CPF ou CNPJ.
   * @param value CPF ou CNPJ para ser formatado.
   * @return Retorna 'value' com a máscara de CPF ou CNPJ.
   */
  static public String formatCPFCNPJ(String value) {
    // se é um CPF
    if (value.length() == 11)
      return formatCustomMask(value, "000.000.000-00", '0');
    // se é um CNPJ
    else if (value.length() == 14)
      return formatCustomMask(value, "00.000.000/0000-00", '0');
    // se é desconhecido
    else
      return value;
  }

  /**
   * Retorna 'value' formatado com a máscara informada em 'mask'.
   * @param value Valor para ser formatado.
   * @param mask Máscara desejada.
   * @param maskChar Caractere em 'mask' que indica o espaço que deve ser
   *                 preenchido com os caracteres de 'value'.
   * @return Retorna 'value' formatado com a máscara informada em 'mask'.
   */
  static public String formatCustomMask(String value, String mask, char maskChar) {
    // se não temos valor para formatar...dispara
    if (value.equals(""))
      return "";
    // resultado = value + mask
    String stResult = "";
    // loop nos algarismos da máscara
    while (!mask.equals("")) {
      // se a posição atual requer um algarismo do valor...
      if (mask.charAt(0) == maskChar) {
        stResult += value.charAt(0); // adiciona o algarismo
        value = value.substring(1);  // apaga do valor
      }
      // se requer um caractere da máscara...
      else
        stResult += mask.charAt(0); // adiciona o caractere
      // apaga o caractere da máscara
      mask = mask.substring(1);
      // se não temos mais algarimos no valor...dispara
      if (value.equals(""))
        break;
    }; // while

    // retorna o valor mascarado
    return stResult;
  }

  /**
   * Faz as transformações necessárias em 'htmlText' para sua correta exibição
   * em formato texto simples. <p>
   * Ajustes realizados: <br>
   * <li> &lt;br /&gt; são substituídos por \r\n
   * <li> %xx são sobstituídos pelo caracter correspondete
   * @param htmlText Texto HTML para ser transformado em texto simples.
   * @return Retorna 'htmlText' com as transformações necessárias em para sua
   *         correta exibição em formato texto simples.
   */
  static public String htmlTextToText(String htmlText) {
    // nosso resultado
    String result = htmlText;
    // troca as quebras de linha
    result = result.replaceAll("<br>", "\r\n");
    result = result.replaceAll("<br />", "\r\n");
    // troca os caracteres codificados
    result = result.replaceAll("%20", " ");
    // ajusta as aspas
    result = result.replace('"', '\'');
    // retorna
    return result;
  }

  /**
   * Retorna 'value' sem acentos.
   * @param value Valor que se deseja retirar os acentos.
   * @return Retorna 'value' sem acentos.
   */
  static public String removeAccents(String value) {
    StringBuffer result = new StringBuffer(value);
    for (int i=0; i<result.length(); i++) {
      char chChar = result.charAt(i);
      // minúsculas
      if ("áàãâ".indexOf(chChar) >= 0)
        chChar = 'a';
      else if ("éèê".indexOf(chChar) >= 0)
        chChar = 'e';
      else if ("íìî".indexOf(chChar) >= 0)
        chChar = 'i';
      else if ("óòôõ".indexOf(chChar) >= 0)
        chChar = 'o';
      else if ("úùû".indexOf(chChar) >= 0)
        chChar = 'u';
      else if ("ç".indexOf(chChar) >= 0)
        chChar = 'c';
      // maiúsculas
      else if ("ÁÀÂÃÂ".indexOf(chChar) >= 0)
        chChar = 'A';
      else if ("ÉÊÈ".indexOf(chChar) >= 0)
        chChar = 'E';
      else if ("ÍÌÎ".indexOf(chChar) >= 0)
        chChar = 'I';
      else if ("ÓÒÔÕ".indexOf(chChar) >= 0)
        chChar = 'O';
      else if ("ÚÙÛ".indexOf(chChar) >= 0)
        chChar = 'U';
      else if ("Ç".indexOf(chChar) >= 0)
        chChar = 'C';
      // substitui o caractere
      result.setCharAt(i, chChar);
    } // for
    return result.toString();
  }

  /**
   * Retorna 'value' sem espaços entre os nomes.
   * @param value Value que se deseja remover os espaços.
   * @return Retorna 'value' sem espaços entre os nomes.
   */
  static public String removeSpaces(String value) {
    return value.replaceAll(" ", "");
  }


  /**
   * Retorna 'value' com '_' no lugar dos espaços.
   * @param value Valor que se desejar trocar os espaços por '_'.
   * @return Retorna 'value' com '_' no lugar dos espaços.
   */
  static public String spacesToUnderline(String value) {
    return value.replaceAll(" ", "_");
  }

  /**
   * Faz as transformações necessárias em 'text' para sua correta exibição em
   * páginas HTML. <p>
   * Ajustes realizados: <br>
   * <li> \r\n, \n\r, \r e \n são substituídos por &lt;br /&gt;
   * <li> " são substituíddas por '
   * @param text Texto para ser transformado em texto HTML.
   * @return Retorna 'text' com as transformações necessárias para
   *         sua correta exibição em páginas HTML.
   */
  static public String textToHTMLText(String text) {
    // se não recebemos nada...dispara
    if ((text == null) || (text.equals("")))
        return text;
    // nosso resultado
    String result = text;
    // troca as quebras de linha
    result = result.replaceAll("\r\n", "<br />");
    result = result.replaceAll("\n\r", "<br />");
    result = result.replaceAll("\r", "<br />");
    result = result.replaceAll("\n", "<br />");
    // ajusta as aspas
    result = result.replace('"', '\'');
    // retorna
    return result;
  }

}
