package multiwork.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import multiwork.*;
import multiwork.ui.*;

/**
 * Representa o Servlet Controller da aplica��o respon�vel pelo redirecionamento
 * das requisi��es para os JSP�s e para a fachada dependendo da a��o recebida.
 */
public class Controller extends HttpServlet {

  private Facade              facade         = null;
  private HttpServletRequest  request        = null;
  private HttpServletResponse response       = null;

  public static final String ACTION  = "action";
  public static final String COMMAND = "command";
  public static final String FACADE  = "facade";
  // *
  public static final String ACTION_CHANGE_PASSWORD       = "changePassword";
  public static final String ACTION_EXTERNAL_LOOKUP       = "externalLookup";
  public static final String ACTION_HOME                  = "home";
  public static final String ACTION_LOGIN                 = "login";
  public static final String ACTION_SYSTEM_INFORMATION    = "systemInformation";
  public static final String ACTION_USUARIO               = "usuario";
  public static final String ACTION_USUARIO_CADASTRO      = "usuarioCadastro";
  // *
  public static final String BEAN_USER = "user";
  // *
  public static final String PARAM_ACTION_STATUS   = "actionStatus";
  public static final String PARAM_EXCEPTION       = "exception";
  public static final String PARAM_EXECUTED_ACTION = "executedAction";
  // *
  public static final String STATUS_OK = "OK";
  // *
  public static final String JSP_CHANGE_PASSWORD = "changepassword.jsp";
  public static final String JSP_ERROR           = "error.jsp";
  public static final String JSP_HOME            = "home.jsp";
  public static final String JSP_LOGIN           = "login.jsp";

  private static final String[][] actions = {
                                              {ACTION_CHANGE_PASSWORD   , "changepassword.jsp"},
                                              {ACTION_EXTERNAL_LOOKUP   , "ui/entity/externallookup.jsp"},
                                              {ACTION_HOME              , "home.jsp"},
                                              {ACTION_LOGIN             , "login.jsp"},
                                              {ACTION_SYSTEM_INFORMATION, "infosistema.jsp"},
                                              {ACTION_USUARIO           , "entity/usuario.jsp"},
                                              {ACTION_USUARIO_CADASTRO  , "entity/usuariocadastro.jsp"},
                                            };

  /**
   * Verifica a existencia do Facade na sess�o e guarda sua refer�ncia. Caso
   * n�o exista uma inst�ncia ser� criada, sua refer�ncia guardada e colocada
   * na sess�o.
   */
  private void checkFacade() {
    try {
      // procura a fachada na sess�o
      facade = (Facade)request.getSession().getAttribute(FACADE);
      // se n�o achamos...
      if (facade == null) {
        // cria
        facade = new Facade();
        // coloca-o na sess�o
        request.getSession().setAttribute(FACADE, facade);
      } // if
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

  /**
   * Processa as requisi��es Get recebidas.
   * @param request HttpServletRequest referente � requisi��o.
   * @param response HttpServletResponse referente � resposta.
   * @throws ServletException Em caso de exce��o na tentativa de realizar uma
   *                          das opera��es de redirecionamento e/ou execu��o
   *                          de comandos.
   * @throws IOException Em caso de exce��o na tentativa de realizar uma
   *                     das opera��es de redirecionamento e/ou execu��o
   *                     de comandos.
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGetPost(request, response);
  }

  /**
   * Processa as requisi��es Get e Post dos JSP�s e realiza o redirecionamento
   * para a outros JSP�s dependendo da a��o recebida.
   * @param request HttpServletRequest referente � requisi��o.
   * @param response HttpServletResponse referente � resposta.
   * @throws ServletException Em caso de exce��o na tentativa de realizar uma
   *                          das opera��es de redirecionamento e/ou execu��o
   *                          de comandos.
   * @throws IOException Em caso de exce��o na tentativa de realizar uma
   *                     das opera��es de redirecionamento e/ou execu��o
   *                     de comandos.
   */
  public void doGetPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // guarda nosso request e response
    this.request  = request;
    this.response = response;
    // verifica o fa�ade
    checkFacade();
    // a��o desejada
    String action = getParameter(ACTION);
    try {
      // se n�o efetuou logon...p�gina de login
      if (facade.getLoggedUser() == null)
        forward(JSP_LOGIN);
      // se n�o recebemos nenhuma a��o...vai para a p�gina inicial
      else if (action.equals(""))
        forward(JSP_HOME);
      // outro caso...
      else {
        // loop na nossa lista de a��es
        for (int i=0; i<actions.length; i++) {
          // a��o da vez
          String[] actionInfo = actions[i];
          // se achamos a a��o...redireciona para a p�gina definida
          if (actionInfo[0].equals(action)) {
            forward(actionInfo[1]);
            return;
          } // if
        } // for
        // loop na lista de a��es da fachada
        for (int i=0; i<facade.actions().length; i++) {
          // a��o da vez
          Action actionInfo = facade.actions()[i];
          // se achamos a a��o...redireciona para a p�gina definida
          if (actionInfo.getName().equals(action)) {
            forward(actionInfo.getJSP());
            return;
          } // if
        } // for
        // se chegou at� aqui...n�o encontramos nada
        throw new Exception(getClass().getName() + ".doGetPost(): recurso '" + action + "' n�o encontrado.");
      } // if
    }
    // em caso de exce��o...
    catch (Exception e) {
      // ...redireciona para a p�gina de erro!
      String param = PARAM_EXCEPTION + "=" + e.getMessage();
      forward(JSP_ERROR + "?" + param);
    } // try-catch
  }

  /**
   * Processa as requisi��es Post recebidas.
   * @param request HttpServletRequest referente � requisi��o.
   * @param response HttpServletResponse referente � resposta.
   * @throws ServletException Em caso de exce��o na tentativa de realizar uma
   *                          das opera��es de redirecionamento e/ou execu��o
   *                          de comandos.
   * @throws IOException Em caso de exce��o na tentativa de realizar uma
   *                     das opera��es de redirecionamento e/ou execu��o
   *                     de comandos.
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGetPost(request, response);
  }

  /**
   * Redireciona a requisi��o para o caminho especificado.
   * @param path Caminho para redirecionar a requisi��o.
   * @throws ServletException Em caso de exce��o na tentativa de realizar uma
   *                          das opera��es de redirecionamento e/ou execu��o
   *                          de comandos.
   * @throws IOException Em caso de exce��o na tentativa de realizar uma
   *                     das opera��es de redirecionamento e/ou execu��o
   *                     de comandos.
   */
  private void forward(String path) throws ServletException, IOException {
    // passa a bola para frente
    getServletContext().getRequestDispatcher("/" + path).forward(request, response);
  }

  /**
   * Redireciona a requisi��o para o caminho especificado passando como par�metros
   * a a��o que foi executada e seu status.
   * @param path Caminho para redirecionar a requisi��o.
   * @param executedAction Nome da a��o que foi executada.
   * @param actionStatus Status da a��o que foi executada.
   * @throws ServletException Em caso de exce��o na tentativa de realizar uma
   *                          das opera��es de redirecionamento e/ou execu��o
   *                          de comandos.
   * @throws IOException Em caso de exce��o na tentativa de realizar uma
   *                     das opera��es de redirecionamento e/ou execu��o
   *                     de comandos.
   */
  private void forwardStatus(String path,
                             String executedAction,
                             String actionStatus) throws ServletException, IOException {
    // passa a bola para frente informando o action executado e seu status
    forward(path + "?" + PARAM_EXECUTED_ACTION + "=" + executedAction
                 + "&" + PARAM_ACTION_STATUS + "=" + actionStatus);
  }

  /**
   * Retorna o valor do par�metro indicado por 'name' em request.
   * @param name Nome do par�metro cujo valor se deseja retornar.
   * @return Retorna o valor do par�metro indicado por 'name' em request.
   */
  private String getParameter(String name) {
    // procura pelo par�metro na requisi��o
    String result = request.getParameter(name);
    // se n�o achou...retorna string vazia
    if (result == null)
      result = "";
    // retorna
    return result;
  }

  /**
   * Inicializa o Servlet.
   * @throws ServletException Em caso de exce��o na tentativa de inicializa��o
   *                          do Servlet.
   */
  public void init() throws ServletException {
    try {
    }
    catch (Exception e) {
      throw new ServletException(e);
    } // try-catch
  }

}
