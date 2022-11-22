package multiwork.util;

import java.util.*;
import java.sql.Timestamp;

/**
 * Classe utilitária para manipulação de data.
 */
public class DateTools {

  /**
   * Retorna 'value' como uma String e adiciona zeros à esquerda, fazendo
   * que o valor retornado tenha o tamanho 'length'.
   * @param value Valor para ser convertido.
   * @param length Tamanho final da String de retorno com zeros a esquerda.
   * @return Retorna 'value' como uma String e adiciona zeros à esquerda, fazendo
   *         que o valor retornado tenha o tamanho 'length'.
   */
  static public String completeZero(int value, int length) {
    String result = Integer.toString(value);
    while (result.length() < length)
      result = '0' + result;
    return result;
  }

  /**
   * Retorna a data/hora informada na representação String.
   * @param date Data/hora que se deseja no formato String.
   * @return Retorna a data/hora informada na representação String.
   */
  static public String dateTimeToString(Date date) {
    if (date == null)
      return "";
    else if (date.getTime() == 0)
      return "";
    String[] parts = dateTimeToStringParts(date);
    return parts[0]  + "/" + parts[1] + "/" + parts[2]
           + " " + parts[3] + ":" + parts[4];
  }


  /**
   * Retorna um String[dia, mes, ano, hora, minuto] referente a data/hora informada.
   * @param date Data/hora que se deseja obter as partes.
   * @return Retorna um String[dia, mes, ano, hora, minuto] referente a data/hora
   *         informada.
   */
  static public String[] dateTimeToStringParts(Date date) {
    if (date == null)
      return new String[5];
    else if (date.getTime() == 0)
      return new String[5];
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int day    = calendar.get(Calendar.DAY_OF_MONTH);
    int month  = calendar.get(Calendar.MONTH) + 1;
    int year   = calendar.get(Calendar.YEAR);
    int hour   = calendar.get(Calendar.HOUR_OF_DAY);
    int minute = calendar.get(Calendar.MINUTE);
    String[] result = new String[5];
    result[0] = completeZero(day, 2);
    result[1] = completeZero(month, 2);
    result[2] = completeZero(year, 4);
    result[3] = completeZero(hour, 2);
    result[4] = completeZero(minute, 2);
    return result;
  }

  /**
   * Retorna a data informada na representação String.
   * @param date Data que se deseja no formato String.
   * @return Retorna a data informada na representação String.
   */
  static public String dateToString(Date date) {
    if (date == null)
      return "";
    else if (date.getTime() == 0)
      return "";
    String[] parts = dateToStringParts(date);
    return parts[0]  + "/" + parts[1] + "/" + parts[2];
  }

  /**
   * Retorna a data informada no formato MM/YYYY.
   * @param date Data de origem.
   * @return Retorna a data informada no formato mes/ano.
   */
  static public String dateToStringMonthYear(Date date) {
    if (date == null)
      return "";
    else if (date.getTime() == 0)
      return "";
    String[] parts = dateToStringParts(date);
    return parts[1] + "/" + parts[2];
  }

  /**
   * Retorna um String[dia, mes, ano] referente a data informada.
   * @param date Data que se deseja obter as partes.
   * @return Retorna um String[dia, mes, ano] referente a data informada.
   */
  static public String[] dateToStringParts(Date date) {
    if (date == null)
      return new String[3];
    else if (date.getTime() == 0)
      return new String[3];
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int day   = calendar.get(Calendar.DAY_OF_MONTH);
    int month = calendar.get(Calendar.MONTH) + 1;
    int year  = calendar.get(Calendar.YEAR);
    String[] result = new String[3];
    result[0] = completeZero(day, 2);
    result[1] = completeZero(month, 2);
    result[2] = completeZero(year, 4);
    return result;
  }

  /**
   * Retorna o valor correspondente a 'field' da data atual do sistema.
   * @param field Valor que se deseja retorna da data atual do sistema. As
   *              constantes reconhecidas são as de java.util.Calendar.
   * @return Retorna o valor correspondente a 'field' da data atual do sistema.
   * @see java.util.Calendar
   */
  static public int getActual(int field) {
    java.util.Date now = new java.util.Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(now);
    return calendar.get(field);
  }

  /**
   * Retorna o dia do mês da data atual do sistema.
   * @return Retorna o dia do mês da data atual do sistema.
   */
  static public int getActualDayOfMonth() {
    return getActual(Calendar.DAY_OF_MONTH);
  }

  /**
   * Retorna o dia da semana da data atual do sistema.
   * @return Retorna o dia da semana da data atual do sistema.
   */
  static public int getActualDayOfWeek() {
    return getActual(Calendar.DAY_OF_WEEK);
  }

  /**
   * Retorna o dia do ano da data atual do sistema.
   * @return Retorna o dia atual do ano da data atual do sistema.
   */
  static public int getActualDayOfYear() {
    return getActual(Calendar.DAY_OF_YEAR);
  }

  /**
   * Retorna o mês da data atual do sistema.
   * @return Retorna o mês da data atual do sistema.
   */
  static public int getActualMonth() {
    return getActual(Calendar.MONTH)+1;
  }

  /**
   * Retorna o ano da data atual do sistema.
   * @return Retorna o ano da data atual do sistema.
   */
  static public int getActualYear() {
    return getActual(Calendar.YEAR);
  }


  /**
   * Retorna um Timestamp criado através da String informada.
   * @param value String no formato DD/MM/YYYY HH:MI.
   * @return Retorna um Timestamp criado através da String informada.
   */
  static public Timestamp stringDateTimeToTimestamp(String value) {
    if ((value == null) || (value.trim().equals("")))
      return new Timestamp(0);
    int firstBar = value.indexOf('/');
    int lastBar  = value.lastIndexOf('/');
    int space    = value.indexOf(' ');
    int twoPoints = value.indexOf(':');
    String day   = value.substring(0, firstBar);
    String month = value.substring(firstBar+1, lastBar);
    String year  = value.substring(lastBar+1, space);
    String hour  = value.substring(space+1, twoPoints);
    String minute = value.substring(twoPoints+1, value.length());
    if (hour.equals("24")) {
      hour   = "23";
      minute = "59";
    } // if
    return Timestamp.valueOf(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":00.000000000");
  }

  /**
   * Retorna um Timestamp criado através da String informada.
   * @param value String no formato DD/MM/YYYY.
   * @return Retorna um Timestamp criado através da String informada.
   */
  static public Timestamp stringDateToTimestamp(String value) {
    if ((value == null) || (value.trim().equals("")))
      return new Timestamp(0);
    int firstBar = value.indexOf('/');
    int lastBar  = value.lastIndexOf('/');
    String day   = value.substring(0, firstBar);
    String month = value.substring(firstBar+1, lastBar);
    String year  = value.substring(lastBar+1, value.length());
    return Timestamp.valueOf(year + "-" + month + "-" + day + " 00:00:00.000000000");
  }

  /**
   * Retorna um Timestamp criado através da String informada.
   * @param value String no formato MM/YYYY.
   * @return Retorna um Timestamp criado através da String informada.
   */
  static public Timestamp stringMonthYearToTimestamp(String value) {
    if ((value == null) || (value.trim().equals("")))
      return new Timestamp(0);
    int firstBar = value.indexOf('/');
    String month = value.substring(0, firstBar);
    String year  = value.substring(firstBar+1, value.length());
    return Timestamp.valueOf(year + "-" + month + "-01 00:00:00.000000000");
  }

  /**
   * Retorna a hora informada na representação String.
   * @param date Hora que se deseja no formato String.
   * @return Retorna a hora informada na representação String.
   */
  static public String timeToString(Date date) {
    if (date == null)
      return "";
    else if (date.getTime() == 0)
      return "";
    String[] parts = dateTimeToStringParts(date);
    return parts[3]  + ":" + parts[4];
  }

}
