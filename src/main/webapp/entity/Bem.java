
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Bem no banco de dados da aplicação.
 */
public class Bem extends Entity {

  /**
   * Nome do campo 'Código Bem' na tabela.
   */
  static public final String FIELD_CODIGO_BEM = "INCODIGOBEM";
  /**
   * Nome do campo 'Código de Barras' na tabela.
   */
  static public final String FIELD_CODIGO_DE_BARRAS = "VACODIGOBARRAS";
  /**
   * Nome do campo 'Tombo' na tabela.
   */
  static public final String FIELD_TOMBO = "VATOMBO";
  /**
   * Nome do campo 'Código Grupo' na tabela.
   */
  static public final String FIELD_CODIGO_GRUPO = "INCODIGOGRUPO";
  /**
   * Nome do campo 'Descrição' na tabela.
   */
  static public final String FIELD_DESCRICAO = "VADESCRICAO";
  /**
   * Nome do campo 'Código Setor' na tabela.
   */
  static public final String FIELD_CODIGO_SETOR = "INCODIGOSETOR";
  /**
   * Nome do campo 'Valor da Compra' na tabela.
   */
  static public final String FIELD_VALOR_DA_COMPRA = "DOVALORCOMPRA";
  /**
   * Nome do campo 'Nota Fiscal' na tabela.
   */
  static public final String FIELD_NOTA_FISCAL = "INNUMERONOTAFISCALCOMPRA";
  /**
   * Nome do campo 'Código Fornecedor' na tabela.
   */
  static public final String FIELD_CODIGO_FORNECEDOR = "INCODIGOFORNECEDOR";
  /**
   * Nome do campo 'Data Inclusão' na tabela.
   */
  static public final String FIELD_DATA_INCLUSAO = "DAINCLUSAO";
  /**
   * Nome do campo 'Proprietário' na tabela.
   */
  static public final String FIELD_PROPRIETARIO = "VAPROPRIETARIO";
  /**
   * Nome do campo 'Particular' na tabela.
   */
  static public final String FIELD_PARTICULAR = "SMPARTICULAR";
  /**
   * Nome do campo 'Última Movimentação' na tabela.
   */
  static public final String FIELD_ULTIMA_MOVIMENTACAO = "DAULTIMAMOVIMENTACAO";
  /**
   * Nome da tabela.
   */
  public static String TABLE_BEM = "BEM";

  /**
   * Construtor padrão.
   */
  public Bem() {
  }

  /**
   * Exclui o(a) Bem informado(a) por 'bemInfo'.
   * Retorna true em caso de sucesso.
   * @param bemInfo BemInfo referente a(o) Bem
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(BemInfo bemInfo) throws Exception {
    // prepara a exclusão
    PreparedStatement deleteStatement = prepareDelete(TABLE_BEM,
                                                      "(" + FIELD_CODIGO_BEM + "=?)"
                                                     );
    try {
      // parâmetros
      deleteStatement.setInt(1, bemInfo.getCodigoBem());
      // executa
      return deleteStatement.execute();
    }
    finally {
      // fecha o Statement
      deleteStatement.close();
    } // try-finally
  }

  /**
   * Retorna um BemInfo contendo os dados existentes no registro atual
   * de 'resultSet'.
   * @param resultSet ResultSet contendo os dados para serem lidos.
   * @return Retorna um BemInfo contendo os dados existentes no registro
   *         atual de 'resultSet'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private BemInfo get(ResultSet resultSet) throws Exception {
    // retorna
    return new BemInfo(
                   resultSet.getInt(FIELD_CODIGO_BEM),
                   resultSet.getString(FIELD_CODIGO_DE_BARRAS).trim(),
                   resultSet.getString(FIELD_TOMBO).trim(),
                   resultSet.getInt(FIELD_CODIGO_GRUPO),
                   resultSet.getString(FIELD_DESCRICAO).trim(),
                   resultSet.getInt(FIELD_CODIGO_SETOR),
                   resultSet.getDouble(FIELD_VALOR_DA_COMPRA),
                   resultSet.getInt(FIELD_NOTA_FISCAL),
                   resultSet.getInt(FIELD_CODIGO_FORNECEDOR),
                   resultSet.getTimestamp(FIELD_DATA_INCLUSAO),
                   resultSet.getString(FIELD_PROPRIETARIO).trim(),
                   resultSet.getInt(FIELD_PARTICULAR),
                   resultSet.getTimestamp(FIELD_ULTIMA_MOVIMENTACAO)
                   );
  }

  /**
   * Retorna um BemInfo[] contendo a lista de Bem retornados(as) pela execução
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que será executado e cujo
   *                          ResultSet será utilizado para pesquisa de registros.
   * @return Retorna um BemInfo[] contendo a lista de Bem retornados(as) pela
   *         execução de 'preparedStatement'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo[] get(PreparedStatement preparedStatement) throws Exception {
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
      BemInfo[] result = new BemInfo[vector.size()];
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
   * de Bem que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Bem
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Bem que atendam a expressão
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
   * de Bem que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Bem
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordenação. Utilize 'DESC' após
   *                       o nome do campo para ordenação descendente.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Bem que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression, String[] orderFields) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    return prepareSelect(TABLE_BEM,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um BemInfo referente a(o) Bem
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param codigoBem Código Bem.
   * @return Retorna um BemInfo referente a(o) Bem
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo getByCodigoBem(
           int codigoBem
         ) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get(
                                         "(" + FIELD_CODIGO_BEM + "=?)"
                                        );
    getStatement.setInt(1, codigoBem);
    BemInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new RecordNotFoundException("Nenhum(a) Bem encontrado(a).");
    else if (result.length > 1)
      throw new ManyRecordsFoundException("Mais de um(a) Bem encontrado(a).");
    else
      return result[0];
  }

  /**
   * Retorna um BemInfo[] contendo a lista de Bem
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param tombo Tombo.
   * @param descricao Descrição.
   * @param valorDaCompra Valor da Compra.
   * @return Retorna um BemInfo[] contendo a lista de Bem
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo[] getByTomboDescricaoValorDaCompra(
           String tombo,
           String descricao,
           double valorDaCompra
         ) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get(
                                         "(" + FIELD_TOMBO + " LIKE ?) AND " +
                                         "(" + FIELD_DESCRICAO + " LIKE ?) AND " +
                                         "(" + FIELD_VALOR_DA_COMPRA + " = ?)"
                                        );
    getStatement.setString(1, tombo + "%");
    getStatement.setString(2, descricao + "%");
    getStatement.setDouble(3, valorDaCompra);
    // retorna
    return get(getStatement);
  }

  /**
   * Insere o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   * sucesso.
   * @param bemInfo BemInfo contendo as informações do(a) Bem que se
   *                    deseja incluir.
   * @return Insere o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(BemInfo bemInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO_BEM,
                       FIELD_CODIGO_DE_BARRAS,
                       FIELD_TOMBO,
                       FIELD_CODIGO_GRUPO,
                       FIELD_DESCRICAO,
                       FIELD_CODIGO_SETOR,
                       FIELD_VALOR_DA_COMPRA,
                       FIELD_NOTA_FISCAL,
                       FIELD_CODIGO_FORNECEDOR,
                       FIELD_DATA_INCLUSAO,
                       FIELD_PROPRIETARIO,
                       FIELD_PARTICULAR,
                       FIELD_ULTIMA_MOVIMENTACAO
                      };
    // prepara inserção
    insertStatement = prepareInsert(TABLE_BEM, fields);
    try {
      // preenche os valores dos campos
      insertStatement.setInt(1, bemInfo.getCodigoBem());
      insertStatement.setString(2, bemInfo.getCodigoDeBarras());
      insertStatement.setString(3, bemInfo.getTombo());
      insertStatement.setInt(4, bemInfo.getCodigoGrupo());
      insertStatement.setString(5, bemInfo.getDescricao());
      insertStatement.setInt(6, bemInfo.getCodigoSetor());
      insertStatement.setDouble(7, bemInfo.getValorDaCompra());
      insertStatement.setInt(8, bemInfo.getNotaFiscal());
      insertStatement.setInt(9, bemInfo.getCodigoFornecedor());
      insertStatement.setTimestamp(10, bemInfo.getDataInclusao());
      insertStatement.setString(11, bemInfo.getProprietario());
      insertStatement.setInt(12, bemInfo.getParticular());
      insertStatement.setTimestamp(13, bemInfo.getUltimaMovimentacao());
      // insere
      return insertStatement.execute();
    }
    finally {
      // fecha o Statement
      insertStatement.close();
    } // try-finally
  }

  /**
   * Atualiza o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   * sucesso.
   * @param bemInfo BemInfo contendo as informações do(a) Bem que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(BemInfo bemInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO_DE_BARRAS,
                       FIELD_TOMBO,
                       FIELD_CODIGO_GRUPO,
                       FIELD_DESCRICAO,
                       FIELD_CODIGO_SETOR,
                       FIELD_VALOR_DA_COMPRA,
                       FIELD_NOTA_FISCAL,
                       FIELD_CODIGO_FORNECEDOR,
                       FIELD_DATA_INCLUSAO,
                       FIELD_PROPRIETARIO,
                       FIELD_PARTICULAR,
                       FIELD_ULTIMA_MOVIMENTACAO
                      };
    // prepara atualização
    updateStatement = prepareUpdate(TABLE_BEM,
                                    fields,
                                    "(" + FIELD_CODIGO_BEM + "=?)"
                                    );
    try {
      // preenche os valores dos campos
      updateStatement.setString(1, bemInfo.getCodigoDeBarras());
      updateStatement.setString(2, bemInfo.getTombo());
      updateStatement.setInt(3, bemInfo.getCodigoGrupo());
      updateStatement.setString(4, bemInfo.getDescricao());
      updateStatement.setInt(5, bemInfo.getCodigoSetor());
      updateStatement.setDouble(6, bemInfo.getValorDaCompra());
      updateStatement.setInt(7, bemInfo.getNotaFiscal());
      updateStatement.setInt(8, bemInfo.getCodigoFornecedor());
      updateStatement.setTimestamp(9, bemInfo.getDataInclusao());
      updateStatement.setString(10, bemInfo.getProprietario());
      updateStatement.setInt(11, bemInfo.getParticular());
      updateStatement.setTimestamp(12, bemInfo.getUltimaMovimentacao());
      // preenche os parâmetros do Where
      updateStatement.setInt(13, bemInfo.getCodigoBem());
        // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

}
