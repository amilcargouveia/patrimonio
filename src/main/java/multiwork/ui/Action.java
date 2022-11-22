package multiwork.ui;

/**
 * Representa uma ação a ser executada pelo usuário no sistema.
 */
public class Action {

  private String name      = "";
  private String jsp       = "";
  private String caption   = "";
  private String title     = "";
  private String menu      = "";
  private int    userLevel = 0;

  /**
   * Construtor padrão.
   * @param name String Nome da ação.
   * @param jsp String Nome da página JSP.
   * @param caption String Rótulo da ação que será exibido para o usuário.
   * @param title String Título da ação que será exibido para o usuário.
   * @param menu String Título do menu onde será inserida.
   * @param userLevel int Nível de segurança do usuário que permitirá acesso à
   *                  ação.
   */
  public Action(String name,
                String jsp,
                String caption,
                String title,
                String menu,
                int    userLevel) {
    this.name      = name;
    this.caption   = caption;
    this.jsp       = jsp;
    this.title     = title;
    this.menu      = menu;
    this.userLevel = userLevel;
  }

  public String getCaption() {
    return caption;
  }

  public String getName() {
    return name;
  }

  public String getJSP() {
    return jsp;
  }

  public String getTitle() {
    return title;
  }

  public String getMenu() {
    return menu;
  }

  public int getUserLevel() {
    return userLevel;
  };

}
