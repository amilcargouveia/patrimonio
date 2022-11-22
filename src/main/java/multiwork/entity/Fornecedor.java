
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Fornecedor no banco de dados da aplicação.
 */
public class Fornecedor extends Entity {

  /**
   * Nome do campo 'Código' na tabela.
   */
  static public final String FIELD_CODIGO = "INCODIGOFORNECEDOR";
  /**
   * Nome do campo 'Nome' na tabela.
   */
  static public final String FIELD_NOME = "VANOME";
  /**
   * Nome do campo 'Endereço' na tabela.
   */
  static public final String FIELD_ENDERECO = "VAENDERECO";
  /**
   * Nome do campo 'Município' na tabela.
   */
  static public final String FIELD_MUNICIPIO = "VAMUNICIPIO";
  /**
   * Nome do campo 'Estado' na tabela.
   */
  static public final String FIELD_ESTADO = "CHUF";
  /**
   * Nome do campo 'CNPJ' na tabela.
   */
  static public final String FIELD_CNPJ = "VACNPJ";
  /**
   * Nome do campo 'Inscrição Estadual' na tabela.
   */
  static public final String FIELD_INSCRICAO_ESTADUAL = "VAINSCRICAOESTADUAL";
  /**
   * Nome do campo 'Inscrição Muncipal' na tabela.
   */
  static public final String FIELD_INSCRICAO_MUNCIPAL = "VAINSCRICAOMUNICIPAL";
  /**
   * Nome da tabela.
   */
  public static String TABLE_FORNECEDOR = "FORNECEDOR";

  /**
   * Construtor padrão.
   */
  public Fornecedor() {
  }

  /**
   * Exclui o(a) Fornecedor informado(a) por 'fornecedorInfo'.
   * Retorna true em caso de sucesso.
   * @param fornecedorInfo FornecedorInfo referente a(o) Fornecedor
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(FornecedorInfo fornecedorInfo) throws Exception {
    // prepara a exclusão
    PreparedStatement deleteStatement = prepareDelete(TABLE_FORNECEDOR,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // instância de Bem
      Bem bem = new Bem();
      // configurar conexão
      bem.setConnection(this.getConnection());
      // Verifica a existência de registros de Bem relacionados com o Fornecedor
      bem.checkForeignKeyConstraintFornecedor(fornecedorInfo.getCodigo());
      // parâmetros
      deleteStatement.setInt(1, fornecedorInfo.getCodigo());
      // executa
      return deleteStatement.execute();
    }
    finally {
      // fecha o Statement
      deleteStatement.close();
    } // try-finally
  }

  /**
   * Retorna um FornecedorInfo contendo os dados existentes no registro atual
   * de 'resultSet'.
   * @param resultSet ResultSet contendo os dados para serem lidos.
   * @return Retorna um FornecedorInfo contendo os dados existentes no registro
   *         atual de 'resultSet'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private FornecedorInfo get(ResultSet resultSet) throws Exception {
    // retorna
    return new FornecedorInfo(
                   resultSet.getInt(FIELD_CODIGO),
                   resultSet.getString(FIELD_NOME).trim(),
                   resultSet.getString(FIELD_ENDERECO).trim(),
                   resultSet.getString(FIELD_MUNICIPIO).trim(),
                   resultSet.getString(FIELD_ESTADO).trim(),
                   resultSet.getString(FIELD_CNPJ).trim(),
                   resultSet.getString(FIELD_INSCRICAO_ESTADUAL).trim(),
                   resultSet.getString(FIELD_INSCRICAO_MUNCIPAL).trim()
                   );
  }

  /**
   * Retorna um FornecedorInfo[] contendo a lista de Fornecedor retornados(as) pela execução
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que será executado e cujo
   *                          ResultSet será utilizado para pesquisa de registros.
   * @return Retorna um FornecedorInfo[] contendo a lista de Fornecedor retornados(as) pela
   *         execução de 'preparedStatement'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public FornecedorInfo[] get(PreparedStatement preparedStatement) throws Exception {
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
      FornecedorInfo[] result = new FornecedorInfo[vector.size()];
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
   * de Fornecedor que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Fornecedor
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Fornecedor que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    String [] orderfields = {FIELD_NOME};
    // pesquisa no banco
    return get(filterExpression, orderfields);
  }

  /**
   * Retorna um PreparedStatement que retornará um ResultSet contendo os dados
   * de Fornecedor que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Fornecedor
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordenação. Utilize 'DESC' após
   *                       o nome do campo para ordenação descendente.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Fornecedor que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String   filterExpression,
                               String[] orderFields) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    return prepareSelect(TABLE_FORNECEDOR,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um FornecedorInfo referente a(o) Fornecedor
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param codigo Código.
   * @return Retorna um FornecedorInfo referente a(o) Fornecedor
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public FornecedorInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO + "=?)");
    getStatement.setInt(1, codigo);
    FornecedorInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new RecordNotFoundException("Nenhum(a) Fornecedor encontrado(a).");
    else if (result.length > 1)
      throw new ManyRecordsFoundException("Mais de um(a) Fornecedor encontrado(a).");
    else
      return result[0];
  }

  /**
   * Retorna um FornecedorInfo[] contendo a lista de Fornecedor
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param nome Nome.
   * @return Retorna um FornecedorInfo[] contendo a lista de Fornecedor
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public FornecedorInfo[] getByNome(String nome) throws Exception {
     // prepara a consulta
     PreparedStatement getStatement = get("(" + FIELD_NOME + " LIKE ?)");
     getStatement.setString(1, nome + "%");
     // retorna
     return get(getStatement);

  }

  /**
   * Gerar o código sequencial do Fornecedor
   * @return o código sequencial do Fornecedor
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public int getNextCodigo() throws Exception {
    return super.getNextSequence(TABLE_FORNECEDOR, FIELD_CODIGO);
  }

  /**
   * Insere o(a) Fornecedor identificado(a) por 'fornecedorInfo' e retorna true em caso de
   * sucesso.
   * @param fornecedorInfo FornecedorInfo contendo as informações do(a) Fornecedor que se
   *                    deseja incluir.
   * @return Insere o(a) Fornecedor identificado(a) por 'fornecedorInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(FornecedorInfo fornecedorInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO,
                       FIELD_NOME,
                       FIELD_ENDERECO,
                       FIELD_MUNICIPIO,
                       FIELD_ESTADO,
                       FIELD_CNPJ,
                       FIELD_INSCRICAO_ESTADUAL,
                       FIELD_INSCRICAO_MUNCIPAL
                      };
    // prepara inserção
    insertStatement = prepareInsert(TABLE_FORNECEDOR, fields);
    try {
      // gera o sequencial do código do fornecedor
      fornecedorInfo.setCodigo(this.getNextCodigo());
      // preenche os valores dos campos
      insertStatement.setInt(1, fornecedorInfo.getCodigo());
      insertStatement.setString(2, fornecedorInfo.getNome());
      insertStatement.setString(3, fornecedorInfo.getEndereco());
      insertStatement.setString(4, fornecedorInfo.getMunicipio());
      insertStatement.setString(5, fornecedorInfo.getEstado());
      insertStatement.setString(6, fornecedorInfo.getCnpj());
      insertStatement.setString(7, fornecedorInfo.getInscricaoEstadual());
      insertStatement.setString(8, fornecedorInfo.getInscricaoMuncipal());
      // insere
      return insertStatement.execute();
    }
    finally {
      // fecha o Statement
      insertStatement.close();
    } // try-finally
  }

  /**
   * Atualiza o(a) Fornecedor identificado(a) por 'fornecedorInfo' e retorna true em caso de
   * sucesso.
   * @param fornecedorInfo FornecedorInfo contendo as informações do(a) Fornecedor que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Fornecedor identificado(a) por 'fornecedorInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(FornecedorInfo fornecedorInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_NOME,
                       FIELD_ENDERECO,
                       FIELD_MUNICIPIO,
                       FIELD_ESTADO,
                       FIELD_CNPJ,
                       FIELD_INSCRICAO_ESTADUAL,
                       FIELD_INSCRICAO_MUNCIPAL
                      };
    // prepara atualização
    updateStatement = prepareUpdate(TABLE_FORNECEDOR,
                                    fields,
                                    "(" + FIELD_CODIGO + "=?)");
    try {
      // preenche os valores dos campos
      updateStatement.setString(1, fornecedorInfo.getNome());
      updateStatement.setString(2, fornecedorInfo.getEndereco());
      updateStatement.setString(3, fornecedorInfo.getMunicipio());
      updateStatement.setString(4, fornecedorInfo.getEstado());
      updateStatement.setString(5, fornecedorInfo.getCnpj());
      updateStatement.setString(6, fornecedorInfo.getInscricaoEstadual());
      updateStatement.setString(7, fornecedorInfo.getInscricaoMuncipal());
      // preenche os parâmetros do Where
      updateStatement.setInt(8, fornecedorInfo.getCodigo());
        // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

}
