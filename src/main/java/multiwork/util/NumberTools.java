package multiwork.util;

import java.text.*;

/**
 * Classe utilit�ria para manipula��o de n�meros.
 */
public class NumberTools {

  /**
   * Retorna a quantidade de d�gitos existentes na parte fracion�ria de 'value'.
   * @param value double Valor cuja quantidade de d�gitos fracion�rios se deseja
   *                     obter.
   * @return int Retorna a quantidade de d�gitos existentes na parte fracion�ria
   *             de 'value'.
   */
  public static int countFractionDigits(double value) {
    // n�meros
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    // representa��o em String do valor recebido
    String strValue = Double.toString(value);
    // procura pelo separador decimal que dever� ser
    // o primeiro caractere, da direita para a esquerda,
    // que n�o esteja entre 0 e 9
    for (int i=0; i<strValue.length(); i++) {
      // caractere da vez
      String chr = strValue.charAt(strValue.length()-i-1) + "";
      // se n�o est� entre 0 e 9...
      // ...retorna a posi��o do caractere que
      // indica tamb�m a quantidade de casas decimais
      if (!StringTools.arrayContains(numbers, chr))
        return i;
    } // for
    // se chegou at� aqui...n�o temos casas decimais
    return 0;
  }

  /**
   * Retorna 'value' formatado de acordo com as configura��es da localidade
   * default.
   * @param value double Valor para ser formatado.
   * @param maxFractionDigits int N�mero m�ximo de casas decimais.
   * @param minimunFractionDigits int N�mero m�nimo de casas decimais.
   * @return String Retorna 'value' formatado de acordo com as configura��es da
   *                localidade default.
   */
  public static String format(double value,
                              int    maxFractionDigits,
                              int    minimunFractionDigits) {
    // nosso formatador
    NumberFormat format = DecimalFormat.getInstance();
    // configura
    format.setMaximumFractionDigits(maxFractionDigits);
    format.setMinimumFractionDigits(minimunFractionDigits);
    // retorna o valor
    return format.format(value);
  }

  /**
   * Retorna 'value' formatado de acordo com as configura��es da localidade
   * default com 2 casa decimais.
   * @param value double Valor para ser formatado.
   * @return String Retorna 'value' formatado de acordo com as configura��es da
   *         localidade default com 2 casa decimais.
   */
  public static String format(double value) {
    return format(value, 2, 2);
  }

  /**
   * Retorna o valor recebido com formata��o interpretado como double.
   * @param value String Valor formatado para ser interpretado.
   * @param maxFractionDigits int Quantidade m�xima de casas decimais aceit�veis.
   * @throws Exception No caso de exce��o na tentativa de interpretar o valor
   *                   recebido ou a quantidade de casas decimais exceder o
   *                   m�ximo permitido.
   * @return double Retorna o valor recebido com formata��o interpretado como double.
   */
  public static double parseDouble(String value,
                                   int    maxFractionDigits) throws Exception {
    // nosso formatador
    NumberFormat format = DecimalFormat.getInstance();
    // faz o parser
    Number number = format.parse(value);
    // se excedeu a quantidade de casas decimais...exce��o
    if (countFractionDigits(number.doubleValue()) > maxFractionDigits)
      throw new Exception("A quantidade m�xima de casas decimais (" + maxFractionDigits + ") " +
                          "foi excedida em: " + value + ".");
    // retorna o valor
    return number.doubleValue();
  }

  /**
   * Retorna o valor recebido com formata��o interpretado como double e permitindo
   * 2 casas decimais.
   * @param value String Valor formatado para ser interpretado.
   * @throws Exception No caso de exce��o na tentativa de interpretar o valor
   *                   recebido ou a quantidade de casas decimais exceder o
   *                   m�ximo permitido.
   * @return double Retorna o valor recebido com formata��o interpretado como double.
   */
  public static double parseDouble(String value) throws Exception {
    return parseDouble(value, 2);
  }

  /**
   * Retorna 'value' arrendondado para a precis�o informada.
   * @param value double Valor que se deseja arredondar.
   * @param precision int Precis�o do arredondamento.
   * @return double Retorna 'value' arrendondado para a precis�o informada.
   */
  public static double round(double value,
                             int    precision) {
    // multiplicador de precis�o
    double precisionMultiplier = Math.pow(10, precision);
    // multiplica o valor recebido
    value = value * precisionMultiplier;
    // arredonda
    value = Math.round(value);
    // divide pelo multiplicador retornando � pecis�o desejada
    value = value / precisionMultiplier;
    // retorna
    return value;
  }

  /**
   * Retorna 'value' arredondado com precis�o igual a 2.
   * @param value double Valor que se deseja arredondar.
   * @return double Retorna 'value' arredondado com precis�o igual a 2.
   */
  public static double round(double value) {
    return round(value, 2);
  }

}
