package multiwork.xml.sql;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Responsável pela leitura do arquivo XML de parâmetros de conexão com o banco
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
   * Representa o elemento "driver" no arquivo de conexão.
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
   * Representa o elemento "user" no arquivo de conexão.
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
   * Constante que indica o nome do arquivo de configuração de conexão.
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
   * Construtor padrão.
   * @param connectionFilePath Caminho local onde se encontra o arquivo de
   *                           conexão. Veja CONNECTION_FILE_NAME.
   * @throws Exception Em caso de exceção na tentativa de abertura do arquivo
   *                   XML especificado ou de inicialização do parser.
   */
  public ConnectionFile(String connectionFilePath) throws Exception {
    // nossa fábrica de parsers
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    // não validaremos o documento
    parserFactory.setValidating(false);
    // sem suporte para XML namespaces
    parserFactory.setNamespaceAware(false);
    // cria o parser
    SAXParser saxParser = parserFactory.newSAXParser();
    // inicia a análise
    saxParser.parse(connectionFilePath + CONNECTION_FILE_NAME, this);
  }

  /**
   * Retorna um Driver contendo as informações do elemento "driver" do arquivo
   * de configuração.
   * @return Retorna um Driver contendo as informações do elemento "driver" do
   *         arquivo de configuração.
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
   * Retorna um User contendo as informações do elemento "user" do arquivo
   * de configuração.
   * @return Retorna um User contendo as informações do elemento "user" do
   *         arquivo de configuração.
   */
  public User user() {
    return user;
  }

}
