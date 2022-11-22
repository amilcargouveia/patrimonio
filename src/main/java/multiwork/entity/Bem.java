
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Bem no banco de dados da aplica��o.
 */
public class Bem extends Entity {

  static public final int PARTICULAR_NAO = 0;
  static public final int PARTICULAR_SIM = 1;

  /**
   * Nome do campo 'C�digo' na tabela.
   */
  static public final String FIELD_CODIGO = "INCODIGOBEM";
  /**
   * Nome do campo 'C�digo de Barras' na tabela.
   */
  static public final String FIELD_CODIGO_DE_BARRAS = "VACODIGOBARRAS";
  /**
   * Nome do campo 'Tombo' na tabela.
   */
  static public final String FIELD_TOMBO = "VATOMBO";
  /**
   * Nome do campo 'N�mero S�rie' na tabela.
   */
  static public final String FIELD_NUMERO_SERIE = "VANUMEROSERIE";
  /**
   * Nome do campo 'C�digo Grupo' na tabela.
   */
  static public final String FIELD_CODIGO_GRUPO = "INCODIGOGRUPO";
  /**
   * Nome do campo 'Descri��o' na tabela.
   */
  static public final String FIELD_DESCRICAO = "VADESCRICAO";
  /**
   * Nome do campo 'C�digo Setor' na tabela.
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
   * Nome do campo 'C�digo Fornecedor' na tabela.
   */
  static public final String FIELD_CODIGO_FORNECEDOR = "INCODIGOFORNECEDOR";
  /**
   * Nome do campo 'Data Inclus�o' na tabela.
   */
  static public final String FIELD_DATA_INCLUSAO = "DAINCLUSAO";
  /**
   * Nome do campo 'Data Garantia' na tabela.
   */
  static public final String FIELD_DATA_GARANTIA = "DAGARANTIA";
  /**
   * Nome do campo 'Propriet�rio' na tabela.
   */
  static public final String FIELD_PROPRIETARIO = "VAPROPRIETARIO";
  /**
   * Nome do campo 'Particular' na tabela.
   */
  static public final String FIELD_PARTICULAR = "SMPARTICULAR";
  /**
   * Nome do campo '�ltima Movimenta��o' na tabela.
   */
  static public final String FIELD_ULTIMA_MOVIMENTACAO = "DAULTIMAMOVIMENTACAO";
  /**
   * Nome da tabela.
   */
  public static String TABLE_BEM = "BEM";

  /**
   * Construtor padr�o.
   */
  public Bem() {
  }

  /**
   * Verifica a exist�ncia de registros de Bem relacionados com o Fornecedor
   * indicado.
   * @param codigoFornecedor int C�digo Fornecedor.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados ou de haver registros relacionados.
   */
  public void checkForeignKeyConstraintFornecedor(int codigoFornecedor) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get(FIELD_CODIGO_FORNECEDOR + "=?");
    getStatement.setInt(1, codigoFornecedor);
    // executa
    getStatement.execute();
    // se temos registros relacionados...exce��o
    if (getStatement.getResultSet().next())
      throw new ForeignKeyConstraintException("Existem Bens relacionados a este Fornecedor.");
  }

  /**
   * Verifica a exist�ncia de registros de Bem relacionados com o Grupo indicado.
   * @param codigoGrupoBem int C�digo Grupo.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados ou de haver registros relacionados.
   */
  public void checkForeignKeyConstraintGrupoBem(int codigoGrupoBem) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get(FIELD_CODIGO_GRUPO + "=?");
    getStatement.setInt(1, codigoGrupoBem);
    // executa
    getStatement.execute();
    // se temos registros relacionados...exce��o
    if (getStatement.getResultSet().next())
      throw new ForeignKeyConstraintException("Existem Bens relacionados a este Grupo.");
  }

  /**
   * Verifica a exist�ncia de registros de Bem relacionados com o Setor indicado.
   * @param codigoSetor int C�digo Setor.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados ou de haver registros relacionados.
   */
  public void checkForeignKeyConstraintSetor(int codigoSetor) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get(FIELD_CODIGO_SETOR + "=?");
    getStatement.setInt(1, codigoSetor);
    // executa
    getStatement.execute();
    // se temos registros relacionados...exce��o
    if (getStatement.getResultSet().next())
      throw new ForeignKeyConstraintException("Existem Bens relacionados a este Setor.");
  }

  /**
   * Exclui o(a) Bem informado(a) por 'bemInfo'.
   * Retorna true em caso de sucesso.
   * @param bemInfo BemInfo referente a(o) Bem
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(BemInfo bemInfo) throws Exception {
    // prepara a exclus�o
    PreparedStatement deleteStatement = prepareDelete(TABLE_BEM,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // par�metros
      deleteStatement.setInt(1, bemInfo.getCodigo());
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
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  private BemInfo get(ResultSet resultSet) throws Exception {
    // retorna
    return new BemInfo(
                   resultSet.getInt(FIELD_CODIGO),
                   resultSet.getString(FIELD_CODIGO_DE_BARRAS).trim(),
                   resultSet.getString(FIELD_TOMBO).trim(),
                   resultSet.getString(FIELD_NUMERO_SERIE).trim(),
                   resultSet.getInt(FIELD_CODIGO_GRUPO),
                   resultSet.getString(FIELD_DESCRICAO).trim(),
                   resultSet.getInt(FIELD_CODIGO_SETOR),
                   resultSet.getDouble(FIELD_VALOR_DA_COMPRA),
                   resultSet.getInt(FIELD_NOTA_FISCAL),
                   resultSet.getInt(FIELD_CODIGO_FORNECEDOR),
                   resultSet.getTimestamp(FIELD_DATA_INCLUSAO),
                   resultSet.getTimestamp(FIELD_DATA_GARANTIA),
                   resultSet.getString(FIELD_PROPRIETARIO).trim(),
                   resultSet.getInt(FIELD_PARTICULAR),
                   resultSet.getTimestamp(FIELD_ULTIMA_MOVIMENTACAO)
                   );
  }

  /**
   * Retorna um BemInfo[] contendo a lista de Bem retornados(as) pela execu��o
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que ser� executado e cujo
   *                          ResultSet ser� utilizado para pesquisa de registros.
   * @return Retorna um BemInfo[] contendo a lista de Bem retornados(as) pela
   *         execu��o de 'preparedStatement'.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
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
      if (resultSet != null) {
        resultSet.getStatement().close();
        resultSet.close();
      } // if
    } // try-finally
  }

  /**
   * Retorna um PreparedStatement que retornar� um ResultSet contendo os dados
   * de Bem que atendam a express�o de filtro informada.
   * @param filterExpression Express�o de filtro limitando a lista de Bem
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornar� um ResultSet contendo
   *         os dados de Bem que atendam a express�o
             de filtro informada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    // pesquisa no banco
    String[] orderFields = {FIELD_DESCRICAO};
    return get(filterExpression, orderFields);
  }

  /**
   * Retorna um PreparedStatement que retornar� um ResultSet contendo os dados
   * de Bem que atendam a express�o de filtro informada.
   * @param filterExpression Express�o de filtro limitando a lista de Bem
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordena��o. Utilize 'DESC' ap�s
   *                       o nome do campo para ordena��o descendente.
   * @return Retorna um PreparedStatement que retornar� um ResultSet contendo
   *         os dados de Bem que atendam a express�o
             de filtro informada.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String   filterExpression,
                               String[] orderFields) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    return prepareSelect(TABLE_BEM,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um BemInfo referente a(o) Bem
   * indicado(a) pelos par�metros que representam sua chave prim�ria.
   * @param codigo C�digo.
   * @return Retorna um BemInfo referente a(o) Bem
   * indicado pelos par�metros que representam sua chave prim�ria.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO + "=?)");
    getStatement.setInt(1, codigo);
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
   * Retorna um array de BemInfo referente ao C�digo de Barras
   * indicado(a) pelo par�metro que representa ao C�digo de Barras
   * @param codigoBarras String
   * @return Retorna um array de BemInfo referente ao C�digo de Barras
   * indicado(a) pelo par�metro que representa ao C�digo de Barras
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo[] getByCodigoBarras(String codigoBarras) throws Exception {
    PreparedStatement getStatement = get("(" + FIELD_CODIGO_DE_BARRAS + "= ?)");
    getStatement.setString(1, codigoBarras);
    return get(getStatement);
  }


  /**
     * Retorna um array de BemInfo referente a Data de Inclusao
     * indicado(a) pelo par�metro que representa a Data de Inclusao
     * @param dataInclusao Timestamp
     * @return Retorna um array de BemInfo referente a Data de Inclusao
     * indicado(a) pelo par�metro que representa a Data de Inclusao
     * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
     *                   dados.
     */
    public BemInfo[] getByDataInclusao(Timestamp dataInclusao) throws Exception {
      PreparedStatement getStatement = get("(" + FIELD_DATA_INCLUSAO + "=?)");
      getStatement.setTimestamp(1, dataInclusao);
      return get(getStatement);
    }

    /**
     * Retorna um array de BemInfo referente a Data da �ltima Movimentacao
     * indicado(a) pelo par�metro que representa a Data da �ltima Movimentacao
     * @param dataUltimaMovimentacao Timestamp
     * @return Retorna um array de BemInfo referente a Data da �ltima Movimentacao
     * indicado(a) pelo par�metro que representa a Data da �ltima Movimentacao
     * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
     *                   dados.
     */
    public BemInfo[] getByDataUltimaMovimentacao(Timestamp dataUltimaMovimentacao) throws Exception {
      PreparedStatement getStatement = get("(" + FIELD_ULTIMA_MOVIMENTACAO + "=?)");
      getStatement.setTimestamp(1, dataUltimaMovimentacao);
      return get(getStatement);
    }

  /**
   * Retorna um array de BemInfo referente a descri��o
   * indicado(a) pelo par�metro que representa a descri��o
   * @param descricao String
   * @return Retorna um array de BemInfo referente a descri��o
   * indicado(a) pelo par�metro que representa a descri��o
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
    public BemInfo[] getByDescricao(String descricao) throws Exception {
      PreparedStatement getStatement = get("(" + FIELD_DESCRICAO + " LIKE ?)");
      getStatement.setString(1, descricao + "%");
      return get(getStatement);
    }

  /**
   * Retorna um BemInfo referente a(o) Tombo
   * indicado(a) pelo par�metro que representa o tombo.
   * @param tombo String
   * @return Retorna um BemInfo referente a(o) Tombo
   * indicado pelos par�metros que representa o tombo.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo getByTombo(String tombo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_TOMBO + "=?)");
    getStatement.setString(1, tombo);
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
   * Retorna um array de BemInfo referente ao Valor da Compra
   * indicado(a) pelo par�metro que representa ao Valor da Compra
   * @param valorCompra double
   * @return Retorna um array de BemInfo referente ao Valor da Compra
   * indicado(a) pelo par�metro que representa ao Valor da Compra
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public BemInfo[] getByValorCompra(double valorCompra) throws Exception {
    PreparedStatement getStatement = get("(" + FIELD_VALOR_DA_COMPRA + "=?)");
    getStatement.setDouble(1, valorCompra);
    return get(getStatement);
  }

  /**
   * Insere o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   * sucesso.
   * @param bemInfo BemInfo contendo as informa��es do(a) Bem que se
   *                    deseja incluir.
   * @return Insere o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(BemInfo bemInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO,
                       FIELD_CODIGO_DE_BARRAS,
                       FIELD_TOMBO,
                       FIELD_NUMERO_SERIE,
                       FIELD_CODIGO_GRUPO,
                       FIELD_DESCRICAO,
                       FIELD_CODIGO_SETOR,
                       FIELD_VALOR_DA_COMPRA,
                       FIELD_NOTA_FISCAL,
                       FIELD_CODIGO_FORNECEDOR,
                       FIELD_DATA_INCLUSAO,
                       FIELD_DATA_GARANTIA,
                       FIELD_PROPRIETARIO,
                       FIELD_PARTICULAR,
                       FIELD_ULTIMA_MOVIMENTACAO
                      };
    // prepara inser��o
    insertStatement = prepareInsert(TABLE_BEM, fields);
    try {
      // gera o sequencial do c�digo do Bem
      bemInfo.setCodigo(this.getNextCodigo());
      // preenche os valores dos campos
      insertStatement.setInt(1, bemInfo.getCodigo());
      insertStatement.setString(2, bemInfo.getCodigoDeBarras());
      insertStatement.setString(3, bemInfo.getTombo());
      insertStatement.setString(4, bemInfo.getNumeroSerie());
      insertStatement.setInt(5, bemInfo.getCodigoGrupo());
      insertStatement.setString(6, bemInfo.getDescricao());
      insertStatement.setInt(7, bemInfo.getCodigoSetor());
      insertStatement.setDouble(8, bemInfo.getValorDaCompra());
      insertStatement.setInt(9, bemInfo.getNotaFiscal());
      insertStatement.setInt(10, bemInfo.getCodigoFornecedor());
      insertStatement.setTimestamp(11, bemInfo.getDataInclusao());
      insertStatement.setTimestamp(12, bemInfo.getDataGarantia());
      insertStatement.setString(13, bemInfo.getProprietario());
      insertStatement.setInt(14, bemInfo.getParticular());
      insertStatement.setTimestamp(15, bemInfo.getUltimaMovimentacao());
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
   * @param bemInfo BemInfo contendo as informa��es do(a) Bem que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Bem identificado(a) por 'bemInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(BemInfo bemInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO_DE_BARRAS,
                       FIELD_TOMBO,
                       FIELD_NUMERO_SERIE,
                       FIELD_CODIGO_GRUPO,
                       FIELD_DESCRICAO,
                       FIELD_CODIGO_SETOR,
                       FIELD_VALOR_DA_COMPRA,
                       FIELD_NOTA_FISCAL,
                       FIELD_CODIGO_FORNECEDOR,
                       FIELD_DATA_INCLUSAO,
                       FIELD_DATA_GARANTIA,
                       FIELD_PROPRIETARIO,
                       FIELD_PARTICULAR,
                       FIELD_ULTIMA_MOVIMENTACAO
                      };
    // prepara atualiza��o
    updateStatement = prepareUpdate(TABLE_BEM,
                                    fields,
                                    "(" + FIELD_CODIGO + "=?)");
    try {
      // preenche os valores dos campos
      updateStatement.setString(1, bemInfo.getCodigoDeBarras());
      updateStatement.setString(2, bemInfo.getTombo());
      updateStatement.setString(3, bemInfo.getNumeroSerie());
      updateStatement.setInt(4, bemInfo.getCodigoGrupo());
      updateStatement.setString(5, bemInfo.getDescricao());
      updateStatement.setInt(6, bemInfo.getCodigoSetor());
      updateStatement.setDouble(7, bemInfo.getValorDaCompra());
      updateStatement.setInt(8, bemInfo.getNotaFiscal());
      updateStatement.setInt(9, bemInfo.getCodigoFornecedor());
      updateStatement.setTimestamp(10, bemInfo.getDataInclusao());
      updateStatement.setTimestamp(11, bemInfo.getDataGarantia());
      updateStatement.setString(12, bemInfo.getProprietario());
      updateStatement.setInt(13, bemInfo.getParticular());
      updateStatement.setTimestamp(14, bemInfo.getUltimaMovimentacao());
      // preenche os par�metros do Where
      updateStatement.setInt(15, bemInfo.getCodigo());
        // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

  /**
   * Gerar o c�digo sequencial do Bem
   * @return o c�digo sequencial do Bem
   * @throws Exception Em caso de exce��o na tentativa de acesso ao banco de
   *                   dados.
   */
  public int getNextCodigo() throws Exception {
      return super.getNextSequence(TABLE_BEM, FIELD_CODIGO);
  }

}
