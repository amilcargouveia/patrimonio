package multiwork.util;

import java.sql.*;

/**
 * Classe utilitária com rotinas para inclusão, consulta, alteração, exclusão,
 * entre outros, em base de dados padrão SQL.
 */
public class SqlTools {

  /**
   * Executa a expressão 'sql' informada e retorna true em caso de sucesso.
   * @param connection Connection para ser utilizado na operação.
   * @param sql Expressão SQL para ser executada.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public static boolean execute(Connection connection,
                                String     sql) throws Exception {
    // cria o Statement
    Statement statement = connection.createStatement();
    // executa o SQL
    boolean result = statement.execute(sql);
    // fecha o Statement
    statement.close();
    // retorna
    return result;
  }

  /**
   * Retorna um ResultSet representando o resultado da execução da expressão
   * SQL informada. O método que faz a chamada a este deve ser responsável
   * pelo fechamento do ResultSet e do seu Statement.
   * @param connection Connection para ser utilizado na operação.
   * @param sql Expressão SQL que se deseja executar.
   * @return Retorna um ResultSet representando o resultado da execução da
   *         expressão SQL informada. O método que faz a chamada a executeQuery
   *         é responsável pelo fechamento do ResultSet e do seu Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public static ResultSet executeQuery(Connection connection,
                                       String     sql) throws Exception {
    // cria o Statement
    Statement statement = connection.createStatement();
    // retorna o ResultSet
    return statement.executeQuery(sql);
  }

  /**
   * Retorna o próximo valor da seqüência formada por 'sequenceFieldName'
   * em 'tableName'.
   * @param connection Connection para ser utilizado na operação.
   * @param tableName Nome da tabela.
   * @param sequenceFieldName Nome do campo formador da seqüência.
   * @return Retorna o próximo valor da seqüência formada por 'sequenceFieldName'
   *         em 'tableName'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   * dados.
   */
  public static int getNextSequence(Connection connection,
                                    String     tableName,
                                    String     sequenceFieldName) throws Exception {
    // retorna
    return getNextSequence(connection,
                           tableName,
                           sequenceFieldName,
                           "");
  }

  /**
   * Retorna o próximo valor da seqüência formada por 'sequenceFieldName'
   * em 'tableName'.
   * @param connection Connection para ser utilizado na operação.
   * @param tableName Nome da tabela.
   * @param sequenceFieldName Nome do campo formador da seqüência.
   * @param where Expressão que limita a faixa da seqüência que será gerada.
   * @return Retorna o próximo valor da seqüência formada por 'sequenceFieldName'
   *         em 'tableName'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   * dados.
   */
  public static int getNextSequence(Connection connection,
                                    String     tableName,
                                    String     sequenceFieldName,
                                    String     where) throws Exception {
    // faz a consulta
    String[] fields = {"MAX(" + sequenceFieldName + ") + 1"};
    PreparedStatement preparedStatement = prepareSelect(connection,
                                                        tableName,
                                                        fields,
                                                        new String[0],
                                                        where);
    try {
      preparedStatement.execute();
      preparedStatement.getResultSet().next();
      // nosso resultado
      int result = preparedStatement.getResultSet().getInt(1);
      if (result <= 0)
        result = 1;
      // retorna
      return result;
    }
    finally {
      preparedStatement.close();
    } // try
  }

  /**
   * Retorna um PreparedStatement para execução da expressão 'sql' informada.
   * O método que faz a chamada a prepare é responsável pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na operação.
   * @param sql Expressão SQL para ser preparada.
   * @return Retorna um PreparedStatement para execução da expressão 'sql'
   *         informada. O método que faz a chamada a prepare é responsável pelo
   *         fechamento do Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public static PreparedStatement prepare(Connection connection,
                                          String     sql) throws Exception {
    // executa o SQL
    return connection.prepareStatement(sql);
  }

  /**
   * Retorna um PreparedStatement para exclusão de registros em 'tableName' que
   * atendam a condição informada por 'where'.
   * @param connection Connection para ser utilizado na operação.
   * @param tableName Nome da tabela alvo da exclusão.
   * @param where Expressão que limita os registros que serão excluídos.
   * @return Retorna um PreparedStatement para exclusão de registros em
   *         'tableName' que atendam a condição informada por 'where'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public static PreparedStatement prepareDelete(Connection connection,
                                                String     tableName,
                                                String     where) throws Exception {
    // expressão SQL
    String sql = "DELETE FROM " + tableName;
    // temos where?
    if ((where != null) && (!where.equals("")))
      sql += " WHERE (" + where + ")";
    // executa
    return prepare(connection, sql);
  }

  /**
   * Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na operação.
   * @param tableName Nome da tabela alvo da inclusão.
   * @param fields Nomes dos campos cujos valores serão incluídos.
   * @return Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public static PreparedStatement prepareInsert(Connection connection,
                                                String     tableName,
                                                String[]   fields) throws Exception {
    return prepareInsert(connection,
                         tableName,
                         fields,
                         null);
  }


  /**
   * Retorna um PreparedStatement para inserção de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na operação.
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
  public static PreparedStatement prepareInsert(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String[]   values) throws Exception {
    // expressão SQL
    String sql = "INSERT INTO " + tableName + " ";
    // nomes dos campos
    sql += "(";
    for (int i=0; i<fields.length; i++) {
      // devemos colocar ',' ?
      if (i > 0)
        sql += ",";
      // campo da vez
      sql += fields[i];
    } // for
    sql += ")";
    // valores
    sql += " VALUES (";
    for (int i=0; i<fields.length; i++) {
      // devemos colocar ',' ?
      if (i > 0)
        sql += ",";
      // se temos um valor para o campo...usa-o
      if ((values != null) && (i < values.length))
        sql += values[i];
      // se não...será um parâmetro
      else
        sql += "?";
    } // for
    sql += ")";
    // executa
    return prepare(connection, sql);
  }

  /**
   * Retorna um PreparedStatement para uma consulta na tabela
   * informada por 'tableName', com os campos informados por 'fields', na ordem
   * informada por 'orderFields' que atendam a condição informada por 'where'.
   * O método que faz a chamada a select é responsável pelo fechamento do
   * ResultSet e do seu Statement.
   * @param connection Connection para ser utilizado na operação.
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
  public static PreparedStatement prepareSelect(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String[]   orderFields,
                                                String     where) throws Exception {
    // expressão SQL
    String sql = "SELECT ";
    // loop nos campos
    for (int i=0; i<fields.length; i++) {
      if (i>0)
        sql += ",";
      sql += fields[i];
    } // for
    // nome tabela
    sql += " FROM " + tableName;
    // temos where?
    if ((where != null) && (!where.equals("")))
      sql += " WHERE (" + where + ")";
    // loop nos campos de ordenação
    if (orderFields.length > 0) {
      sql += " ORDER BY ";
      for (int i=0; i<orderFields.length; i++) {
        if (i>0)
          sql += ",";
        sql += orderFields[i];
      } // for
    } // if
    // executa o SQL
    return prepare(connection, sql);
  }

  /**
   * Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na operação.
   * @param tableName Nome da tabela alvo da alteração.
   * @param fields Nomes dos campos cujos valores serão alterados.
   * @param where Expressão que limita os registros que serão atualizados.
   * @return Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public static PreparedStatement prepareUpdate(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String     where) throws Exception {
    return prepareUpdate(connection,
                         tableName,
                         fields,
                         null,
                         where);
  }

  /**
   * Retorna um PreparedStatement para atualização de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condição estabelecida por 'where'.
   * O método que faz a chamada a prepareInsert é responsável pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na operação.
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
  public static PreparedStatement prepareUpdate(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String[]   values,
                                                String     where) throws Exception {
    // expressão SQL
    String sql = "UPDATE " + tableName + " SET ";
    // loop nos campos
    for (int i=0; i<fields.length; i++) {
      // devemos colocar ',' ?
      if (i > 0)
        sql += ",";
      // campo da vez
      sql += fields[i] + "=";
      // se temos um valor para o campo...usa-o
      if ((values != null) && (i < values.length))
        sql += values[i];
      // se não...será um parâmetro
      else
        sql += "?";
    } // for
    // temos where?
    if ((where != null) && (!where.equals("")))
      sql += " WHERE (" + where + ")";
    // executa
    return prepare(connection, sql);
  }

}
