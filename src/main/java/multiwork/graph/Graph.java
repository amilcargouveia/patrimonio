//package multiwork.graph;
//
//import java.sql.*;
//import javax.servlet.jsp.*;
//
//import multiwork.util.*;
//
///**
// * Representa a classe base de todas as classes que representam gráficos
// * na aplicação.
// */
//public class Graph {
//
//  private Connection connection = null;
//
//  /**
//   * Construtor padrão.
//   */
//  public Graph() {
//  }
//
//  /**
//   * Executa a expressão 'sql' informada e retorna true em caso de sucesso.
//   * @param sql Expressão SQL para ser executada.
//   * @return Retorna true em caso de sucesso.
//   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
//   *                   dados.
//   */
//  public boolean execute(String sql) throws Exception {
//    return SqlTools.execute(connection, sql);
//  }
//
//  /**
//   * Retorna um ResultSet representando o resultado da execução da expressão
//   * SQL informada. O método que faz a chamada a este deve ser responsável
//   * pelo fechamento do ResultSet e do seu Statement.
//   * @param sql Expressão SQL que se deseja executar.
//   * @return Retorna um ResultSet representando o resultado da execução da
//   *         expressão SQL informada. O método que faz a chamada a executeQuery
//   *         é responsável pelo fechamento do ResultSet e do seu Statement.
//   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
//   * Retorna um PreparedStatement para execução da expressão 'sql' informada.
//   * O método que faz a chamada a prepare é responsável pelo fechamento do
//   * Statement.
//   * @param sql Expressão SQL para ser preparada.
//   * @return Retorna um PreparedStatement para execução da expressão 'sql'
//   *         informada. O método que faz a chamada a prepare é responsável pelo
//   *         fechamento do Statement.
//   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
//   *                   dados.
//   */
//  public PreparedStatement prepare(String sql) throws Exception {
//    return SqlTools.prepare(connection, sql);
//  }
//
//  /**
//   * Retorna um PreparedStatement para uma consulta na tabela
//   * informada por 'tableName', com os campos informados por 'fields', na ordem
//   * informada por 'orderFields' que atendam a condição informada por 'where'.
//   * O método que faz a chamada a select é responsável pelo fechamento do
//   * ResultSet e do seu Statement.
//   * @param tableName Nome da tabela alvo da pesquisa.
//   * @param fields Nomes dos campos que serão retornados.
//   * @param orderFields Nomes dos que se deseja aplicar aos registros retornados.
//   * @param where Condição que limita os registros que serão exibidos.
//   * @return Retorna um PreparedStatement para uma consulta na tabela
//   *         informada por 'tableName', com os campos informados por 'fields', na
//   *         ordem informada por 'orderFields' que atendam a condição informada
//   *         por 'where'. O método que faz a chamada a select é responsável pelo
//   *         fechamento do ResultSet e do seu Statement.
//   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
//   * Publica um BarProducer para a geração de gráficos de barra através
//   * da biblioteca Cewolf.
//   * @param pageContext PageContext referente à página JSP onde o BarProducer
//   *                    será publicado.
//   * @param producerName Nome do BarProducer.
//   * @param resultSet ResultSet contendo os dados que irão gerar o gráfico.
//   * @param seriesNamesFieldIndex Índice do campo que representa os nomes das séries
//   *                              do gráfico.
//   * @param seriesValuesFieldIndex Índice do campo que representa os valores
//   *                               das séries do gráfico.
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
//    // põe na página
//    pageContext.setAttribute(producerName, barProducer);
//  }
//
//  /**
//   * Publica um CategoryProducer para a geração de gráficos de categorias através
//   * da biblioteca Cewolf.
//   * @param pageContext PageContext referente à página JSP onde o BarProducer
//   *                    será publicado.
//   * @param producerName Nome do BarProducer.
//   * @param resultSetList ResultSet[] contendo os ResultSets que contém os dados
//   *                      que irão gerar o gráfico.
//   * @param categoryList String[] contendo os nomes das categorias que farão parte do gráfico.
//   * @param seriesNamesFieldIndex Índice do campo que representa os nomes das séries
//   *                              do gráfico.
//   * @param seriesValuesFieldIndex Índice do campo que representa os valores
//   *                               das séries do gráfico.
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
//    // põe na página
//    pageContext.setAttribute(producerName, categoryProducer);
//  }
//
//  /**
//   * Publica um PieProducer para a geração de um gráfico de pizza através
//   * da biblioteca Cewolf.
//   * @param pageContext PageContext referente à página JSP onde o PieProducer
//   *                    será publicado.
//   * @param producerName Nome do PieProducer.
//   * @param resultSet ResultSet contendo os dados que irão gerar o gráfico.
//   * @param seriesNamesFieldIndex Índice do campo que representa os nomes das séries
//   *                              do gráfico.
//   * @param seriesValuesFieldIndex Índice do campo que representa os valores
//   *                               das séries do gráfico.
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
//    // põe na página
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
