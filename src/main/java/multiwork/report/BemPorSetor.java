package multiwork.report;

import java.sql.*;
import multiwork.report.*;

/**
 * Representa o relatório de Bem por Setor.
 */
public class BemPorSetor extends Report {

  public BemPorSetor() {
  }

  /**
   * Retorna o ResultSet que será usado para gerar o gráfico de Bem por Setor.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que será usado para gerar o gráfico de Estatísticas
   *         por Sssunto.
   */
  public ResultSet getResultSetByQuantidade() throws Exception {
    // prepara a consulta
    PreparedStatement preparedStatement = prepare("SELECT SETOR.VADESCRICAO, " +
                                                  "       COUNT(BEM.INCODIGOSETOR) " +
                                                  "FROM BEM, SETOR " +
                                                  "WHERE (BEM.INCODIGOSETOR = SETOR.INCODIGOSETOR) " +
                                                  "GROUP BY SETOR.VADESCRICAO ");


    // executa
    preparedStatement.execute();
    // retorna
    return preparedStatement.getResultSet();
  }

  /**
   * Retorna o ResultSet que será usado para gerar o gráfico de Bem por Setor.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que será usado para gerar o gráfico de Estatísticas
   *         por Sssunto.
   */
  public ResultSet getResultSetByValor() throws Exception {
    // prepara a consulta
    PreparedStatement preparedStatement = prepare("SELECT SETOR.VADESCRICAO, " +
                                                  "       SUM(BEM.DOVALORCOMPRA) " +
                                                  "FROM BEM, SETOR " +
                                                  "WHERE (BEM.INCODIGOSETOR = SETOR.INCODIGOSETOR) " +
                                                  "GROUP BY SETOR.VADESCRICAO ");
    // executa
    preparedStatement.execute();
    // retorna
    return preparedStatement.getResultSet();
  }

}
