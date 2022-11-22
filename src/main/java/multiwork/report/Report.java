package multiwork.report;

import java.sql.*;

import multiwork.util.*;

/**
 * Representa a classe base de todas as classes que representam relatórios
 * na aplicação.
 */
public class Report {

  private Connection connection = null;

  /**
   * Construtor padrão.
   */
  public Report() {
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
   * Define o Connection utilizado para acesso ao banco de dados.
   * @param connection Connection utilizado para acesso ao banco de dados.
   */
  public void setConnection(Connection connection) {
    this.connection = connection;
  }

}
