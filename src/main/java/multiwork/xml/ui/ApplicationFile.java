package multiwork.xml.ui;

import java.util.*;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Responsável pela leitura do arquivo XML de configurações da aplicação
 * indicado por APPLICATION_FILE_NAME.
 * <p>
 * Exemplo do formato do arquivo:
 * </p>
 * <p>
 * <pre>
 * &lt;application&gt;
 *   &lt;information name="nome" title="titulo" /&gt;
 * &lt;/application&gt;
 * </pre>
 * </p>
 */
public class ApplicationFile extends DefaultHandler {

  public class Information {
    private String name  = "";
    private String title = "";
    public Information(String name, String title) {
      this.name  = name;
      this.title = title;
    }
    public String getName() { return name; }
    public String getTitle() { return title; }
  }

  /**
   * Constante que indica o nome do arquivo de configuração da aplicação.
   */
  private static final String APPLICATION_FILE_NAME = "application.xml";
  // *
  private static final String INFORMATION = "information";
  private static final String NAME        = "name";
  private static final String TITLE       = "title";
  // *
  private Information information = null;

  /**
   * Construtor padrão.
   * @param applicationFilePath Caminho local onde se encontra o arquivo de
   *                        configurações da aplicação. Veja APPLICATION_FILE_NAME.
   * @throws Exception Em caso de exceção na tentativa de abertura do arquivo
   *                   XML especificado ou de inicialização do parser.
   */
  public ApplicationFile(String applicationFilePath) throws Exception {
    // nossa fábrica de parsers
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    // não validaremos o documento
    parserFactory.setValidating(false);
    // sem suporte para XML namespaces
    parserFactory.setNamespaceAware(false);
    // cria o parser
    SAXParser saxParser = parserFactory.newSAXParser();
    // inicia a análise
    saxParser.parse(applicationFilePath + APPLICATION_FILE_NAME, this);
  }

  public Information information() {
    return information;
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    // achamos o nó de informações?
    if (qName.equalsIgnoreCase(INFORMATION)) {
      information = new Information(attributes.getValue(NAME),
                                    attributes.getValue(TITLE));
    } // if
  }

}
