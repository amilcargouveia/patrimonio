package multiwork.process;

import java.sql.*;

import multiwork.sql.*;
import multiwork.util.*;

/**
 * Representa a classe base de todas as classes que representam processos
 * na aplica��o.
 */
public class Process {

  private Connection connection = null;

  /**
   * Construtor padr�o.
   */
  public Process() {
  }

  /**
   * Executa a express�o 'sql' informada e retorna true em caso de sucesso.
   * @param sql Express�o SQL para ser executada.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean execute(String sql) throws Exception {
    return SqlTools.execute(connection, sql);
  }

  /**
   * Retorna um ResultSet representando o resultado da execu��o da express�o
   * SQL informada. O m�todo que faz a chamada a este deve ser respons�vel
   * pelo fechamento do ResultSet e do seu Statement.
   * @param sql Express�o SQL que se deseja executar.
   * @return Retorna um ResultSet representando o resultado da execu��o da
   *         express�o SQL informada. O m�todo que faz a chamada a executeQuery
   *         � respons�vel pelo fechamento do ResultSet e do seu Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public ResultSet executeQuery(String sql) throws Exception {
    return SqlTools.executeQuery(connection, sql);
  }

  /**
   * Retorna o Connection utilizado para acesso ao banco de dados.
   * @return Retorna o Connection utilizado para acesso ao banco de dados.
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * Retorna um PreparedStatement para execu��o da express�o 'sql' informada.
   * O m�todo que faz a chamada a prepare � respons�vel pelo fechamento do
   * Statement.
   * @param sql Express�o SQL para ser preparada.
   * @return Retorna um PreparedStatement para execu��o da express�o 'sql'
   *         informada. O m�todo que faz a chamada a prepare � respons�vel pelo
   *         fechamento do Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepare(String sql) throws Exception {
    return SqlTools.prepare(connection, sql);
  }

  /**
   * Retorna um PreparedStatement para exclus�o de registros em 'tableName' que
   * atendam a condi��o informada por 'where'.
   * @param tableName Nome da tabela alvo da exclus�o.
   * @param where Express�o que limita os registros que ser�o exclu�dos.
   * @return Retorna um PreparedStatement para exclus�o de registros em
   *         'tableName' que atendam a condi��o informada por 'where'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareDelete(String   tableName,
                                         String   where) throws Exception {
    return SqlTools.prepareDelete(connection, tableName, where);
  }

  /**
   * Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da inclus�o.
   * @param fields Nomes dos campos cujos valores ser�o inclu�dos.
   * @return Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareInsert(String   tableName,
                                         String[] fields) throws Exception {
    return SqlTools.prepareInsert(connection,
                                  tableName,
                                  fields);
  }

  /**
   * Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da inclus�o.
   * @param fields Nomes dos campos cujos valores ser�o inclu�dos.
   * @param values Valores para serem atribu�dos diretamente aos campos. Utilizado
   *               por exemplo em casos de campos constantes ou Sequences do
   *               Oracle. Deve estar na mesma ordem que 'fields' por�m n�o
   *               � necess�rio incluir um valor para todos os campos.
   * @return Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareInsert(String   tableName,
                                         String[] fields,
                                         String[] values) throws Exception {
    return SqlTools.prepareInsert(connection,
                                  tableName,
                                  fields,
                                  values);
  }

  /**
   * Retorna um PreparedStatement para uma consulta na tabela
   * informada por 'tableName', com os campos informados por 'fields', na ordem
   * informada por 'orderFields' que atendam a condi��o informada por 'where'.
   * O m�todo que faz a chamada a select � respons�vel pelo fechamento do
   * ResultSet e do seu Statement.
   * @param tableName Nome da tabela alvo da pesquisa.
   * @param fields Nomes dos campos que ser�o retornados.
   * @param orderFields Nomes dos que se deseja aplicar aos registros retornados.
   * @param where Condi��o que limita os registros que ser�o exibidos.
   * @return Retorna um PreparedStatement para uma consulta na tabela
   *         informada por 'tableName', com os campos informados por 'fields', na
   *         ordem informada por 'orderFields' que atendam a condi��o informada
   *         por 'where'. O m�todo que faz a chamada a select � respons�vel pelo
   *         fechamento do ResultSet e do seu Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareSelect(String   tableName,
                                         String[] fields,
                                         String[] orderFields,
                                         String   where) throws Exception {
    return SqlTools.prepareSelect(connection,
                                  tableName,
                                  fields,
                                  orderFields,
                                  where);
  }

  /**
   * Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da altera��o.
   * @param fields Nomes dos campos cujos valores ser�o alterados.
   * @param where Express�o que limita os registros que ser�o atualizados.
   * @return Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareUpdate(String   tableName,
                                         String[] fields,
                                         String   where) throws Exception {
    return SqlTools.prepareUpdate(connection,
                                  tableName,
                                  fields,
                                  where);
  }

  /**
   * Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da altera��o.
   * @param fields Nomes dos campos cujos valores ser�o alterados.
   * @param values Valores para serem atribu�dos diretamente aos campos. Utilizado
   *               por exemplo em casos de campos constantes ou Sequences do
   *               Oracle. Deve estar na mesma ordem que 'fields' por�m n�o
   *               � necess�rio incluir um valor para todos os campos.
   * @param where Express�o que limita os registros que ser�o atualizados.
   * @return Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareUpdate(String   tableName,
                                         String[] fields,
                                         String[] values,
                                         String   where) throws Exception {
    return SqlTools.prepareUpdate(connection,
                                  tableName,
                                  fields,
                                  values,
                                  where);
  }

  /**
   * Define o Connection utilizado para acesso ao banco de dados.
   * @param connection Connection utilizado para acesso ao banco de dados.
   */
  public void setConnection(Connection connection) {
    this.connection = connection;
  }

}
