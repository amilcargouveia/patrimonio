//package multiwork.graph;
//
//import java.sql.*;
//import javax.servlet.jsp.*;
//
//import multiwork.util.*;
//
///**
// * Representa a classe base de todas as classes que representam gr�ficos
// * na aplica��o.
// */
//public class Graph {
//
//  private Connection connection = null;
//
//  /**
//   * Construtor padr�o.
//   */
//  public Graph() {
//  }
//
//  /**
//   * Executa a express�o 'sql' informada e retorna true em caso de sucesso.
//   * @param sql Express�o SQL para ser executada.
//   * @return Retorna true em caso de sucesso.
//   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
//   *                   dados.
//   */
//  public boolean execute(String sql) throws Exception {
//    return SqlTools.execute(connection, sql);
//  }
//
//  /**
//   * Retorna um ResultSet representando o resultado da execu��o da express�o
//   * SQL informada. O m�todo que faz a chamada a este deve ser respons�vel
//   * pelo fechamento do ResultSet e do seu Statement.
//   * @param sql Express�o SQL que se deseja executar.
//   * @return Retorna um ResultSet representando o resultado da execu��o da
//   *         express�o SQL informada. O m�todo que faz a chamada a executeQuery
//   *         � respons�vel pelo fechamento do ResultSet e do seu Statement.
//   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
//   *                   dados.
//   */
//  public ResultSet executeQuery(String sql) throws Exception {
//    return SqlTools.executeQuery(connection, sql);
//  }
//
//  /**
//   * Retorna o Connection utilizado para acesso ao banco de dados.
//   * @return Retorna o Connection utilizado para acesso ao banco de dados.
//   */
//  public Connection getConnection() {
//    return connection;
//  }
//
//  /**
//   * Retorna um PreparedStatement para execu��o da express�o 'sql' informada.
//   * O m�todo que faz a chamada a prepare � respons�vel pelo fechamento do
//   * Statement.
//   * @param sql Express�o SQL para ser preparada.
//   * @return Retorna um PreparedStatement para execu��o da express�o 'sql'
//   *         informada. O m�todo que faz a chamada a prepare � respons�vel pelo
//   *         fechamento do Statement.
//   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
//   *                   dados.
//   */
//  public PreparedStatement prepare(String sql) throws Exception {
//    return SqlTools.prepare(connection, sql);
//  }
//
//  /**
//   * Retorna um PreparedStatement para uma consulta na tabela
//   * informada por 'tableName', com os campos informados por 'fields', na ordem
//   * informada por 'orderFields' que atendam a condi��o informada por 'where'.
//   * O m�todo que faz a chamada a select � respons�vel pelo fechamento do
//   * ResultSet e do seu Statement.
//   * @param tableName Nome da tabela alvo da pesquisa.
//   * @param fields Nomes dos campos que ser�o retornados.
//   * @param orderFields Nomes dos que se deseja aplicar aos registros retornados.
//   * @param where Condi��o que limita os registros que ser�o exibidos.
//   * @return Retorna um PreparedStatement para uma consulta na tabela
//   *         informada por 'tableName', com os campos informados por 'fields', na
//   *         ordem informada por 'orderFields' que atendam a condi��o informada
//   *         por 'where'. O m�todo que faz a chamada a select � respons�vel pelo
//   *         fechamento do ResultSet e do seu Statement.
//   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
//   *                   dados.
//   */
//  public PreparedStatement prepareSelect(String   tableName,
//                                         String[] fields,
//                                         String[] orderFields,
//                                         String   where) throws Exception {
//    return SqlTools.prepareSelect(connection,
//                                  tableName,
//                                  fields,
//                                  orderFields,
//                                  where);
//  }
//
//  /**
//   * Publica um BarProducer para a gera��o de gr�ficos de barra atrav�s
//   * da biblioteca Cewolf.
//   * @param pageContext PageContext referente � p�gina JSP onde o BarProducer
//   *                    ser� publicado.
//   * @param producerName Nome do BarProducer.
//   * @param resultSet ResultSet contendo os dados que ir�o gerar o gr�fico.
//   * @param seriesNamesFieldIndex �ndice do campo que representa os nomes das s�ries
//   *                              do gr�fico.
//   * @param seriesValuesFieldIndex �ndice do campo que representa os valores
//   *                               das s�ries do gr�fico.
//   */
//  public void publishBarProducer(PageContext pageContext,
//                                 String      producerName,
//                                 ResultSet   resultSet,
//                                 int         seriesNamesFieldIndex,
//                                 int         seriesValuesFieldIndex) {
//    // cria o Producer
//    BarProducer barProducer = new BarProducer();
//    // configura-o
//    barProducer.setResultSet(resultSet);
//    barProducer.setSeriesNamesFieldIndex(seriesNamesFieldIndex);
//    barProducer.setSeriesValuesFieldIndex(seriesValuesFieldIndex);
//    // p�e na p�gina
//    pageContext.setAttribute(producerName, barProducer);
//  }
//
//  /**
//   * Publica um CategoryProducer para a gera��o de gr�ficos de categorias atrav�s
//   * da biblioteca Cewolf.
//   * @param pageContext PageContext referente � p�gina JSP onde o BarProducer
//   *                    ser� publicado.
//   * @param producerName Nome do BarProducer.
//   * @param resultSetList ResultSet[] contendo os ResultSets que cont�m os dados
//   *                      que ir�o gerar o gr�fico.
//   * @param categoryList String[] contendo os nomes das categorias que far�o parte do gr�fico.
//   * @param seriesNamesFieldIndex �ndice do campo que representa os nomes das s�ries
//   *                              do gr�fico.
//   * @param seriesValuesFieldIndex �ndice do campo que representa os valores
//   *                               das s�ries do gr�fico.
//   */
//  public void publishCategoryProducer(PageContext pageContext,
//                                      String      producerName,
//                                      ResultSet[] resultSetList,
//                                      String[]    categoryList,
//                                      int         seriesNamesFieldIndex,
//                                      int         seriesValuesFieldIndex) {
//    // cria o Producer
//    CategoryProducer categoryProducer = new CategoryProducer();
//    // configura-o
//    categoryProducer.setCategoryList(categoryList);
//    categoryProducer.setResultSetList(resultSetList);
//    categoryProducer.setSeriesNamesFieldIndex(seriesNamesFieldIndex);
//    categoryProducer.setSeriesValuesFieldIndex(seriesValuesFieldIndex);
//    // p�e na p�gina
//    pageContext.setAttribute(producerName, categoryProducer);
//  }
//
//  /**
//   * Publica um PieProducer para a gera��o de um gr�fico de pizza atrav�s
//   * da biblioteca Cewolf.
//   * @param pageContext PageContext referente � p�gina JSP onde o PieProducer
//   *                    ser� publicado.
//   * @param producerName Nome do PieProducer.
//   * @param resultSet ResultSet contendo os dados que ir�o gerar o gr�fico.
//   * @param seriesNamesFieldIndex �ndice do campo que representa os nomes das s�ries
//   *                              do gr�fico.
//   * @param seriesValuesFieldIndex �ndice do campo que representa os valores
//   *                               das s�ries do gr�fico.
//   */
//  public void publishPieProducer(PageContext pageContext,
//                                 String      producerName,
//                                 ResultSet   resultSet,
//                                 int         seriesNamesFieldIndex,
//                                 int         seriesValuesFieldIndex) {
//    // cria o Producer
//    PieProducer pieProducer = new PieProducer();
//    // cponfigura-o
//    pieProducer.setResultSet(resultSet);
//    pieProducer.setSeriesNamesFieldIndex(seriesNamesFieldIndex);
//    pieProducer.setSeriesValuesFieldIndex(seriesValuesFieldIndex);
//    // p�e na p�gina
//    pageContext.setAttribute(producerName, pieProducer);
//  }
//
//  /**
//   * Define o Connection utilizado para acesso ao banco de dados.
//   * @param connection Connection utilizado para acesso ao banco de dados.
//   */
//  public void setConnection(Connection connection) {
//    this.connection = connection;
//  }
//
//}
