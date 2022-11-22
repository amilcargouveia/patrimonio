package multiwork.xml.ui;

import java.util.*;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import multiwork.ui.*;

/**
 * Respons�vel pela leitura do arquivo XML de configura��es de a��es da aplica��o
 * indicado por ACTIONS_FILE_NAME.
 * <p>
 * Exemplo do formato do arquivo:
 * </p>
 * <p>
 * <pre>
 * &lt;actions&gt;
 *   &lt;action name="acao1" jsp="paginaacao1.jsp" caption="descricao acao 1" title="titulo acao 1" menu="nome menu" userlevel="1" /&gt;
 *   &lt;action name="acao2" jsp="paginaacao2.jsp" caption="descricao acao 2" title="titulo acao 2" menu="nome menu" userlevel="3" /&gt;
 * &lt;/actions&gt;
 * </pre>
 * </p>
 *
 */
public class ActionsFile extends DefaultHandler {

  /**
   * Constante que indica o nome do arquivo de configura��o de a��es.
   */
  private static final String ACTIONS_FILE_NAME = "actions.xml";
  // *
  private static final String ACTION            = "action";
  private static final String CAPTION           = "caption";
  private static final String NAME              = "name";
  private static final String JSP               = "jsp";
  private static final String TITLE             = "title";
  private static final String MENU              = "menu";
  private static final String USER_LEVEL        = "userlevel";
  // *
  private Action[] actions = null;
  private Vector   vector  = new Vector();

  /**
   * Construtor padr�o.
   * @param actionsFilePath Caminho local onde se encontra o arquivo de
   *                        a��es. Veja ACTIONS_FILE_NAME.
   * @throws Exception Em caso de exce��o na tentativa de abertura do arquivo
   *                   XML especificado ou de inicializa��o do parser.
   */
  public ActionsFile(String actionsFilePath) throws Exception {
    // nossa f�brica de parsers
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    // n�o validaremos o documento
    parserFactory.setValidating(false);
    // sem suporte para XML namespaces
    parserFactory.setNamespaceAware(false);
    // cria o parser
    SAXParser saxParser = parserFactory.newSAXParser();
    // inicia a an�lise
    saxParser.parse(actionsFilePath + ACTIONS_FILE_NAME, this);
    // guarda no array
    moveActionsIntoArray();
  }

  /**
   * Retorna um Action[] contendo a lista de Action's encontrados no arquivo de
   * configura��o de Action's.
   * @return Retorna um Action[] contendo a lista de Action's encontrados no
   *         arquivo de configura��o de Action's.
   */
  public Action[] actions() {
    return actions;
  }

  /**
   * Move os Action's lidos pelo parser de 'vector' para 'actions'.
   */
  private void moveActionsIntoArray() {
    // cria a array de Action's com o tamanho certo
    actions = new Action[vector.size()];
    // copia para a array
    vector.copyInto(actions);
    // limpa o vector
    vector.clear();
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    // achamos um elemento Action?
    if (qName.equalsIgnoreCase(ACTION)) {
      // l� o Action
      Action action = new Action(attributes.getValue(NAME),
                                 attributes.getValue(JSP),
                                 attributes.getValue(CAPTION),
                                 attributes.getValue(TITLE),
                                 attributes.getValue(MENU),
                                 Integer.parseInt(attributes.getValue(USER_LEVEL)));
      // adiciona no Vector
      vector.add(action);
    } // if
  }

}
