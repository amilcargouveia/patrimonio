package multiwork.process;

import java.sql.*;

import multiwork.sql.*;
import multiwork.util.*;

/**
 * Representa a classe base de todas as classes que representam processos
 * na aplicação.
 */
public class Process {

  private Connection connection = null;

  /**
   * Construtor padrão.
   */
  public Process() {
  }

  /**
   * Executa a expressão 'sql' informada e retorna true em caso de sucesso.
   * @param sql Expressão SQL para ser executada.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean execute(String sql) throws Exception {
    return SqlTools.execute(connection, sql);
  }

  /**
   * Retorna um ResultSet representando o resultado da execução da expressão
   * SQL informada. O método que faz a chamada a este deve ser responsável
   * pelo fechamento do ResultSet e do seu Statement.
   * @param sql Expressão SQL que se deseja executar.
   * @return Retorna um ResultSet representando o resultado da execução da
   *         expressão SQL informada. O método que faz a chamada a executeQuery
   *         é responsável pelo fechamento do ResultSet e do seu Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement para execução da expressão 'sql' informada.
   * O método que faz a chamada a prepare é responsável pelo fechamento do
   * Statement.
   * @param sql Expressão SQL para ser preparada.
   * @return Retorna um PreparedStatement para execução da expressão 'sql'
   *         informada. O método que faz a chamada a prepare é responsável pelo
   *         fechamento do Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepare(String sql) throws Exception {
    return SqlTools.prepare(connection, sql);
  }

  /**
   * Retorna um PreparedStatement para exclusão de registros em 'tableName' que
   * atendam a condição informada por 'where'.
   * @param tableName Nome da tabela alvo da exclusão.
   * @param where Expressão que limita os registros que serão excluídos.
   * @return Retorna um PreparedStatement para exclusão de registros em
   *         'tableName' que atendam a condição informada por 'where'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareDelete(String   tableName,
                                         String   where) throws Exception {
    return SqlTools.prepareDelete(connection, tableName, where);
  }

  /**
   * Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da inclusão.
   * @param fields Nomes dos campos cujos valores serão incluídos.
   * @return Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement prepareInsert(String   tableName,
                                         String[] fields) throws Exception {
    return SqlTools.prepareInsert(connection,
                                  tableName,
                                  fields);
  }

  /**
   * Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da inclusão.
   * @param fields Nomes dos campos cujos valores serão incluídos.
   * @param values Valores para serem atribuídos diretamente aos campos. Utilizado
   *               por exemplo em casos de campos constantes ou Sequences do
   *               Oracle. Deve estar na mesma ordem que 'fields' porém não
   *               é necessário incluir um valor para todos os campos.
   * @return Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * informada por 'orderFields' que atendam a condição informada por 'where'.
   * O método que faz a chamada a select é responsável pelo fechamento do
   * ResultSet e do seu Statement.
   * @param tableName Nome da tabela alvo da pesquisa.
   * @param fields Nomes dos campos que serão retornados.
   * @param orderFields Nomes dos que se deseja aplicar aos registros retornados.
   * @param where Condição que limita os registros que serão exibidos.
   * @return Retorna um PreparedStatement para uma consulta na tabela
   *         informada por 'tableName', com os campos informados por 'fields', na
   *         ordem informada por 'orderFields' que atendam a condição informada
   *         por 'where'. O método que faz a chamada a select é responsável pelo
   *         fechamento do ResultSet e do seu Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da alteração.
   * @param fields Nomes dos campos cujos valores serão alterados.
   * @param where Expressão que limita os registros que serão atualizados.
   * @return Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param tableName Nome da tabela alvo da alteração.
   * @param fields Nomes dos campos cujos valores serão alterados.
   * @param values Valores para serem atribuídos diretamente aos campos. Utilizado
   *               por exemplo em casos de campos constantes ou Sequences do
   *               Oracle. Deve estar na mesma ordem que 'fields' porém não
   *               é necessário incluir um valor para todos os campos.
   * @param where Expressão que limita os registros que serão atualizados.
   * @return Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
