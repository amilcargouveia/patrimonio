package multiwork.sql;

import java.sql.*;
import java.io.*;
import multiwork.xml.sql.*;

/**
 * Classe respons�vel pela ger�ncia das conex�es com o banco de dados.
 * Principais fun��es: <br><br>
 * 1. Carrega as configura��es da conex�o do arquivo 'connection.xml'. <br>
 * Este arquivo dever� ser criado no diret�rio onde a aplica��o estiver sendo
 * executada e no formato reconhecido pela classe ConnectionFile. <br><br>
 * 2. Cria um pool de conex�es a partir das informa��es do arquivo sendo as conex�es
 * l�gicas obtidas a partir desse pool.
 * @see ConnectionFile
 */
public class ConnectionManager {

  static private ConnectionManager connectionManager = null;

  private String     url        = null;
  private String     user       = null;
  private String     password   = null;

  /**
   * construtor padr�o
   */
  private ConnectionManager() {
  }

  /**
   * Retorna a inst�ncia de ConnectionManager para a aplica��o.
   * @return Retorna a inst�ncia de ConnectionManager para a aplica��o.
   */
  static public ConnectionManager getInstance() {
    if (connectionManager == null)
      connectionManager = new ConnectionManager();
    return connectionManager;
  }

  /**
   * Retorna uma conex�o l�gica a partir do pool de conex�es
   * @param connectionFilePath Caminho local onde se encontra o arquivo de
   *                           conex�o.
   * @return conex�o l�gica com o banco de dados a partir do pool
   * @throws Exception Em caso de exce��o na tentativa de pegar uma conex�o.
   */
  public Connection getConnection(String connectionFilePath) throws Exception {
    try {
      // nosso arquivo de conex�o
      ConnectionFile connectionFile = new ConnectionFile(connectionFilePath);
      // carrega o Driver
      Class.forName(connectionFile.driver().getName());
      // cria a conex�o
      Connection connection = DriverManager.getConnection(connectionFile.driver().getURL(),
                                                          connectionFile.user().getName(),
                                                          connectionFile.user().getPassword());
      // retorna a conex�o
      return connection;
    }
    catch(Exception e) {
      throw new RuntimeException(getClass().getName() + ".getConnection: " + e.getMessage()) ;
    } // try-catch
  }

}
