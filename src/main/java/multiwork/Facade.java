package multiwork;

import java.io.*;
import java.sql.*;
import java.util.*;

import multiwork.entity.*;
import multiwork.graph.*;
import multiwork.process.*;
import multiwork.report.*;
import multiwork.sql.*;
import multiwork.ui.*;
import multiwork.xml.ui.*;

/**
 * Representa a classe de fachada da aplica��o.
 */
public class Facade {

  private static final String CONFIGURATION_PATH = "conf";

  private ActionsFile     actionsFile     = null;
  private ApplicationFile applicationFile = null;
  private Connection      connection      = null;
  private UsuarioInfo     loggedUser      = null;
  // objetos de neg�cio
  private Usuario usuario = null;
  // *
  private Vector  businessObjects = new Vector();

  /**
   * Construtor padr�o. Verifica a existencia do usu�rio administrador da
   * aplica��o.
   * @throws Exception Em caso de exce��o na verifica��o do usu�rio administrador
   *                   padr�o.
   */
  public Facade() throws Exception {
    // pega uma conex�o com o banco
    connection = ConnectionManager.getInstance().getConnection(configurationLocalPath());
    // l� o arquivo de configura��o de a��es
    actionsFile = new ActionsFile(configurationLocalPath());
    // l� o arquivo de configura��o da aplica��o
    applicationFile = new ApplicationFile(configurationLocalPath());
    // Entity de usu�rio
    usuario = new Usuario(configurationLocalPath());
    usuario.setConnection(connection);
  }

  /**
   * Retorna um Action[] contendo a lista de a��es dispon�veis na aplica��o.
   * @return Retorna um Action[] contendo a lista de a��es dispon�veis na aplica��o.
   */
  public Action[] actions() {
    return actionsFile.actions();
  }

  /**
   * Retorna um ApplicationFile.Information contendo as informa��es da aplica��o.
   * @return Retorna um ApplicationFile.Information contendo as informa��es da
   *         aplica��o.
   */
  public ApplicationFile.Information applicationInformation() {
    return applicationFile.information();
  }

  /**
   * Retorna o caminho local de execu��o da aplica��o.
   * @return Retorna o caminho local de execu��o da aplica��o.
   */
  public String applicationLocalPath() {
    // pega o caminho completo de nossa classe
    String result = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    // se 'WEB-INF' est� contido no caminho...mant�m somente o que h� antes
    int webInfPos = result.indexOf("WEB-INF");
    if (webInfPos >= 0) {
      result = result.substring(0, webInfPos);
    } // if
    // se 'classes' est� contido no caminho...mant�m somente o que h� antes
    int classesPos = result.indexOf("classes");
    if (classesPos >= 0) {
      result = result.substring(0, classesPos);
    } // if
    // substitui '/' por separatorChar
    if (File.separatorChar != '/')
      result = result.replace('/', File.separatorChar);
    // se � um caminho do Windows (com :) e se inicia por '/' retira
    if ((result.indexOf(':') > 0) && (result.charAt(0) == File.separatorChar))
      result = result.substring(1, result.length()-1);
    // retorna com separatorChar no final
    if (result.charAt(result.length()-1) != File.separatorChar)
      result += File.separatorChar;
    // retorna
    return result;
  }

  /**
   * Retorna o caminho local onde se encontram as configura��es da aplica��o.
   * @return Retorna o caminho local onde se encontram as configura��es da
   *         aplica��o.
   */
  public String configurationLocalPath() {
    // retorna o caminho local da aplica��o mais o caminho de configura��o
    return applicationLocalPath()
         + CONFIGURATION_PATH
         + File.separatorChar;
  }

  /**
   * Retorna o Connection utilizado para acesso a banco.
   * @return Retorna o Connection utilizado para acesso a banco.
   */
  public Connection connection() {
    return connection;
  }

  /**
   * Procura por uma instancia de 'objectClass' no pool de objetos de neg�cio.
   * @param objectClass Classe do objeto de neg�cios que se deseja localizar.
   * @return Procura por uma instancia de 'objectClass' no pool de objetos de neg�cio.
   */
  private Object findBusinessObject(Class objectClass) {
    // loop nos objetos de neg�cio
    Object result = null;
    for (int i=0; i<businessObjects.size(); i++) {
      // objto da vez
      result = businessObjects.elementAt(i);
      // se � da classe desejada...retorna
      if (objectClass.equals(result.getClass()))
        return result;
    } // for
    // se n�o achamos...retorna nada
    return null;
  }

  /**
   * Retorna uma inst�ncia de 'className' configurada e pronta para uso.
   * @param className Nome da classe da entidade que se deseja uma inst�ncia.
   * @return Retorna uma inst�ncia de 'className' configurada e pronta para uso.
   * @throws Exception Em caso de exce��o na tentativa de instanciar o objeto
   *                   de neg�cio.
   */
  public Entity getEntity(String className) throws Exception {
    // carrega a classe
    Class entityClass = Class.forName(className);
    // j� existe uma instancia desta classe?
    Entity result = (Entity)findBusinessObject(entityClass);
    // se temos...retorna
    if (result != null)
      return result;
    // se n�o temos...cria
    else {
      // instancia
      result = (Entity)entityClass.newInstance();
      // p�e na nossa lista
      businessObjects.add(result);
      // configura
      result.setConnection(connection);
      // retorna
      return result;
    } // if
  }

//  /**
//   * Retorna uma inst�ncia de 'className' configurada e pronta para uso.
//   * @param className Nome da classe do gr�fico que se deseja uma inst�ncia.
//   * @return Retorna uma inst�ncia de 'className' configurada e pronta para uso.
//   * @throws Exception Em caso de exce��o na tentativa de instanciar o objeto
//   *                   de neg�cio.
//   */
//  public Graph getGraph(String className) throws Exception {
//    // carrega a classe
//    Class entityClass = Class.forName(className);
//    // j� existe uma instancia desta classe?
//    Graph result = (Graph)findBusinessObject(entityClass);
//    // se temos...retorna
//    if (result != null)
//      return result;
//    // se n�o temos...cria
//    else {
//      // instancia
//      result = (Graph)entityClass.newInstance();
//      // p�e na nossa lista
//      businessObjects.add(result);
//      // configura
//      result.setConnection(connection);
//      // retorna
//      return result;
//    } // if
//  }

  /**
   * Retorna uma inst�ncia de 'className' configurada e pronta para uso.
   * @param className Nome da classe do processo que se deseja uma inst�ncia.
   * @return Retorna uma inst�ncia de 'className' configurada e pronta para uso.
   * @throws Exception Em caso de exce��o na tentativa de instanciar o objeto
   *                   de neg�cio.
   */
  public multiwork.process.Process getProcess(String className) throws Exception {
    // carrega a classe
    Class processClass = Class.forName(className);
    // j� existe uma instancia desta classe?
    multiwork.process.Process result = (multiwork.process.Process)findBusinessObject(processClass);
    // se temos...retorna
    if (result != null)
      return result;
    // se n�o temos...cria
    else {
      // instancia
      result = (multiwork.process.Process)processClass.newInstance();
      // p�e na nossa lista
      businessObjects.add(result);
      // configura
      result.setConnection(connection);
      // retorna
      return result;
    } // if
  }

  /**
   * Retorna uma inst�ncia de 'className' configurada e pronta para uso.
   * @param className Nome da classe do relat�rio que se deseja uma inst�ncia.
   * @return Retorna uma inst�ncia de 'className' configurada e pronta para uso.
   * @throws Exception Em caso de exce��o na tentativa de instanciar o objeto
   *                   de neg�cio.
   */
  public Report getReport(String className) throws Exception {
    // carrega a classe
    Class reportClass = Class.forName(className);
    // j� existe uma instancia desta classe?
    Report result = (Report)findBusinessObject(reportClass);
    // se temos...retorna
    if (result != null)
      return result;
    // se n�o temos...cria
    else {
      // instancia
      result = (Report)reportClass.newInstance();
      // p�e na nossa lista
      businessObjects.add(result);
      // configura
      result.setConnection(connection);
      // retorna
      return result;
    } // if
  }

  /**
   * Retorna o UsuarioInfo referente ao Usu�rio que efetuou logon. Retorna null
   * se nenhum Usu�rio efetuou logon.
   * @return Retorna o UsuarioInfo referente ao Usu�rio que efetuou logon.
   *         Retorna null se nenhum Usu�rio efetuou logon.
   */
  public UsuarioInfo getLoggedUser() {
    return loggedUser;
  }

  /**
   * Define o UsuarioInfo referente ao Usu�rio que efetuou logon.
   * @param loggedUser UsuarioInfo referente ao Usu�rio que efetuou logon.
   */
  public void setLoggedUser(UsuarioInfo loggedUser) {
    this.loggedUser = loggedUser;
  }

  public Usuario usuario() {
    return usuario;
  }

}
