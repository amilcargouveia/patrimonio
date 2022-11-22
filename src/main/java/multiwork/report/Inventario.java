package multiwork.report;

import java.sql.*;

/**
 * Representa o relat�rio de Invent�rio.
 */
public class Inventario extends Report {

  /**
   * Retorna um ResultSet contendo os Grupos que cont�m Bens no Setor informado.
   * @param codigoSetor int C�digo do Setor.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
   * @return Retorna um ResultSet contendo os Grupos que cont�m Bens no Setor informado.
   */
  public ResultSet getGrupo(int codigoSetor) throws Exception {
      // prepara a consulta
      String sql = "SELECT DISTINCT "
                 + "  BEM.INCODIGOGRUPO, "
                 + "  GRUPOBEM.VADESCRICAO  "
                 + "FROM BEM, GRUPOBEM "
                 + "WHERE (BEM.INCODIGOSETOR = ?) "
                 + "  AND (BEM.INCODIGOGRUPO = GRUPOBEM.INCODIGOGRUPOBEM) "
                 + "ORDER BY GRUPOBEM.VADESCRICAO ";
      PreparedStatement preparedStatement = prepare(sql);
      preparedStatement.setInt(1, codigoSetor);
      // retorna
      preparedStatement.execute();
      return preparedStatement.getResultSet();
    }

    /**
     * Retorna um ResultSet contendo os Bens do Setor e Grupo informados.
     * @param codigoSetor int C�digo do Setor.
     * @param codigoGrupoBem int C�digo do Grupo.
     * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de dados.
     * @return Retorna um ResultSet contendo os Bens do Setor e Grupo informados.
     */
    public ResultSet getBem(int codigoSetor, int codigoGrupoBem) throws Exception {
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
                   + "WHERE (BEM.INCODIGOSETOR = ?) "
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
