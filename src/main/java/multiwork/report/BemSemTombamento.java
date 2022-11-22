package multiwork.report;

import java.sql.*;

/**
 * Representa o relat�rio de Bens sem Tombamento
 */
public class BemSemTombamento extends Report {

  /**
   * Retorna um ResultSet contendo os Setores que cont�m Bens sem Tombamento.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   * @return Retorna um ResultSet contendo os Setores que cont�m Bens sem Tombamento.
   */
  public ResultSet getSetor() throws Exception {
    // prepara a consulta
    String sql = "SELECT DISTINCT BEM.INCODIGOSETOR, "
               + "SETOR.VADESCRICAO "
               + "FROM BEM, SETOR "
               + "WHERE (BEM.VATOMBO = '') "
               + "  AND (BEM.INCODIGOSETOR = SETOR.INCODIGOSETOR) "
               + "ORDER BY SETOR.VADESCRICAO ";
    PreparedStatement preparedStatement = prepare(sql);
    // retorna
    preparedStatement.execute();
    return preparedStatement.getResultSet();
  }

  /**
   * Retorna um ResultSet contendo os Grupos que cont�m Bens sem Tombamento.
   * @param codigoSetor int C�digo do Setor.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   * @return Retorna um ResultSet contendo os Grupos que cont�m Bens sem Tombamento.
   */
  public ResultSet getGrupo(int codigoSetor) throws Exception {
      // prepara a consulta
      String sql = "SELECT DISTINCT "
                 + "  BEM.INCODIGOGRUPO, "
                 + "  GRUPOBEM.VADESCRICAO  "
                 + "FROM BEM, GRUPOBEM "
                 + "WHERE (BEM.VATOMBO = '') "
                 + "  AND (BEM.INCODIGOSETOR = ?) "
                 + "  AND (BEM.INCODIGOGRUPO = GRUPOBEM.INCODIGOGRUPOBEM) "
                 + "ORDER BY GRUPOBEM.VADESCRICAO ";
      PreparedStatement preparedStatement = prepare(sql);
      preparedStatement.setInt(1, codigoSetor);
      // retorna
      preparedStatement.execute();
      return preparedStatement.getResultSet();
    }

    /**
     * Retorna um ResultSet contendo os Bens sem Tombamento.
     * @param codigoSetor int C�digo do Setor.
     * @param codigoGrupoBem int C�digo do Grupo.
     * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
     * @return Retorna um ResultSet contendo os Bens sem Tombamento.
     */
    public ResultSet getBem(int codigoSetor, int codigoGrupoBem) throws Exception {
        // prepara a consulta
        String sql = "SELECT DISTINCT "
                   + "  BEM.INCODIGOBEM, "
                   + "  BEM.VACODIGOBARRAS, "
                   + "  BEM.VADESCRICAO, "
                   + "  BEM.DAINCLUSAO, "
                   + "  BEM.VAPROPRIETARIO "
                   + "FROM BEM "
                   + "WHERE (BEM.VATOMBO = '') "
                   + "  AND (BEM.INCODIGOSETOR = ?) "
                   + "  AND (INCODIGOGRUPO = ?) "
                   + "ORDER BY BEM.VADESCRICAO ";
        PreparedStatement preparedStatement = prepare(sql);
        preparedStatement.setInt(1, codigoSetor);
        preparedStatement.setInt(2, codigoGrupoBem);
        // retorna
        preparedStatement.execute();
        return preparedStatement.getResultSet();
      }

}
