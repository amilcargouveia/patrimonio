package multiwork.util;

import java.text.*;

/**
 * Classe utilitária para manipulação de números.
 */
public class NumberTools {

  /**
   * Retorna a quantidade de dígitos existentes na parte fracionária de 'value'.
   * @param value double Valor cuja quantidade de dígitos fracionários se deseja
   *                     obter.
   * @return int Retorna a quantidade de dígitos existentes na parte fracionária
   *             de 'value'.
   */
  public static int countFractionDigits(double value) {
    // números
    String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    // representação em String do valor recebido
    String strValue = Double.toString(value);
    // procura pelo separador decimal que deverá ser
    // o primeiro caractere, da direita para a esquerda,
    // que não esteja entre 0 e 9
    for (int i=0; i<strValue.length(); i++) {
      // caractere da vez
      String chr = strValue.charAt(strValue.length()-i-1) + "";
      // se não está entre 0 e 9...
      // ...retorna a posição do caractere que
      // indica também a quantidade de casas decimais
      if (!StringTools.arrayContains(numbers, chr))
        return i;
    } // for
    // se chegou até aqui...não temos casas decimais
    return 0;
  }

  /**
   * Retorna 'value' formatado de acordo com as configurações da localidade
   * default.
   * @param value double Valor para ser formatado.
   * @param maxFractionDigits int Número máximo de casas decimais.
   * @param minimunFractionDigits int Número mínimo de casas decimais.
   * @return String Retorna 'value' formatado de acordo com as configurações da
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
   * Retorna 'value' formatado de acordo com as configurações da localidade
   * default com 2 casa decimais.
   * @param value double Valor para ser formatado.
   * @return String Retorna 'value' formatado de acordo com as configurações da
   *         localidade default com 2 casa decimais.
   */
  public static String format(double value) {
    return format(value, 2, 2);
  }

  /**
   * Retorna o valor recebido com formatação interpretado como double.
   * @param value String Valor formatado para ser interpretado.
   * @param maxFractionDigits int Quantidade máxima de casas decimais aceitáveis.
   * @throws Exception No caso de exceção na tentativa de interpretar o valor
   *                   recebido ou a quantidade de casas decimais exceder o
   *                   máximo permitido.
   * @return double Retorna o valor recebido com formatação interpretado como double.
   */
  public static double parseDouble(String value,
                                   int    maxFractionDigits) throws Exception {
    // nosso formatador
    NumberFormat format = DecimalFormat.getInstance();
    // faz o parser
    Number number = format.parse(value);
    // se excedeu a quantidade de casas decimais...exceção
    if (countFractionDigits(number.doubleValue()) > maxFractionDigits)
      throw new Exception("A quantidade máxima de casas decimais (" + maxFractionDigits + ") " +
                          "foi excedida em: " + value + ".");
    // retorna o valor
    return number.doubleValue();
  }

  /**
   * Retorna o valor recebido com formatação interpretado como double e permitindo
   * 2 casas decimais.
   * @param value String Valor formatado para ser interpretado.
   * @throws Exception No caso de exceção na tentativa de interpretar o valor
   *                   recebido ou a quantidade de casas decimais exceder o
   *                   máximo permitido.
   * @return double Retorna o valor recebido com formatação interpretado como double.
   */
  public static double parseDouble(String value) throws Exception {
    return parseDouble(value, 2);
  }

  /**
   * Retorna 'value' arrendondado para a precisão informada.
   * @param value double Valor que se deseja arredondar.
   * @param precision int Precisão do arredondamento.
   * @return double Retorna 'value' arrendondado para a precisão informada.
   */
  public static double round(double value,
                             int    precision) {
    // multiplicador de precisão
    double precisionMultiplier = Math.pow(10, precision);
    // multiplica o valor recebido
    value = value * precisionMultiplier;
    // arredonda
    value = Math.round(value);
    // divide pelo multiplicador retornando à pecisão desejada
    value = value / precisionMultiplier;
    // retorna
    return value;
  }

  /**
   * Retorna 'value' arredondado com precisão igual a 2.
   * @param value double Valor que se deseja arredondar.
   * @return double Retorna 'value' arredondado com precisão igual a 2.
   */
  public static double round(double value) {
    return round(value, 2);
  }

}
