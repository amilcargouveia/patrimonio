package multiwork.report;

import java.sql.*;
import multiwork.report.*;

/**
 * Representa o relatório de Garantia a Vencer.
 */
public class GarantiaVencer extends Report {

  public GarantiaVencer() {
  }

  /**
   * Retorna o ResultSet que será usado para gerar o relatório de Garantia a Vencer.
   * @param dataInicial Data Inicial.
   * @param dataFinal Data Final.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que será usado para gerar o relatório de Garantia a Vencer.
   */
  public ResultSet getSetor(Timestamp dataInicial,
                            Timestamp dataFinal) throws Exception {
    // prepara a consulta
    String sql = "SELECT DISTINCT "
               + "  SETOR.INCODIGOSETOR, "
               + "  SETOR.VADESCRICAO "
               + "FROM BEM, SETOR "
               + "WHERE (BEM.DAGARANTIA >= ?) "
               + "  AND (BEM.DAGARANTIA <= ?) "
               + "  AND (SETOR.INCODIGOSETOR = BEM.INCODIGOSETOR) "
               + "ORDER BY SETOR.VADESCRICAO ";
    PreparedStatement preparedStatement = prepare(sql);
    preparedStatement.setTimestamp(1, dataInicial);
    preparedStatement.setTimestamp(2, dataFinal);
    // retorna
    preparedStatement.execute();
    return preparedStatement.getResultSet();
  }

  /**
   * Retorna o ResultSet que será usado para gerar o relatório de Garantia a Vencer.
   * @param dataInicial Data Inicial.
   * @param dataFinal Data Final.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que será usado para gerar o relatório de Garantia a Vencer.
   */
  public ResultSet getBem(Timestamp dataInicial,
                          Timestamp dataFinal,
                          int       codigoSetor) throws Exception {
    // prepara a consulta
    String sql = "SELECT DISTINCT "
               + "  BEM.INCODIGOBEM, "
               + "  BEM.VACODIGOBARRAS, "
               + "  BEM.VANUMEROSERIE, "
               + "  BEM.VATOMBO, "
               + "  BEM.VADESCRICAO, "
               + "  BEM.DAGARANTIA, "
               + "  BEM.VAPROPRIETARIO "
               + "FROM BEM "
               + "WHERE (BEM.DAGARANTIA >= ?) "
               + "  AND (BEM.DAGARANTIA <= ?) "
               + "  AND (BEM.INCODIGOSETOR = ?) "
               + "ORDER BY BEM.DAGARANTIA, "
               + "         BEM.VADESCRICAO ";
    PreparedStatement preparedStatement = prepare(sql);
    preparedStatement.setTimestamp(1, dataInicial);
    preparedStatement.setTimestamp(2, dataFinal);
    preparedStatement.setInt(3, codigoSetor);
    // retorna
    preparedStatement.execute();
    return preparedStatement.getResultSet();
  }

}
