//package multiwork.graph;
//
//import java.io.*;
//import java.util.*;
//import java.sql.*;
//
//import org.jfree.data.*;
//import de.laures.cewolf.*;
//
///**
// * Implementa a Interface DatasetProducer da biblioteca Cewolf para a criação
// * de gráficos agrupados por categoria.
// */
//public class CategoryProducer implements DatasetProducer, Serializable {
//
//  private String[]    categoryList           = null;
//  private ResultSet[] resultSetList          = null;
//  private int         seriesNamesFieldIndex  = 0;
//  private int         seriesValuesFieldIndex = 0;
//
//  public String getProducerId() {
//    return "Multi Work Bar Producer";
//  }
//
//  /**
//   * Retorna o String[] contendo os nomes das categorias que farão parte do gráfico.
//   * @return Retorna o String[] contendo os nomes das categorias que farão parte do gráfico.
//   */
//  public String[] getCategoryList() {
//    return categoryList;
//  }
//
//  /**
//   * Retorna o ResultSet[] contendo a lista dos ResultSets que contém os dados
//   * que resultarão no gráfico.
//   * @return Retorna o ResultSet[] contendo a lista dos ResultSets que contém os dados
//   * que resultarão no gráfico.
//   */
//  public ResultSet[] getResultSetList() {
//    return resultSetList;
//  }
//
//  /**
//   * Retorna o índice do campo que representa os nomes das séries do gráfico.
//   * @return Retorna o índice do campo que representa os nomes das séries do gráfico.
//   */
//  public int getSeriesNamesFieldIndex() {
//    return seriesNamesFieldIndex;
//  }
//
//  /**
//   * Retorna o índice do campo que representa os valores das séries do gráfico.
//   * @return Retorna o índice do campo que representa os valores das séries do gráfico.
//   */
//  public int getSeriesValuesFieldIndex() {
//    return seriesValuesFieldIndex;
//  }
//
//  public boolean hasExpired(Map params, java.util.Date since) {
//    return (System.currentTimeMillis() - since.getTime()) > 5000;
//  }
//
//  public Object produceDataset(Map params) throws DatasetProduceException {
//    try {
//      // nosso DataSet producer
//      DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//      // loop nos ResultSets
//      for (int i=0; i<resultSetList.length; i++) {
//        // ResultSet da vez
//        ResultSet resultSet = resultSetList[i];
//        // loop nos dados
//        while (resultSet.next()) {
//          dataset.addValue(resultSet.getDouble(seriesValuesFieldIndex),
//                           resultSet.getString(seriesNamesFieldIndex),
//                           categoryList[i]);
//        } // while
//      } // for
//      // retorna
//      return dataset;
//    }
//    catch (Exception e) {
//      throw new RuntimeException(e);
//    } // try-finnaly
//  }
//
//  /**
//   * Define o String[] contendo os nomes das categorias que farão parte do gráfico.
//   * @param categoryList String[] contendo os nomes das categorias que farão parte do gráfico.
//   */
//  public void setCategoryList(String[] categoryList) {
//    this.categoryList = categoryList;
//  }
//
//  /**
//   * Define o ResultSet[] contendo os ResultSets que contém os dados que resultarão
//   * no gráfico.
//   * @param resultSetList ResultSet[] contendo os ResultSets que contém os dados que
//   *                      resultarão no gráfico.
//   */
//  public void setResultSetList(ResultSet[] resultSetList) {
//    this.resultSetList = resultSetList;
//  }
//
//  /**
//   * Define o índice do campo que representa os nomes das séries do gráfico.
//   * @param seriesNamesFieldIndex Índice do campo que representa os nomes das
//   *        séries do gráfico.
//   */
//  public void setSeriesNamesFieldIndex(int seriesNamesFieldIndex) {
//    this.seriesNamesFieldIndex = seriesNamesFieldIndex;
//  }
//
//  /**
//   * Define o índice do campo que representa os valores das séries do gráfico.
//   * @param seriesValuesFieldIndex Índice do campo que representa os valores
//   *        das séries do gráfico.
//   */
//  public void setSeriesValuesFieldIndex(int seriesValuesFieldIndex) {
//    this.seriesValuesFieldIndex = seriesValuesFieldIndex;
//  }
//
//}
