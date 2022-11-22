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
// * Implementa a Interface DatasetProducer da biblioteca Cewolf para a cria��o
// * de gr�ficos agrupados por categoria.
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
//   * Retorna o String[] contendo os nomes das categorias que far�o parte do gr�fico.
//   * @return Retorna o String[] contendo os nomes das categorias que far�o parte do gr�fico.
//   */
//  public String[] getCategoryList() {
//    return categoryList;
//  }
//
//  /**
//   * Retorna o ResultSet[] contendo a lista dos ResultSets que cont�m os dados
//   * que resultar�o no gr�fico.
//   * @return Retorna o ResultSet[] contendo a lista dos ResultSets que cont�m os dados
//   * que resultar�o no gr�fico.
//   */
//  public ResultSet[] getResultSetList() {
//    return resultSetList;
//  }
//
//  /**
//   * Retorna o �ndice do campo que representa os nomes das s�ries do gr�fico.
//   * @return Retorna o �ndice do campo que representa os nomes das s�ries do gr�fico.
//   */
//  public int getSeriesNamesFieldIndex() {
//    return seriesNamesFieldIndex;
//  }
//
//  /**
//   * Retorna o �ndice do campo que representa os valores das s�ries do gr�fico.
//   * @return Retorna o �ndice do campo que representa os valores das s�ries do gr�fico.
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
//   * Define o String[] contendo os nomes das categorias que far�o parte do gr�fico.
//   * @param categoryList String[] contendo os nomes das categorias que far�o parte do gr�fico.
//   */
//  public void setCategoryList(String[] categoryList) {
//    this.categoryList = categoryList;
//  }
//
//  /**
//   * Define o ResultSet[] contendo os ResultSets que cont�m os dados que resultar�o
//   * no gr�fico.
//   * @param resultSetList ResultSet[] contendo os ResultSets que cont�m os dados que
//   *                      resultar�o no gr�fico.
//   */
//  public void setResultSetList(ResultSet[] resultSetList) {
//    this.resultSetList = resultSetList;
//  }
//
//  /**
//   * Define o �ndice do campo que representa os nomes das s�ries do gr�fico.
//   * @param seriesNamesFieldIndex �ndice do campo que representa os nomes das
//   *        s�ries do gr�fico.
//   */
//  public void setSeriesNamesFieldIndex(int seriesNamesFieldIndex) {
//    this.seriesNamesFieldIndex = seriesNamesFieldIndex;
//  }
//
//  /**
//   * Define o �ndice do campo que representa os valores das s�ries do gr�fico.
//   * @param seriesValuesFieldIndex �ndice do campo que representa os valores
//   *        das s�ries do gr�fico.
//   */
//  public void setSeriesValuesFieldIndex(int seriesValuesFieldIndex) {
//    this.seriesValuesFieldIndex = seriesValuesFieldIndex;
//  }
//
//}
