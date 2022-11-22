package multiwork.report;

import java.sql.*;
import multiwork.report.*;

/**
 * Representa o relat�rio de Bens por Grupo de Bem.
 */
public class BemPorGrupoBem extends Report {

  public BemPorGrupoBem() {
  }

  /**
   * Retorna o ResultSet que ser� usado para gerar o gr�fico de Bem por Grupo de Bem.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que ser� usado para gerar o gr�fico de Bem por
   *         Grupo de Bem.
   */
  public ResultSet getResultSetByQuantidade() throws Exception {
    // prepara a consulta
    PreparedStatement preparedStatement = prepare("SELECT GRUPOBEM.VADESCRICAO, " +
                                                  "       COUNT(BEM.INCODIGOGRUPO) " +
                                                  "FROM BEM, GRUPOBEM " +
                                                  "WHERE (BEM.INCODIGOGRUPO = GRUPOBEM.INCODIGOGRUPOBEM) " +
                                                  "GROUP BY GRUPOBEM.VADESCRICAO ");


    // executa
    preparedStatement.execute();
    // retorna
    return preparedStatement.getResultSet();
  }

  /**
   * Retorna o ResultSet que ser� usado para gerar o gr�fico de Bem por Grupo de Bem.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que ser� usado para gerar o gr�fico de Bem por
   *         Grupo de Bem.
   */
  public ResultSet getResultSetByValor() throws Exception {
    // prepara a consulta
    PreparedStatement preparedStatement = prepare("SELECT GRUPOBEM.VADESCRICAO, " +
                                                  "       SUM(BEM.DOVALORCOMPRA) " +
                                                  "FROM BEM, GRUPOBEM " +
                                                  "WHERE (BEM.INCODIGOGRUPO = GRUPOBEM.INCODIGOGRUPOBEM) " +
                                                  "GROUP BY GRUPOBEM.VADESCRICAO ");


    // executa
    preparedStatement.execute();
    // retorna
    return preparedStatement.getResultSet();
  }

}
