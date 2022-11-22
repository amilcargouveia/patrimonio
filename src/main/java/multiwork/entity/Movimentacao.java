
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Movimenta��o no banco de dados da aplica��o.
 */
public class Movimentacao extends Entity {

  /**
   * Nome do campo 'C�digo' na tabela.
   */
  static public final String FIELD_CODIGO = "INCODIGOMOVIMENTACAO";
  /**
   * Nome do campo 'C�digo Bem' na tabela.
   */
  static public final String FIELD_CODIGO_BEM = "INCODIGOBEM";
  /**
   * Nome do campo 'Data' na tabela.
   */
  static public final String FIELD_DATA = "DADATA";
  /**
   * Nome do campo 'C�digo Setor Origem' na tabela.
   */
  static public final String FIELD_CODIGO_SETOR_ORIGEM = "INCODIGOSETORORIGEM";
  /**
   * Nome do campo 'C�digo Setor Destino' na tabela.
   */
  static public final String FIELD_CODIGO_SETOR_DESTINO = "INCODIGOSETORDESTINO";
  /**
   * Nome do campo 'Descri��o' na tabela.
   */
  static public final String FIELD_DESCRICAO = "VADESCRICAO";
  /**
   * Nome da tabela.
   */
  public static String TABLE_MOVIMENTACAO = "MOVIMENTACAO";

  /**
   * Construtor padr�o.
   */
  public Movimentacao() {
  }

  /**
   * Exclui o(a) Movimenta��o informado(a) por 'movimentacaoInfo'.
   * Retorna true em caso de sucesso.
   * @param movimentacaoInfo MovimentacaoInfo referente a(o) Movimenta��o
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(MovimentacaoInfo movimentacaoInfo) throws Exception {
    // prepara a exclus�o
    PreparedStatement deleteStatement = prepareDelete(TABLE_MOVIMENTACAO,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // par�metros
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
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um MovimentacaoInfo[] contendo a lista de Movimenta��o retornados(as) pela execu��o
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que ser� executado e cujo
   *                          ResultSet ser� utilizado para pesquisa de registros.
   * @return Retorna um MovimentacaoInfo[] contendo a lista de Movimenta��o retornados(as) pela
   *         execu��o de 'preparedStatement'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um PreparedStatement que retornar� um ResultSet contendo os dados
   * de Movimenta��o que atendam a express�o de filtro informada.
   * @param filterExpression Express�o de filtro limitando a lista de Movimenta��o
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornar� um ResultSet contendo
   *         os dados de Movimenta��o que atendam a express�o
             de filtro informada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    // pesquisa no banco
    return get(filterExpression, new String[0]);
  }

  /**
   * Retorna um PreparedStatement que retornar� um ResultSet contendo os dados
   * de Movimenta��o que atendam a express�o de filtro informada.
   * @param filterExpression Express�o de filtro limitando a lista de Movimenta��o
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordena��o. Utilize 'DESC' ap�s
   *                       o nome do campo para ordena��o descendente.
   * @return Retorna um PreparedStatement que retornar� um ResultSet contendo
   *         os dados de Movimenta��o que atendam a express�o
             de filtro informada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Retorna um MovimentacaoInfo referente a(o) Movimenta��o
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param codigo C�digo.
   * @return Retorna um MovimentacaoInfo referente a(o) Movimenta��o
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public MovimentacaoInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO + "=?)");
    getStatement.setInt(1, codigo);
    MovimentacaoInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new RecordNotFoundException("Nenhum(a) Movimenta��o encontrado(a).");
    else if (result.length > 1)
      throw new ManyRecordsFoundException("Mais de um(a) Movimenta��o encontrado(a).");
    else
      return result[0];
  }

  /**
   * Retorna um MovimentacaoInfo[] contendo a lista de Movimenta��o
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @param codigoBem C�digo Bem.
   * @return Retorna um MovimentacaoInfo[] contendo a lista de Movimenta��o
   * indicados(as) pelos par�metros que representam sua chave secund�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
   * Insere o(a) Movimenta��o identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   * sucesso.
   * @param movimentacaoInfo MovimentacaoInfo contendo as informa��es do(a) Movimenta��o que se
   *                    deseja incluir.
   * @return Insere o(a) Movimenta��o identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
    // prepara inser��o
    insertStatement = prepareInsert(TABLE_MOVIMENTACAO, fields);
    try {
      // gera o sequencial do c�digo
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
   * Atualiza o(a) Movimenta��o identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   * sucesso.
   * @param movimentacaoInfo MovimentacaoInfo contendo as informa��es do(a) Movimenta��o que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Movimenta��o identificado(a) por 'movimentacaoInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
    // prepara atualiza��o
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
      // preenche os par�metros do Where
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
   * Gerar o c�digo sequencial da movimenta��o
   * @return o c�digo sequencial da movimenta��o
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public int getNextCodigo() throws Exception {
      return super.getNextSequence(TABLE_MOVIMENTACAO, FIELD_CODIGO);
  }

}
