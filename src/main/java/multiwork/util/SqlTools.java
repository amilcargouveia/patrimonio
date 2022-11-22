package multiwork.util;

import java.sql.*;

/**
 * Classe utilit�ria com rotinas para inclus�o, consulta, altera��o, exclus�o,
 * entre outros, em base de dados padr�o SQL.
 */
public class SqlTools {

  /**
   * Executa a express�o 'sql' informada e retorna true em caso de sucesso.
   * @param connection Connection para ser utilizado na opera��o.
   * @param sql Express�o SQL para ser executada.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um ResultSet representando o resultado da execu��o da express�o
   * SQL informada. O m�todo que faz a chamada a este deve ser respons�vel
   * pelo fechamento do ResultSet e do seu Statement.
   * @param connection Connection para ser utilizado na opera��o.
   * @param sql Express�o SQL que se deseja executar.
   * @return Retorna um ResultSet representando o resultado da execu��o da
   *         express�o SQL informada. O m�todo que faz a chamada a executeQuery
   *         � respons�vel pelo fechamento do ResultSet e do seu Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna o pr�ximo valor da seq��ncia formada por 'sequenceFieldName'
   * em 'tableName'.
   * @param connection Connection para ser utilizado na opera��o.
   * @param tableName Nome da tabela.
   * @param sequenceFieldName Nome do campo formador da seq��ncia.
   * @return Retorna o pr�ximo valor da seq��ncia formada por 'sequenceFieldName'
   *         em 'tableName'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna o pr�ximo valor da seq��ncia formada por 'sequenceFieldName'
   * em 'tableName'.
   * @param connection Connection para ser utilizado na opera��o.
   * @param tableName Nome da tabela.
   * @param sequenceFieldName Nome do campo formador da seq��ncia.
   * @param where Express�o que limita a faixa da seq��ncia que ser� gerada.
   * @return Retorna o pr�ximo valor da seq��ncia formada por 'sequenceFieldName'
   *         em 'tableName'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement para execu��o da express�o 'sql' informada.
   * O m�todo que faz a chamada a prepare � respons�vel pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na opera��o.
   * @param sql Express�o SQL para ser preparada.
   * @return Retorna um PreparedStatement para execu��o da express�o 'sql'
   *         informada. O m�todo que faz a chamada a prepare � respons�vel pelo
   *         fechamento do Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public static PreparedStatement prepare(Connection connection,
                                          String     sql) throws Exception {
    // executa o SQL
    return connection.prepareStatement(sql);
  }

  /**
   * Retorna um PreparedStatement para exclus�o de registros em 'tableName' que
   * atendam a condi��o informada por 'where'.
   * @param connection Connection para ser utilizado na opera��o.
   * @param tableName Nome da tabela alvo da exclus�o.
   * @param where Express�o que limita os registros que ser�o exclu�dos.
   * @return Retorna um PreparedStatement para exclus�o de registros em
   *         'tableName' que atendam a condi��o informada por 'where'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public static PreparedStatement prepareDelete(Connection connection,
                                                String     tableName,
                                                String     where) throws Exception {
    // express�o SQL
    String sql = "DELETE FROM " + tableName;
    // temos where?
    if ((where != null) && (!where.equals("")))
      sql += " WHERE (" + where + ")";
    // executa
    return prepare(connection, sql);
  }

  /**
   * Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na opera��o.
   * @param tableName Nome da tabela alvo da inclus�o.
   * @param fields Nomes dos campos cujos valores ser�o inclu�dos.
   * @return Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement para inser��o de registros na tabela
   * informada por 'tableName' preenchendo os campos informados por 'fields'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na opera��o.
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
  public static PreparedStatement prepareInsert(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String[]   values) throws Exception {
    // express�o SQL
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
      // se n�o...ser� um par�metro
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
   * informada por 'orderFields' que atendam a condi��o informada por 'where'.
   * O m�todo que faz a chamada a select � respons�vel pelo fechamento do
   * ResultSet e do seu Statement.
   * @param connection Connection para ser utilizado na opera��o.
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
  public static PreparedStatement prepareSelect(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String[]   orderFields,
                                                String     where) throws Exception {
    // express�o SQL
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
    // loop nos campos de ordena��o
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
   * Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na opera��o.
   * @param tableName Nome da tabela alvo da altera��o.
   * @param fields Nomes dos campos cujos valores ser�o alterados.
   * @param where Express�o que limita os registros que ser�o atualizados.
   * @return Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement para atualiza��o de registros na tabela
   * informada por 'tableName', preenchendo os campos informados por 'fields' e
   * que atendam a condi��o estabelecida por 'where'.
   * O m�todo que faz a chamada a prepareInsert � respons�vel pelo fechamento do
   * Statement.
   * @param connection Connection para ser utilizado na opera��o.
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
  public static PreparedStatement prepareUpdate(Connection connection,
                                                String     tableName,
                                                String[]   fields,
                                                String[]   values,
                                                String     where) throws Exception {
    // express�o SQL
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
      // se n�o...ser� um par�metro
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
