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
// * de gráficos tipo Pizza.
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
//   * Retorna o ResultSet contendo os dados que resultarão no gráfico.
//   * @return Retorna o ResultSet contendo os dados que resultarão no gráfico.
//   */
//  public ResultSet getResultSet() {
//    return resultSet;
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
//   * Define o ResultSet contendo os dados que resultarão no gráfico.
//   * @param resultSet ResultSet contendo os dados que resultarão no gráfico.
//   */
//  public void setResultSet(ResultSet resultSet) {
//    this.resultSet = resultSet;
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
