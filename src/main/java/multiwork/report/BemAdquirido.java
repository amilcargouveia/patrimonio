package multiwork.report;

import java.sql.*;
import multiwork.report.*;

/**
 * Representa o relatório de Bens Adquiridos.
 */
public class BemAdquirido extends Report {

  public BemAdquirido() {
  }

  /**
   * Retorna o ResultSet que será usado para gerar o gráfico de Bens adquirido.
   * @param dataInicial Data Inicial.
   * @param dataFinal Data Final.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que será usado para gerar o gráfico de Bens adquiridos.
   */
  public ResultSet getResultSet(Timestamp dataInicial,
                                Timestamp dataFinal) throws Exception {
    // prepara a consulta
    String sql = "SELECT DISTINCT "
               + "  BEM.INCODIGOBEM, "
               + "  BEM.VACODIGOBARRAS, "
               + "  BEM.VANUMEROSERIE, "
               + "  BEM.VATOMBO, "
               + "  BEM.VADESCRICAO, "
               + "  BEM.DAINCLUSAO, "
               + "  BEM.VAPROPRIETARIO "
               + "FROM BEM "
               + "WHERE (BEM.DAINCLUSAO >= ?) "
               + "  AND (BEM.DAINCLUSAO <= ?) "
               + "ORDER BY BEM.DAINCLUSAO, "
               + "         BEM.VADESCRICAO ";
    PreparedStatement preparedStatement = prepare(sql);
    preparedStatement.setTimestamp(1, dataInicial);
    preparedStatement.setTimestamp(2, dataFinal);
    // retorna
    preparedStatement.execute();
    return preparedStatement.getResultSet();
  }

}
