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
// * de gr�ficos tipo Pizza.
// */
//public class PieProducer implements DatasetProducer, Serializable {
//
//  private ResultSet resultSet              = null;
//  private int       seriesNamesFieldIndex  = 0;
//  private int       seriesValuesFieldIndex = 0;
//
//  public String getProducerId() {
//    return "Multi Work Pie Producer";
//  }
//
//  /**
//   * Retorna o ResultSet contendo os dados que resultar�o no gr�fico.
//   * @return Retorna o ResultSet contendo os dados que resultar�o no gr�fico.
//   */
//  public ResultSet getResultSet() {
//    return resultSet;
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
//      DefaultPieDataset pieDataset = new DefaultPieDataset();
//      // loop nos dados
//      while (resultSet.next()) {
//        pieDataset.setValue(resultSet.getString(seriesNamesFieldIndex),
//                            resultSet.getDouble(seriesValuesFieldIndex));
//      } // while
//      return pieDataset;
//    }
//    catch (Exception e) {
//      throw new RuntimeException(e);
//    } // try-finnaly
//  }
//
//  /**
//   * Define o ResultSet contendo os dados que resultar�o no gr�fico.
//   * @param resultSet ResultSet contendo os dados que resultar�o no gr�fico.
//   */
//  public void setResultSet(ResultSet resultSet) {
//    this.resultSet = resultSet;
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
