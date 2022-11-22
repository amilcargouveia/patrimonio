
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Grupo Bem no banco de dados da aplica��o.
 */
public class GrupoBem extends Entity {

  /**
   * Nome do campo 'C�digo' na tabela.
   */
  static public final String FIELD_CODIGO = "INCODIGOGRUPOBEM";
  /**
   * Nome do campo 'Descri��o' na tabela.
   */
  static public final String FIELD_DESCRICAO = "VADESCRICAO";
  /**
   * Nome da tabela.
   */
  public static String TABLE_GRUPO_BEM = "GRUPOBEM";

  /**
   * Construtor padr�o.
   */
  public GrupoBem() {
  }

  /**
   * Exclui o(a) Grupo Bem informado(a) por 'grupobemInfo'.
   * Retorna true em caso de sucesso.
   * @param grupobemInfo GrupoBemInfo referente a(o) Grupo Bem
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(GrupoBemInfo grupobemInfo) throws Exception {
    // prepara a exclus�o
    PreparedStatement deleteStatement = prepareDelete(TABLE_GRUPO_BEM,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // inst�ncia de Bem
      Bem bem = new Bem();
      // configurar conex�o
      bem.setConnection(this.getConnection());
      // Verifica a exist�ncia de registros de Bem relacionados com o Grupo
      bem.checkForeignKeyConstraintGrupoBem(grupobemInfo.getCodigo());

      // par�metros
      deleteStatement.setInt(1, grupobemInfo.getCodigo());
      // executa
      return deleteStatement.execute();
    }
    finally {
      // fecha o Statement
      deleteStatement.close();
    } // try-finally
  }

  /**
   * Retorna um GrupoBemInfo contendo os dados existentes no registro atual
   * de 'resultSet'.
   * @param resultSet ResultSet contendo os dados para serem lidos.
   * @return Retorna um GrupoBemInfo contendo os dados existentes no registro
   *         atual de 'resultSet'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  private GrupoBemInfo get(ResultSet resultSet) throws Exception {
    // retorna
    return new GrupoBemInfo(
                   resultSet.getInt(FIELD_CODIGO),
                   resultSet.getString(FIELD_DESCRICAO).trim()
                   );
  }

  /**
   * Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem retornados(as) pela execu��o
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que ser� executado e cujo
   *                          ResultSet ser� utilizado para pesquisa de registros.
   * @return Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem retornados(as) pela
   *         execu��o de 'preparedStatement'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public GrupoBemInfo[] get(PreparedStatement preparedStatement) throws Exception {
    ResultSet resultSet = null;
    try {
      // executa a consulta
      preparedStatement.execute();
      // pega o ResultSet
      resultSet = preparedStatement.getResultSet();
      // loop nos dados
      Vector vector = new Vector();
      while (resultSet.next())
        vector.add(get(resultSet));
      // cria o vetor de retorno
      GrupoBemInfo[] result = new GrupoBemInfo[vector.size()];
      vector.copyInto(result);
      // retorna
      return result;
    }
    finally {
      // fecha o ResultSet e o Statement
      resultSet.getStatement().close();
      resultSet.close();
    } // try-finally
  }

  /**
   * Retorna um PreparedStatement que retornar� um ResultSet contendo os dados
   * de Grupo Bem que atendam a express�o de filtro informada.
   * @param filterExpression Express�o de filtro limitando a lista de Grupo Bem
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornar� um ResultSet contendo
   *         os dados de Grupo Bem que atendam a express�o
             de filtro informada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    String[] orderFields = {FIELD_DESCRICAO};
    // pesquisa no banco
    return get(filterExpression, orderFields);
  }

  /**
   * Retorna um PreparedStatement que retornar� um ResultSet contendo os dados
   * de Grupo Bem que atendam a express�o de filtro informada.
   * @param filterExpression Express�o de filtro limitando a lista de Grupo Bem
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordena��o. Utilize 'DESC' ap�s
   *                       o nome do campo para ordena��o descendente.
   * @return Retorna um PreparedStatement que retornar� um ResultSet contendo
   *         os dados de Grupo Bem que atendam a express�o
             de filtro informada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String   filterExpression,
                               String[] orderFields) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    return prepareSelect(TABLE_GRUPO_BEM,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um GrupoBemInfo referente a(o) Grupo Bem
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param codigo C�digo.
   * @return Retorna um GrupoBemInfo referente a(o) Grupo Bem
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public GrupoBemInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO + "=?)");
    getStatement.setInt(1, codigo);
    GrupoBemInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new RecordNotFoundException("Nenhum(a) Grupo Bem encontrado(a).");
    else if (result.length > 1)
      throw new ManyRecordsFoundException("Mais de um(a) Grupo Bem encontrado(a).");
    else
      return result[0];
  }

  /**
   * Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param descricao Descri��o.
   * @return Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public GrupoBemInfo[] getByDescricao(String descricao) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_DESCRICAO + " LIKE ?)");
    getStatement.setString(1, descricao + "%");
    // retorna
    return get(getStatement);
  }

    /**
     * Gerar o c�digo sequencial do Grupo Bem
     * @return o c�digo sequencial do Grupo Bem
     * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
     *                   dados.
     */
    public int getNextCodigo() throws Exception {
        return super.getNextSequence(TABLE_GRUPO_BEM, FIELD_CODIGO);
    }

  /**
   * Insere o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   * sucesso.
   * @param grupobemInfo GrupoBemInfo contendo as informa��es do(a) Grupo Bem que se
   *                    deseja incluir.
   * @return Insere o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(GrupoBemInfo grupobemInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO,
                       FIELD_DESCRICAO
                      };
    // prepara inser��o
    insertStatement = prepareInsert(TABLE_GRUPO_BEM, fields);
    try {
      // gera o sequencial do c�digo do Grupo Bem
      grupobemInfo.setCodigo(this.getNextCodigo());

      // preenche os valores dos campos
      insertStatement.setInt(1, grupobemInfo.getCodigo());
      insertStatement.setString(2, grupobemInfo.getDescricao());
      // insere
      return insertStatement.execute();
    }
    finally {
      // fecha o Statement
      insertStatement.close();
    } // try-finally
  }

  /**
   * Atualiza o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   * sucesso.
   * @param grupobemInfo GrupoBemInfo contendo as informa��es do(a) Grupo Bem que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(GrupoBemInfo grupobemInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_DESCRICAO
                      };
    // prepara atualiza��o
    updateStatement = prepareUpdate(TABLE_GRUPO_BEM,
                                    fields,
                                    "(" + FIELD_CODIGO + "=?)");
    try {
      // preenche os valores dos campos
      updateStatement.setString(1, grupobemInfo.getDescricao());
      // preenche os par�metros do Where
      updateStatement.setInt(2, grupobemInfo.getCodigo());
        // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

}
