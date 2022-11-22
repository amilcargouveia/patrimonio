
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Movimentação no banco de dados da aplicação.
 */
public class Movimentacao extends Entity {

  /**
   * Nome do campo 'Código' na tabela.
   */
  static public final String FIELD_CODIGO = "INCODIGOMOVIMENTACAO";
  /**
   * Nome do campo 'Código Bem' na tabela.
   */
  static public final String FIELD_CODIGO_BEM = "INCODIGOBEM";
  /**
   * Nome do campo 'Data' na tabela.
   */
  static public final String FIELD_DATA = "DADATA";
  /**
   * Nome do campo 'Código Setor Origem' na tabela.
   */
  static public final String FIELD_CODIGO_SETOR_ORIGEM = "INCODIGOSETORORIGEM";
  /**
   * Nome do campo 'Código Setor Destino' na tabela.
   */
  static public final String FIELD_CODIGO_SETOR_DESTINO = "INCODIGOSETORDESTINO";
  /**
   * Nome do campo 'Descrição' na tabela.
   */
  static public final String FIELD_DESCRICAO = "VADESCRICAO";
  /**
   * Nome da tabela.
   */
  public static String TABLE_MOVIMENTACAO = "MOVIMENTACAO";

  /**
   * Construtor padrão.
   */
  public Movimentacao() {
  }

  /**
   * Exclui o(a) Movimentação informado(a) por 'movimentacaoInfo'.
   * Retorna true em caso de sucesso.
   * @param movimentacaoInfo MovimentacaoInfo referente a(o) Movimentação
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(MovimentacaoInfo movimentacaoInfo) throws Exception {
    // prepara a exclusão
    PreparedStatement deleteStatement = prepareDelete(TABLE_MOVIMENTACAO,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // parâmetros
      deleteStatement.setInt(1, movimentacaoInfo.getCodigo());
      // executa
      return deleteStatement.execute();
    }
    finally {
      // fecha o Statement
      deleteStatement.close();
    } // try-finally
  }

  /**
   * Retorna um MovimentacaoInfo contendo os dados existentes no registro atual
   * de 'resultSet'.
   * @param resultSet ResultSet contendo os dados para serem lidos.
   * @return Retorna um MovimentacaoInfo contendo os dados existentes no registro
   *         atual de 'resultSet'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private MovimentacaoInfo get(ResultSet resultSet) throws Exception {
    // retorna
    return new MovimentacaoInfo(
                   resultSet.getInt(FIELD_CODIGO),
                   resultSet.getInt(FIELD_CODIGO_BEM),
                   resultSet.getTimestamp(FIELD_DATA),
                   resultSet.getInt(FIELD_CODIGO_SETOR_ORIGEM),
                   resultSet.getInt(FIELD_CODIGO_SETOR_DESTINO),
                   resultSet.getString(FIELD_DESCRICAO).trim()
                   );
  }

  /**
   * Retorna um MovimentacaoInfo[] contendo a lista de Movimentação retornados(as) pela execução
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que será executado e cujo
   *                          ResultSet será utilizado para pesquisa de registros.
   * @return Retorna um MovimentacaoInfo[] contendo a lista de Movimentação retornados(as) pela
   *         execução de 'preparedStatement'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public MovimentacaoInfo[] get(PreparedStatement preparedStatement) throws Exception {
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
      MovimentacaoInfo[] result = new MovimentacaoInfo[vector.size()];
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
   * de Movimentação que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Movimentação
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Movimentação que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    // pesquisa no banco
    return get(filterExpression, new String[0]);
  }

  /**
   * Retorna um PreparedStatement que retornará um ResultSet contendo os dados
   * de Movimentação que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Movimentação
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordenação. Utilize 'DESC' após
   *                       o nome do campo para ordenação descendente.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Movimentação que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String   filterExpression,
                               String[] orderFields) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    return prepareSelect(TABLE_MOVIMENTACAO,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um MovimentacaoInfo referente a(o) Movimentação
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param codigo Código.
   * @return Retorna um MovimentacaoInfo referente a(o) Movimentação
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public MovimentacaoInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO + "=?)");
    getStatement.setInt(1, codigo);
    MovimentacaoInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new RecordNotFoundException("Nenhum(a) Movimentação encontrado(a).");
    else if (result.length > 1)
      throw new ManyRecordsFoundException("Mais de um(a) Movimentação encontrado(a).");
    else
      return result[0];
  }

  /**
   * Retorna um MovimentacaoInfo[] contendo a lista de Movimentação
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param codigoBem Código Bem.
   * @return Retorna um MovimentacaoInfo[] contendo a lista de Movimentação
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public MovimentacaoInfo[] getByCodigoBem(int codigoBem) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO_BEM + " = ?)");
    getStatement.setInt(1, codigoBem);
    // retorna
    return get(getStatement);
  }

  /**
   * Insere o(a) Movimentação identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   * sucesso.
   * @param movimentacaoInfo MovimentacaoInfo contendo as informações do(a) Movimentação que se
   *                    deseja incluir.
   * @return Insere o(a) Movimentação identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(MovimentacaoInfo movimentacaoInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO,
                       FIELD_CODIGO_BEM,
                       FIELD_DATA,
                       FIELD_CODIGO_SETOR_ORIGEM,
                       FIELD_CODIGO_SETOR_DESTINO,
                       FIELD_DESCRICAO
                      };
    // prepara inserção
    insertStatement = prepareInsert(TABLE_MOVIMENTACAO, fields);
    try {
      // gera o sequencial do código
      movimentacaoInfo.setCodigo(this.getNextCodigo());

      // preenche os valores dos campos
      insertStatement.setInt(1, movimentacaoInfo.getCodigo());
      insertStatement.setInt(2, movimentacaoInfo.getCodigoBem());
      insertStatement.setTimestamp(3, movimentacaoInfo.getData());
      insertStatement.setInt(4, movimentacaoInfo.getCodigoSetorOrigem());
      insertStatement.setInt(5, movimentacaoInfo.getCodigoSetorDestino());
      insertStatement.setString(6, movimentacaoInfo.getDescricao());
      // insere
      return insertStatement.execute();
    }
    finally {
      // fecha o Statement
      insertStatement.close();
    } // try-finally
  }

  /**
   * Atualiza o(a) Movimentação identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   * sucesso.
   * @param movimentacaoInfo MovimentacaoInfo contendo as informações do(a) Movimentação que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Movimentação identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(MovimentacaoInfo movimentacaoInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO_BEM,
                       FIELD_DATA,
                       FIELD_CODIGO_SETOR_ORIGEM,
                       FIELD_CODIGO_SETOR_DESTINO,
                       FIELD_DESCRICAO
                      };
    // prepara atualização
    updateStatement = prepareUpdate(TABLE_MOVIMENTACAO,
                                    fields,
                                    "(" + FIELD_CODIGO + "=?)");
    try {
      // preenche os valores dos campos
      updateStatement.setInt(1, movimentacaoInfo.getCodigoBem());
      updateStatement.setTimestamp(2, movimentacaoInfo.getData());
      updateStatement.setInt(3, movimentacaoInfo.getCodigoSetorOrigem());
      updateStatement.setInt(4, movimentacaoInfo.getCodigoSetorDestino());
      updateStatement.setString(5, movimentacaoInfo.getDescricao());
      // preenche os parâmetros do Where
      updateStatement.setInt(6, movimentacaoInfo.getCodigo());
        // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

  /**
   * Gerar o código sequencial da movimentação
   * @return o código sequencial da movimentação
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public int getNextCodigo() throws Exception {
      return super.getNextSequence(TABLE_MOVIMENTACAO, FIELD_CODIGO);
  }

}
