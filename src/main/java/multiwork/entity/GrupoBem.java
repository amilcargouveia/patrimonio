
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Grupo Bem no banco de dados da aplicação.
 */
public class GrupoBem extends Entity {

  /**
   * Nome do campo 'Código' na tabela.
   */
  static public final String FIELD_CODIGO = "INCODIGOGRUPOBEM";
  /**
   * Nome do campo 'Descrição' na tabela.
   */
  static public final String FIELD_DESCRICAO = "VADESCRICAO";
  /**
   * Nome da tabela.
   */
  public static String TABLE_GRUPO_BEM = "GRUPOBEM";

  /**
   * Construtor padrão.
   */
  public GrupoBem() {
  }

  /**
   * Exclui o(a) Grupo Bem informado(a) por 'grupobemInfo'.
   * Retorna true em caso de sucesso.
   * @param grupobemInfo GrupoBemInfo referente a(o) Grupo Bem
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(GrupoBemInfo grupobemInfo) throws Exception {
    // prepara a exclusão
    PreparedStatement deleteStatement = prepareDelete(TABLE_GRUPO_BEM,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // instância de Bem
      Bem bem = new Bem();
      // configurar conexão
      bem.setConnection(this.getConnection());
      // Verifica a existência de registros de Bem relacionados com o Grupo
      bem.checkForeignKeyConstraintGrupoBem(grupobemInfo.getCodigo());

      // parâmetros
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
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem retornados(as) pela execução
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que será executado e cujo
   *                          ResultSet será utilizado para pesquisa de registros.
   * @return Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem retornados(as) pela
   *         execução de 'preparedStatement'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement que retornará um ResultSet contendo os dados
   * de Grupo Bem que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Grupo Bem
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Grupo Bem que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    String[] orderFields = {FIELD_DESCRICAO};
    // pesquisa no banco
    return get(filterExpression, orderFields);
  }

  /**
   * Retorna um PreparedStatement que retornará um ResultSet contendo os dados
   * de Grupo Bem que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Grupo Bem
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordenação. Utilize 'DESC' após
   *                       o nome do campo para ordenação descendente.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Grupo Bem que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param codigo Código.
   * @return Retorna um GrupoBemInfo referente a(o) Grupo Bem
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param descricao Descrição.
   * @return Retorna um GrupoBemInfo[] contendo a lista de Grupo Bem
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
     * Gerar o código sequencial do Grupo Bem
     * @return o código sequencial do Grupo Bem
     * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
     *                   dados.
     */
    public int getNextCodigo() throws Exception {
        return super.getNextSequence(TABLE_GRUPO_BEM, FIELD_CODIGO);
    }

  /**
   * Insere o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   * sucesso.
   * @param grupobemInfo GrupoBemInfo contendo as informações do(a) Grupo Bem que se
   *                    deseja incluir.
   * @return Insere o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
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
    // prepara inserção
    insertStatement = prepareInsert(TABLE_GRUPO_BEM, fields);
    try {
      // gera o sequencial do código do Grupo Bem
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
   * @param grupobemInfo GrupoBemInfo contendo as informações do(a) Grupo Bem que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Grupo Bem identificado(a) por 'grupobemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(GrupoBemInfo grupobemInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_DESCRICAO
                      };
    // prepara atualização
    updateStatement = prepareUpdate(TABLE_GRUPO_BEM,
                                    fields,
                                    "(" + FIELD_CODIGO + "=?)");
    try {
      // preenche os valores dos campos
      updateStatement.setString(1, grupobemInfo.getDescricao());
      // preenche os parâmetros do Where
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
