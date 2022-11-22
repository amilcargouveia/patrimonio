package multiwork.ui;

/**
 * Representa uma a��o a ser executada pelo usu�rio no sistema.
 */
public class Action {

  private String name      = "";
  private String jsp       = "";
  private String caption   = "";
  private String title     = "";
  private String menu      = "";
  private int    userLevel = 0;

  /**
   * Construtor padr�o.
   * @param name String Nome da a��o.
   * @param jsp String Nome da p�gina JSP.
   * @param caption String R�tulo da a��o que ser� exibido para o usu�rio.
   * @param title String T�tulo da a��o que ser� exibido para o usu�rio.
   * @param menu String T�tulo do menu onde ser� inserida.
   * @param userLevel int N�vel de seguran�a do usu�rio que permitir� acesso �
   *                  a��o.
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
