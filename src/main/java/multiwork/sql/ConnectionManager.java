package multiwork.sql;

import java.sql.*;
import java.io.*;
import multiwork.xml.sql.*;

/**
 * Classe responsável pela gerência das conexões com o banco de dados.
 * Principais funções: <br><br>
 * 1. Carrega as configurações da conexão do arquivo 'connection.xml'. <br>
 * Este arquivo deverá ser criado no diretório onde a aplicação estiver sendo
 * executada e no formato reconhecido pela classe ConnectionFile. <br><br>
 * 2. Cria um pool de conexões a partir das informações do arquivo sendo as conexões
 * lógicas obtidas a partir desse pool.
 * @see ConnectionFile
 */
public class ConnectionManager {

  static private ConnectionManager connectionManager = null;

  private String     url        = null;
  private String     user       = null;
  private String     password   = null;

  /**
   * construtor padrão
   */
  private ConnectionManager() {
  }

  /**
   * Retorna a instância de ConnectionManager para a aplicação.
   * @return Retorna a instância de ConnectionManager para a aplicação.
   */
  static public ConnectionManager getInstance() {
    if (connectionManager == null)
      connectionManager = new ConnectionManager();
    return connectionManager;
  }

  /**
   * Retorna uma conexão lógica a partir do pool de conexões
   * @param connectionFilePath Caminho local onde se encontra o arquivo de
   *                           conexão.
   * @return conexão lógica com o banco de dados a partir do pool
   * @throws Exception Em caso de exceção na tentativa de pegar uma conexão.
   */
  public Connection getConnection(String connectionFilePath) throws Exception {
    try {
      // nosso arquivo de conexão
      ConnectionFile connectionFile = new ConnectionFile(connectionFilePath);
      // carrega o Driver
      Class.forName(connectionFile.driver().getName());
      // cria a conexão
      Connection connection = DriverManager.getConnection(connectionFile.driver().getURL(),
                                                          connectionFile.user().getName(),
                                                          connectionFile.user().getPassword());
      // retorna a conexão
      return connection;
    }
    catch(Exception e) {
      throw new RuntimeException(getClass().getName() + ".getConnection: " + e.getMessage()) ;
    } // try-catch
  }

}
