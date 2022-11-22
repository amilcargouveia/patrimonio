
package multiwork.entity;

import java.sql.*;
import java.util.*;

/**
 * Representa a entidade Setor no banco de dados da aplicação.
 */
public class Setor extends Entity {

  /**
   * Nome do campo 'Código' na tabela.
   */
  static public final String FIELD_CODIGO = "inCodigoSetor";
  /**
   * Nome do campo 'Descrição' na tabela.
   */
  static public final String FIELD_DESCRICAO = "vaDescricao";
  /**
   * Nome da tabela.
   */
  public static String TABLE_SETOR = "setor";

  /**
   * Construtor padrão.
   */
  public Setor() {
  }

  /**
   * Exclui o(a) Setor informado(a) por 'setorInfo'.
   * Retorna true em caso de sucesso.
   * @param setorInfo SetorInfo referente a(o) Setor
   *        que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(SetorInfo setorInfo) throws Exception {
    // prepara a exclusão
    PreparedStatement deleteStatement = prepareDelete(TABLE_SETOR,
                                                      "(" + FIELD_CODIGO + "=?)");
    try {
      // instância de Bem
      Bem bem = new Bem();
      // configurar conexão
      bem.setConnection(this.getConnection());
      // Verifica a existência de registros de Bem relacionados com o Setor
      bem.checkForeignKeyConstraintSetor(setorInfo.getCodigo());

      // parâmetros
      deleteStatement.setInt(1, setorInfo.getCodigo());
      // executa
      return deleteStatement.execute();
    }
    finally {
      // fecha o Statement
      deleteStatement.close();
    } // try-finally
  }

  /**
   * Retorna um SetorInfo contendo os dados existentes no registro atual
   * de 'resultSet'.
   * @param resultSet ResultSet contendo os dados para serem lidos.
   * @return Retorna um SetorInfo contendo os dados existentes no registro
   *         atual de 'resultSet'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private SetorInfo get(ResultSet resultSet) throws Exception {
    // retorna
    return new SetorInfo(
                   resultSet.getInt(FIELD_CODIGO),
                   resultSet.getString(FIELD_DESCRICAO).trim()
                   );
  }

  /**
   * Retorna um SetorInfo[] contendo a lista de Setor retornados(as) pela execução
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que será executado e cujo
   *                          ResultSet será utilizado para pesquisa de registros.
   * @return Retorna um SetorInfo[] contendo a lista de Setor retornados(as) pela
   *         execução de 'preparedStatement'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public SetorInfo[] get(PreparedStatement preparedStatement) throws Exception {
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
      SetorInfo[] result = new SetorInfo[vector.size()];
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
   * de Setor que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Setor
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Setor que atendam a expressão
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
   * de Setor que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Setor
   *                         que se deseja retornar.
   * @param orderFields Nomes dos campos para ordenação. Utilize 'DESC' após
   *                       o nome do campo para ordenação descendente.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados de Setor que atendam a expressão
             de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String   filterExpression,
                               String[] orderFields) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    return prepareSelect(TABLE_SETOR,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um SetorInfo referente a(o) Setor
   * indicado(a) pelos parâmetros que representam sua chave primária.
   * @param codigo Código.
   * @return Retorna um SetorInfo referente a(o) Setor
   * indicado pelos parâmetros que representam sua chave primária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public SetorInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_CODIGO + "=?)");
    getStatement.setInt(1, codigo);
    SetorInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new RecordNotFoundException("Nenhum(a) Setor encontrado(a).");
    else if (result.length > 1)
      throw new ManyRecordsFoundException("Mais de um(a) Setor encontrado(a).");
    else
      return result[0];
  }

  /**
   * Retorna um SetorInfo[] contendo a lista de Setor
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @param descricao Descrição.
   * @return Retorna um SetorInfo[] contendo a lista de Setor
   * indicados(as) pelos parâmetros que representam sua chave secundária.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public SetorInfo[] getByDescricao(String descricao) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get("(" + FIELD_DESCRICAO + " LIKE ?)");
    getStatement.setString(1, descricao + "%");
    // retorna
    return get(getStatement);
  }

  /**
   * Gerar o código sequencial do Setor
   * @return o código sequencial do Setor
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public int getNextCodigo() throws Exception {
      return super.getNextSequence(TABLE_SETOR, FIELD_CODIGO);
  }


  /**
   * Insere o(a) Setor identificado(a) por 'setorInfo' e retorna true em caso de
   * sucesso.
   * @param setorInfo SetorInfo contendo as informações do(a) Setor que se
   *                    deseja incluir.
   * @return Insere o(a) Setor identificado(a) por 'setorInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(SetorInfo setorInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {
                       FIELD_CODIGO,
                       FIELD_DESCRICAO
                      };
    // prepara inserção
    insertStatement = prepareInsert(TABLE_SETOR, fields);
    try {
      // gera o sequencial do código do Setor
      setorInfo.setCodigo(this.getNextCodigo());
      // preenche os valores dos campos
      insertStatement.setInt(1, setorInfo.getCodigo());
      insertStatement.setString(2, setorInfo.getDescricao());
      // insere
      return insertStatement.execute();
    }
    finally {
      // fecha o Statement
      insertStatement.close();
    } // try-finally
  }

  /**
   * Atualiza o(a) Setor identificado(a) por 'setorInfo' e retorna true em caso de
   * sucesso.
   * @param setorInfo SetorInfo contendo as informações do(a) Setor que se
   *                    deseja atualizar.
   * @return Atualiza o(a) Setor identificado(a) por 'setorInfo' e retorna true em caso de
   *         sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(SetorInfo setorInfo) throws Exception {
    // nosso Statement
    PreparedStatement updateStatement;
    // nossos campos
    String[] fields = {
                       FIELD_DESCRICAO
                      };
    // prepara atualização
    updateStatement = prepareUpdate(TABLE_SETOR,
                                    fields,
                                    "(" + FIELD_CODIGO + "=?)");
    try {
      // preenche os valores dos campos
      updateStatement.setString(1, setorInfo.getDescricao());
      // preenche os parâmetros do Where
      updateStatement.setInt(2, setorInfo.getCodigo());
        // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

}
