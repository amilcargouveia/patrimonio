package multiwork.xml.sql;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Respons�vel pela leitura do arquivo XML de par�metros de conex�o com o banco
 * de dados do arquivo indicado por CONNECTION_FILE_NAME.
 * <p>
 * Exemplo do formato do arquivo:
 * </p>
 * <p>
 * <pre>
 * &lt;connection&gt;
 *   &lt;driver name="" url=""/&gt;
 *   &lt;user name="" password=""/&gt;
 * &lt;/connection&gt;
 * </pre>
 * </p>
 */
public class ConnectionFile extends DefaultHandler {

  /**
   * Representa o elemento "driver" no arquivo de conex�o.
   */
  public class Driver {
    private String name = "";
    private String url  = "";
    public Driver(String name, String url) {
      this.name = name;
      this.url  = url;
    }
    public String getName() {
      return name;
    }
    public String getURL() {
      return url;
    }
  }

  /**
   * Representa o elemento "user" no arquivo de conex�o.
   */
  public class User {
    private String name = "";
    private String password = "";
    public User(String name, String password) {
      this.name = name;
      this.password = password;
    }
    public String getName() {
      return name;
    }
    public String getPassword() {
      return password;
    }
  }

  /**
   * Constante que indica o nome do arquivo de configura��o de conex�o.
   */
  private static final String CONNECTION_FILE_NAME = "connection.xml";
  // *
  private static final String DRIVER   = "driver";
  private static final String NAME     = "name";
  private static final String PASSWORD = "password";
  private static final String URL      = "url";
  private static final String USER     = "user";

  private Driver driver;
  private User user;

  /**
   * Construtor padr�o.
   * @param connectionFilePath Caminho local onde se encontra o arquivo de
   *                           conex�o. Veja CONNECTION_FILE_NAME.
   * @throws Exception Em caso de exce��o na tentativa de abertura do arquivo
   *                   XML especificado ou de inicializa��o do parser.
   */
  public ConnectionFile(String connectionFilePath) throws Exception {
    // nossa f�brica de parsers
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    // n�o validaremos o documento
    parserFactory.setValidating(false);
    // sem suporte para XML namespaces
    parserFactory.setNamespaceAware(false);
    // cria o parser
    SAXParser saxParser = parserFactory.newSAXParser();
    // inicia a an�lise
    saxParser.parse(connectionFilePath + CONNECTION_FILE_NAME, this);
  }

  /**
   * Retorna um Driver contendo as informa��es do elemento "driver" do arquivo
   * de configura��o.
   * @return Retorna um Driver contendo as informa��es do elemento "driver" do
   *         arquivo de configura��o.
   */
  public Driver driver() {
    return driver;
  }

  public void startElement(java.lang.String uri,
                           java.lang.String localName,
                           java.lang.String qName,
                           Attributes attributes) {
    // se encontramos o elemento "driver"
    if (qName.equalsIgnoreCase(DRIVER)) {
      driver = new Driver(attributes.getValue(NAME),
                          attributes.getValue(URL));
      // se encontramos o elemento "user"
    }
    else if (qName.equalsIgnoreCase(USER)) {
      user = new User(attributes.getValue(NAME),
                      attributes.getValue(PASSWORD));
    } // if
  }

  /**
   * Retorna um User contendo as informa��es do elemento "user" do arquivo
   * de configura��o.
   * @return Retorna um User contendo as informa��es do elemento "user" do
   *         arquivo de configura��o.
   */
  public User user() {
    return user;
  }

}
