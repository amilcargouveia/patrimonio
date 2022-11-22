package multiwork.report;

import java.sql.*;
import java.util.*;

import multiwork.report.*;

/**
 * Representa o relat�rio de Movimenta��o por Grupo de Bem.
 */
public class MovimentacaoPorGrupoBem extends Report {

  public MovimentacaoPorGrupoBem() {
  }

  /**
   * Retorna o ResultSet que ser� usado para gerar o relat�rio de Movimenta��o por Grupo de Bem.
   * @param month M�s.
   * @param year Ano.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   * @return Retorna o ResultSet que ser� usado para gerar o gr�fico de Movimenta��o por
   *         Grupo de Bem.
   */
  public ResultSet getResultSet(int month, int year) throws Exception {
    // nosso calend�rio
    Calendar calendar = Calendar.getInstance();
    // primeiro dia do m�s
    calendar.set(year, month - 1, 1);
    // data inicial
    Timestamp dataInicial = new Timestamp(calendar.getTime().getTime());
    // �ltima dia do m�s
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    // data final
    Timestamp dataFinal = new Timestamp(calendar.getTime().getTime());
    // nosso SQL
    String sql = "SELECT GRUPOBEM.VADESCRICAO, COUNT(GRUPOBEM.INCODIGOGRUPOBEM) "
               + "FROM MOVIMENTACAO, BEM, GRUPOBEM "
               + "WHERE (MOVIMENTACAO.DADATA >= ?) "
               + "  AND (MOVIMENTACAO.DADATA <= ?) "
               + "  AND (MOVIMENTACAO.INCODIGOBEM = BEM.INCODIGOBEM) "
               + "  AND (BEM.INCODIGOGRUPO = GRUPOBEM.INCODIGOGRUPOBEM) "
               + "GROUP BY GRUPOBEM.VADESCRICAO ";
    // prepara a consulta
    PreparedStatement preparedStatement = prepare(sql);
    // par�metros
    preparedStatement.setTimestamp(1, dataInicial);
    preparedStatement.setTimestamp(2, dataFinal);
    // executa
    preparedStatement.execute();
    // retorna
    return preparedStatement.getResultSet();
  }

}
