package multiwork.util;

import javax.servlet.http.*;

/**
 * Classe utilitária para operações com elementos HTTP.
 */

public class HttpTools {

  /**
   * Retorna o Cookie contido em 'request' com nome indicado em 'name'.
   * @param request HttpServletRequest Requisição onde o cookie sera pesquisado.
   * @param name String Nome do cookie que se deseja retornar.
   * @return Cookie Retorna o Cookie contido em 'request' com nome indicado em 'name'.
   */
  public static Cookie getCookie(HttpServletRequest request,
                                 String             name) {
    Cookie[] cookies = null;
    try {
      // pega os Cookies
      cookies = request.getCookies();
    }
    catch (Exception e) {
    } // try-catch
    // loop a procura do Cookie desejado
    Cookie result;
    for (int i = 0; i<cookies.length; i++) {
      // cookie da vez
      result = cookies[i];
      // se achamos...retorna
      if (result.getName().equalsIgnoreCase(name))
        return result;
    } // for
    // se chegou até aqui...não achamos
    return null;
  }

  /**
   * Retorna o valor do cookie contido em 'request' com nome indicado em 'name'.
   * @param request HttpServletRequest Requisição onde o cookie sera pesquisado.
   * @param name String Nome do cookie cujo valor se deseja retornar.
   * @return String Retorna o valor do cookie contido em 'request' com nome
   *                indicado em 'name'.
   */
  public static String getCookieValue(HttpServletRequest request,
                                      String             name) {
    // procura o Cookie
    Cookie cookie = getCookie(request, name);
    // se achamos...retorna seu valor
    return cookie != null ? cookie.getValue() : null;
  }

}
