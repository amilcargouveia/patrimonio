package multiwork.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import multiwork.*;
import multiwork.ui.*;

/**
 * Representa o Servlet Controller da aplicação responável pelo redirecionamento
 * das requisições para os JSP´s e para a fachada dependendo da ação recebida.
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
   * Verifica a existencia do Facade na sessão e guarda sua referência. Caso
   * não exista uma instância será criada, sua referência guardada e colocada
   * na sessão.
   */
  private void checkFacade() {
    try {
      // procura a fachada na sessão
      facade = (Facade)request.getSession().getAttribute(FACADE);
      // se não achamos...
      if (facade == null) {
        // cria
        facade = new Facade();
        // coloca-o na sessão
        request.getSession().setAttribute(FACADE, facade);
      } // if
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

  /**
   * Processa as requisições Get recebidas.
   * @param request HttpServletRequest referente à requisição.
   * @param response HttpServletResponse referente à resposta.
   * @throws ServletException Em caso de exceção na tentativa de realizar uma
   *                          das operações de redirecionamento e/ou execução
   *                          de comandos.
   * @throws IOException Em caso de exceção na tentativa de realizar uma
   *                     das operações de redirecionamento e/ou execução
   *                     de comandos.
   */
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGetPost(request, response);
  }

  /**
   * Processa as requisições Get e Post dos JSP´s e realiza o redirecionamento
   * para a outros JSP´s dependendo da ação recebida.
   * @param request HttpServletRequest referente à requisição.
   * @param response HttpServletResponse referente à resposta.
   * @throws ServletException Em caso de exceção na tentativa de realizar uma
   *                          das operações de redirecionamento e/ou execução
   *                          de comandos.
   * @throws IOException Em caso de exceção na tentativa de realizar uma
   *                     das operações de redirecionamento e/ou execução
   *                     de comandos.
   */
  public void doGetPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // guarda nosso request e response
    this.request  = request;
    this.response = response;
    // verifica o façade
    checkFacade();
    // ação desejada
    String action = getParameter(ACTION);
    try {
      // se não efetuou logon...página de login
      if (facade.getLoggedUser() == null)
        forward(JSP_LOGIN);
      // se não recebemos nenhuma ação...vai para a página inicial
      else if (action.equals(""))
        forward(JSP_HOME);
      // outro caso...
      else {
        // loop na nossa lista de ações
        for (int i=0; i<actions.length; i++) {
          // ação da vez
          String[] actionInfo = actions[i];
          // se achamos a ação...redireciona para a página definida
          if (actionInfo[0].equals(action)) {
            forward(actionInfo[1]);
            return;
          } // if
        } // for
        // loop na lista de ações da fachada
        for (int i=0; i<facade.actions().length; i++) {
          // ação da vez
          Action actionInfo = facade.actions()[i];
          // se achamos a ação...redireciona para a página definida
          if (actionInfo.getName().equals(action)) {
            forward(actionInfo.getJSP());
            return;
          } // if
        } // for
        // se chegou até aqui...não encontramos nada
        throw new Exception(getClass().getName() + ".doGetPost(): recurso '" + action + "' não encontrado.");
      } // if
    }
    // em caso de exceção...
    catch (Exception e) {
      // ...redireciona para a página de erro!
      String param = PARAM_EXCEPTION + "=" + e.getMessage();
      forward(JSP_ERROR + "?" + param);
    } // try-catch
  }

  /**
   * Processa as requisições Post recebidas.
   * @param request HttpServletRequest referente à requisição.
   * @param response HttpServletResponse referente à resposta.
   * @throws ServletException Em caso de exceção na tentativa de realizar uma
   *                          das operações de redirecionamento e/ou execução
   *                          de comandos.
   * @throws IOException Em caso de exceção na tentativa de realizar uma
   *                     das operações de redirecionamento e/ou execução
   *                     de comandos.
   */
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    doGetPost(request, response);
  }

  /**
   * Redireciona a requisição para o caminho especificado.
   * @param path Caminho para redirecionar a requisição.
   * @throws ServletException Em caso de exceção na tentativa de realizar uma
   *                          das operações de redirecionamento e/ou execução
   *                          de comandos.
   * @throws IOException Em caso de exceção na tentativa de realizar uma
   *                     das operações de redirecionamento e/ou execução
   *                     de comandos.
   */
  private void forward(String path) throws ServletException, IOException {
    // passa a bola para frente
    getServletContext().getRequestDispatcher("/" + path).forward(request, response);
  }

  /**
   * Redireciona a requisição para o caminho especificado passando como parâmetros
   * a ação que foi executada e seu status.
   * @param path Caminho para redirecionar a requisição.
   * @param executedAction Nome da ação que foi executada.
   * @param actionStatus Status da ação que foi executada.
   * @throws ServletException Em caso de exceção na tentativa de realizar uma
   *                          das operações de redirecionamento e/ou execução
   *                          de comandos.
   * @throws IOException Em caso de exceção na tentativa de realizar uma
   *                     das operações de redirecionamento e/ou execução
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
   * Retorna o valor do parâmetro indicado por 'name' em request.
   * @param name Nome do parâmetro cujo valor se deseja retornar.
   * @return Retorna o valor do parâmetro indicado por 'name' em request.
   */
  private String getParameter(String name) {
    // procura pelo parâmetro na requisição
    String result = request.getParameter(name);
    // se não achou...retorna string vazia
    if (result == null)
      result = "";
    // retorna
    return result;
  }

  /**
   * Inicializa o Servlet.
   * @throws ServletException Em caso de exceção na tentativa de inicialização
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
