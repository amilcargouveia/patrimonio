package multiwork.xml.entity;

import java.util.*;
import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * Responsável pela leitura do arquivo XML de configurações de Usuario
 * indicado por USUARIO_FILE_NAME.
 * <p>
 * Exemplo do formato do arquivo:
 * </p>
 * <p>
 * <pre>
 * &lt;usuario tablename="tabela"&gt;
 *   &lt;fieldcodigo name="codigo" /&gt;
 *   &lt;fieldnome   name="nome" /&gt;
 *   &lt;fieldnivel  name="nivel" /&gt;
 *   &lt;fieldsenha  name="senha" /&gt;
 * &lt;administrator username="admin" password="admin" /&gt;
</usuario>

 * </pre>
 * </p>
 */
public class UsuarioFile extends DefaultHandler {

  /**
   * Constante que indica o nome do arquivo de configuração de Usuário.
   */
  private static final String USUARIO_FILE_NAME = "usuario.xml";
  // *
  private static final String ADMINISTRATOR   = "administrator";
  private static final String FIELD_CODIGO    = "fieldcodigo";
  private static final String TABLE_NAME      = "tablename";
  private static final String FIELD_NIVEL     = "fieldnivel";
  private static final String FIELD_NOME      = "fieldnome";
  private static final String FIELD_SENHA     = "fieldsenha";
  private static final String NAME            = "name";
  private static final String PASSWORD        = "password";
  private static final String USER_NAME       = "username";
  private static final String USUARIO         = "usuario";

  private String tableName      = "";
  private String fieldCodigo    = "";
  private String fieldNivel     = "";
  private String fieldNome      = "";
  private String fieldSenha     = "";
  private String adminUserName  = "";
  private String adminPassword  = "";

  /**
   * Construtor padrão.
   * @param usuarioFilePath Caminho local onde se encontra o arquivo de
   *                        configurações de Usuário. Veja USUARIO_FILE_NAME.
   * @throws Exception Em caso de exceção na tentativa de abertura do arquivo
   *                   XML especificado ou de inicialização do parser.
   */
  public UsuarioFile(String usuarioFilePath) throws Exception {
    // nossa fábrica de parsers
    SAXParserFactory parserFactory = SAXParserFactory.newInstance();
    // não validaremos o documento
    parserFactory.setValidating(false);
    // sem suporte para XML namespaces
    parserFactory.setNamespaceAware(false);
    // cria o parser
    SAXParser saxParser = parserFactory.newSAXParser();
    // inicia a análise
    saxParser.parse(usuarioFilePath + USUARIO_FILE_NAME, this);
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    // raiz
    if (qName.equals(USUARIO))
      tableName = attributes.getValue(TABLE_NAME);
    // campo Código
    else if (qName.equals(FIELD_CODIGO))
      fieldCodigo = attributes.getValue(NAME);
    // campo Nível
    else if (qName.equals(FIELD_NIVEL))
      fieldNivel = attributes.getValue(NAME);
    // campo Nome
    else if (qName.equals(FIELD_NOME))
      fieldNome = attributes.getValue(NAME);
    // campo Senha
    else if (qName.equals(FIELD_SENHA))
      fieldSenha = attributes.getValue(NAME);
    // Administrador
    else if (qName.equals(ADMINISTRATOR)) {
      adminUserName = attributes.getValue(USER_NAME);
      adminPassword = attributes.getValue(PASSWORD);
    }
  }
  public String adminPassword() {
    return adminPassword;
  }

  public String adminUserName() {
    return adminUserName;
  }

  public String fieldCodigo() {
    return fieldCodigo;
  }

  public String fieldNivel() {
    return fieldNivel;
  }

  public String fieldNome() {
    return fieldNome;
  }

  public String fieldSenha() {
    return fieldSenha;
  }

  public String tableName() {
    return tableName;
  }

}
