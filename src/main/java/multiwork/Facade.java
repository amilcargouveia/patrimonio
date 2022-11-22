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
 * Representa a classe de fachada da aplicação.
 */
public class Facade {

  private static final String CONFIGURATION_PATH = "conf";

  private ActionsFile     actionsFile     = null;
  private ApplicationFile applicationFile = null;
  private Connection      connection      = null;
  private UsuarioInfo     loggedUser      = null;
  // objetos de negócio
  private Usuario usuario = null;
  // *
  private Vector  businessObjects = new Vector();

  /**
   * Construtor padrão. Verifica a existencia do usuário administrador da
   * aplicação.
   * @throws Exception Em caso de exceção na verificação do usuário administrador
   *                   padrão.
   */
  public Facade() throws Exception {
    // pega uma conexão com o banco
    connection = ConnectionManager.getInstance().getConnection(configurationLocalPath());
    // lê o arquivo de configuração de ações
    actionsFile = new ActionsFile(configurationLocalPath());
    // lê o arquivo de configuração da aplicação
    applicationFile = new ApplicationFile(configurationLocalPath());
    // Entity de usuário
    usuario = new Usuario(configurationLocalPath());
    usuario.setConnection(connection);
  }

  /**
   * Retorna um Action[] contendo a lista de ações disponíveis na aplicação.
   * @return Retorna um Action[] contendo a lista de ações disponíveis na aplicação.
   */
  public Action[] actions() {
    return actionsFile.actions();
  }

  /**
   * Retorna um ApplicationFile.Information contendo as informações da aplicação.
   * @return Retorna um ApplicationFile.Information contendo as informações da
   *         aplicação.
   */
  public ApplicationFile.Information applicationInformation() {
    return applicationFile.information();
  }

  /**
   * Retorna o caminho local de execução da aplicação.
   * @return Retorna o caminho local de execução da aplicação.
   */
  public String applicationLocalPath() {
    // pega o caminho completo de nossa classe
    String result = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    // se 'WEB-INF' está contido no caminho...mantém somente o que há antes
    int webInfPos = result.indexOf("WEB-INF");
    if (webInfPos >= 0) {
      result = result.substring(0, webInfPos);
    } // if
    // se 'classes' está contido no caminho...mantém somente o que há antes
    int classesPos = result.indexOf("classes");
    if (classesPos >= 0) {
      result = result.substring(0, classesPos);
    } // if
    // substitui '/' por separatorChar
    if (File.separatorChar != '/')
      result = result.replace('/', File.separatorChar);
    // se é um caminho do Windows (com :) e se inicia por '/' retira
    if ((result.indexOf(':') > 0) && (result.charAt(0) == File.separatorChar))
      result = result.substring(1, result.length()-1);
    // retorna com separatorChar no final
    if (result.charAt(result.length()-1) != File.separatorChar)
      result += File.separatorChar;
    // retorna
    return result;
  }

  /**
   * Retorna o caminho local onde se encontram as configurações da aplicação.
   * @return Retorna o caminho local onde se encontram as configurações da
   *         aplicação.
   */
  public String configurationLocalPath() {
    // retorna o caminho local da aplicação mais o caminho de configuração
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
   * Procura por uma instancia de 'objectClass' no pool de objetos de negócio.
   * @param objectClass Classe do objeto de negócios que se deseja localizar.
   * @return Procura por uma instancia de 'objectClass' no pool de objetos de negócio.
   */
  private Object findBusinessObject(Class objectClass) {
    // loop nos objetos de negócio
    Object result = null;
    for (int i=0; i<businessObjects.size(); i++) {
      // objto da vez
      result = businessObjects.elementAt(i);
      // se é da classe desejada...retorna
      if (objectClass.equals(result.getClass()))
        return result;
    } // for
    // se não achamos...retorna nada
    return null;
  }

  /**
   * Retorna uma instância de 'className' configurada e pronta para uso.
   * @param className Nome da classe da entidade que se deseja uma instância.
   * @return Retorna uma instância de 'className' configurada e pronta para uso.
   * @throws Exception Em caso de exceção na tentativa de instanciar o objeto
   *                   de negócio.
   */
  public Entity getEntity(String className) throws Exception {
    // carrega a classe
    Class entityClass = Class.forName(className);
    // já existe uma instancia desta classe?
    Entity result = (Entity)findBusinessObject(entityClass);
    // se temos...retorna
    if (result != null)
      return result;
    // se não temos...cria
    else {
      // instancia
      result = (Entity)entityClass.newInstance();
      // põe na nossa lista
      businessObjects.add(result);
      // configura
      result.setConnection(connection);
      // retorna
      return result;
    } // if
  }

//  /**
//   * Retorna uma instância de 'className' configurada e pronta para uso.
//   * @param className Nome da classe do gráfico que se deseja uma instância.
//   * @return Retorna uma instância de 'className' configurada e pronta para uso.
//   * @throws Exception Em caso de exceção na tentativa de instanciar o objeto
//   *                   de negócio.
//   */
//  public Graph getGraph(String className) throws Exception {
//    // carrega a classe
//    Class entityClass = Class.forName(className);
//    // já existe uma instancia desta classe?
//    Graph result = (Graph)findBusinessObject(entityClass);
//    // se temos...retorna
//    if (result != null)
//      return result;
//    // se não temos...cria
//    else {
//      // instancia
//      result = (Graph)entityClass.newInstance();
//      // põe na nossa lista
//      businessObjects.add(result);
//      // configura
//      result.setConnection(connection);
//      // retorna
//      return result;
//    } // if
//  }

  /**
   * Retorna uma instância de 'className' configurada e pronta para uso.
   * @param className Nome da classe do processo que se deseja uma instância.
   * @return Retorna uma instância de 'className' configurada e pronta para uso.
   * @throws Exception Em caso de exceção na tentativa de instanciar o objeto
   *                   de negócio.
   */
  public multiwork.process.Process getProcess(String className) throws Exception {
    // carrega a classe
    Class processClass = Class.forName(className);
    // já existe uma instancia desta classe?
    multiwork.process.Process result = (multiwork.process.Process)findBusinessObject(processClass);
    // se temos...retorna
    if (result != null)
      return result;
    // se não temos...cria
    else {
      // instancia
      result = (multiwork.process.Process)processClass.newInstance();
      // põe na nossa lista
      businessObjects.add(result);
      // configura
      result.setConnection(connection);
      // retorna
      return result;
    } // if
  }

  /**
   * Retorna uma instância de 'className' configurada e pronta para uso.
   * @param className Nome da classe do relatório que se deseja uma instância.
   * @return Retorna uma instância de 'className' configurada e pronta para uso.
   * @throws Exception Em caso de exceção na tentativa de instanciar o objeto
   *                   de negócio.
   */
  public Report getReport(String className) throws Exception {
    // carrega a classe
    Class reportClass = Class.forName(className);
    // já existe uma instancia desta classe?
    Report result = (Report)findBusinessObject(reportClass);
    // se temos...retorna
    if (result != null)
      return result;
    // se não temos...cria
    else {
      // instancia
      result = (Report)reportClass.newInstance();
      // põe na nossa lista
      businessObjects.add(result);
      // configura
      result.setConnection(connection);
      // retorna
      return result;
    } // if
  }

  /**
   * Retorna o UsuarioInfo referente ao Usuário que efetuou logon. Retorna null
   * se nenhum Usuário efetuou logon.
   * @return Retorna o UsuarioInfo referente ao Usuário que efetuou logon.
   *         Retorna null se nenhum Usuário efetuou logon.
   */
  public UsuarioInfo getLoggedUser() {
    return loggedUser;
  }

  /**
   * Define o UsuarioInfo referente ao Usuário que efetuou logon.
   * @param loggedUser UsuarioInfo referente ao Usuário que efetuou logon.
   */
  public void setLoggedUser(UsuarioInfo loggedUser) {
    this.loggedUser = loggedUser;
  }

  public Usuario usuario() {
    return usuario;
  }

}
