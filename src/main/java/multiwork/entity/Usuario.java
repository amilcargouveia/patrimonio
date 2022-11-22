package multiwork.entity;

import java.sql.*;
import java.util.*;

import multiwork.util.*;
import multiwork.xml.entity.*;

/**
 * Representa a entidade Usuario no banco de dados da aplicação.
 */
public class Usuario extends Entity {

  /**
   * Nome do campo 'código' na tabela.
   */
  public static String FIELD_CODIGO;
  /**
   * Nome do campo 'nome' na tabela.
   */
  public static String FIELD_NOME;
  /**
   * Nome do campo 'nível' na tabela.
   */
  public static String FIELD_NIVEL;
  /**
   * Nome do campo 'senha' na tabela.
   */
  public static String FIELD_SENHA;
  /**
   * Nome da tabela.
   */
  public static String TABLE_USUARIO;

  /**
   * Nome do usuário padrão administrador da aplicação.
   */
  static public String ADMIN_USER_NAME;
  /**
   * Senha padrão do usuário padrão administrador da aplicação.
   */
  static public String ADMIN_PASSWORD;
  /**
   * Código que identifica o usuário com direitos básicos.
   */
  public static final int    NIVEL_BASICO        = 1;
  /**
   * Código que identifica o usuário com direitos avançados.
   */
  public static final int    NIVEL_AVANCADO      = 2;
  /**
   * Código que identifica o usuário com direitos administrativos.
   */
  public static final int    NIVEL_ADMINISTRADOR = 3;

  public Usuario(String usuarioFilePath) {
    try {
      // configurações de Usuário
      UsuarioFile usuarioFile = new UsuarioFile(usuarioFilePath);
      FIELD_CODIGO = usuarioFile.fieldCodigo();
      FIELD_NIVEL  = usuarioFile.fieldNivel();
      FIELD_NOME   = usuarioFile.fieldNome();
      FIELD_SENHA  = usuarioFile.fieldSenha();
      ADMIN_USER_NAME = usuarioFile.adminUserName();
      ADMIN_PASSWORD  = usuarioFile.adminPassword();
      TABLE_USUARIO   = usuarioFile.tableName();
    }
    catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Altera a senha do usuário identificador por 'nome'.
   * @param nome Nome do usuário cuja senha será alterada.
   * @param senha Senha atual do usuário.
   * @param novaSenha Nova senha do usuário.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean changePassword(String nome,
                                String senha,
                                String novaSenha) throws Exception {
    // se não localizou o usuário...exceção
    UsuarioInfo usuarioInfo = getByNome(nome);
    if (usuarioInfo == null)
      throw new Exception(getClass().getName() + ".changePassword(): usuário não encontrado '" + nome + "'.");
    // se a senha atual está errada...exceção
    if (!usuarioInfo.getSenha().equals(senha))
      throw new Exception(getClass().getName() + ".changePassword(): a senha atual não confere.");
    // altera a senha
    UsuarioInfo newUsuarioInfo = new UsuarioInfo(usuarioInfo.getCodigo(),
                                                 usuarioInfo.getNome(),
                                                 novaSenha,
                                                 usuarioInfo.getNivel());
    // põe no banco
    return update(newUsuarioInfo);
  }

  /**
   * Verifica a existência do usuário administrador do sistema e em caso de
   * inexistencia cria-o.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   * @see Usuario#ADMIN_USER_NAME
   * @see Usuario#ADMIN_PASSWORD
   * @see Usuario#NIVEL_ADMINISTRADOR
   */
  public void checkAdminExists() throws Exception {
    // procura pelo administrador
    UsuarioInfo usuarioInfo = getByNome(ADMIN_USER_NAME);
    // se não existe...inclui
    if (usuarioInfo == null) {
      // informações do usuário
      usuarioInfo = new UsuarioInfo(0,
                                    ADMIN_USER_NAME,
                                    ADMIN_PASSWORD,
                                    NIVEL_ADMINISTRADOR);
      // inclui
      insert(usuarioInfo);
    } // if
  }

  /**
   * Exclui o usuário informado por 'usuarioInfo'. Retorna true em caso de sucesso.
   * @param usuarioInfo UsuarioInfo referente ao usuário que se deseja excluir.
   * @return Retorna true em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean delete(UsuarioInfo usuarioInfo) throws Exception {
    // prepara a exclusão
    PreparedStatement deleteStatement = prepareDelete(TABLE_USUARIO, FIELD_CODIGO + "=?");
    try {
      // parâmetros
      deleteStatement.setInt(1, usuarioInfo.getCodigo());
      // executa
      return deleteStatement.execute();
    }
    finally {
      // fecha o Statement
      deleteStatement.close();
    } // try-finally
  }

  /**
   * Retorna um UsuarioInfo contendo os dados existentes no registro atual
   * de 'resultSet'.
   * @param resultSet ResultSet contendo os dados para serem lidos.
   * @return Retorna um UsuarioInfo contendo os dados existentes no registro
   *         atual de 'resultSet'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  private UsuarioInfo get(ResultSet resultSet) throws Exception {
    // retorna o UsuarioInfo
    return new UsuarioInfo(resultSet.getInt(FIELD_CODIGO),
                           resultSet.getString(FIELD_NOME).trim(),
                           resultSet.getString(FIELD_SENHA).trim(),
                           resultSet.getInt(FIELD_NIVEL));
  }

  /**
   * Retorna um UsuarioInfo[] contendo a lista Usuário retornados pela execução
   * de 'preparedStatement'.
   * @param preparedStatement PreparedStatement que será executado e cujo
   *                          ResultSet será utilizado para pesquisa de registros.
   * @return Retorna um UsuarioInfo[] contendo a lista Usuário retornados pela
   *         execução de 'preparedStatement'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo[] get(PreparedStatement preparedStatement) throws Exception {
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
      UsuarioInfo[] result = new UsuarioInfo[vector.size()];
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
   * dos Usuários que atendam a expressão de filtro informada.
   * @param filterExpression Expressão de filtro limitando a lista de Usuários
   *                         que se deseja retornar.
   * @return Retorna um PreparedStatement que retornará um ResultSet contendo
   *         os dados dos Usuários que atendam a expressão de filtro informada.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public PreparedStatement get(String filterExpression) throws Exception {
    // pesquisa no banco
    String[] fields = {"*"};
    String[] orderFields = {};
    return prepareSelect(TABLE_USUARIO,
                         fields,
                         orderFields,
                         filterExpression);
  }

  /**
   * Retorna um UsuarioInfo referente ao Usuário indicado por 'codigo'.
   * @param codigo Código do Usuário que se deseja localizar.
   * @return Retorna um UsuarioInfo referente ao Usuário indicado por 'codigo'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo getByCodigo(int codigo) throws Exception {
    // prepara a consulta
    PreparedStatement getStatement = get(FIELD_CODIGO + "=?");
    getStatement.setInt(1, codigo);
    UsuarioInfo[] result = get(getStatement);
    // retorna
    if (result.length == 0)
      throw new Exception("Nenhum Usuário encontrado: " + codigo + ".");
    else if (result.length > 1)
      throw new Exception("Mais de um Usuário encontrado: " + codigo + ".");
    else
      return result[0];
  }

  /**
   * Retorna o UsuarioInfo referente ao Usuário cujo nome seja igual a 'nome'.
   * @param nome Nome do usuário que se deseja localizar.
   * @return Retorna o UsuarioInfo referente ao Usuário cujo nome seja igual a 'nome'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public UsuarioInfo getByNome(String nome) throws Exception {
    // pega a lista o usuário com o nome informado
    PreparedStatement getStatement = get(FIELD_NOME + "=?");
    getStatement.setString(1, nome);
    UsuarioInfo[] usuariosInfo = get(getStatement);
    // se não achamos nada...retorna null
    if (usuariosInfo.length == 0)
      return null;
    // se achamos...retorna o primeiro, e provavelmente único, usuário
    else
      return usuariosInfo[0];
  }

  /**
   * Insere o Usuário identificado por 'usuarioInfo' e retorna true em caso de
   * sucesso.
   * @param usuarioInfo UsuarioInfo contendo as informações do usuário que se
   *                    deseja incluir.
   * @return Insere o usuário identificado por 'usuarioInfo' e retorna true em
   *         caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean insert(UsuarioInfo usuarioInfo) throws Exception {
    // nosso Statement
    PreparedStatement insertStatement;
    // nossos campos
    String[] fields = {FIELD_CODIGO,
                       FIELD_NOME,
                       FIELD_SENHA,
                       FIELD_NIVEL};
    // prepara inserção
    insertStatement = prepareInsert(TABLE_USUARIO, fields);
    try {
      // põe os outros valores
      insertStatement.setInt(1, getNextSequence(TABLE_USUARIO, FIELD_CODIGO));
      insertStatement.setString(2, usuarioInfo.getNome());
      insertStatement.setString(3, usuarioInfo.getSenha());
      insertStatement.setInt(4, usuarioInfo.getNivel());
      // insere
      return insertStatement.execute();
    }
    finally {
      // fecha o Statement
      insertStatement.close();
    } // try-finally
  }

  /**
   * Retorna o UsuarioInfo referente ao Usuário identificado por 'nome' e 'senha'.
   * @param nome Nome do usuário para efetuar logon.
   * @param senha Senha do usuário.
   * @return Retorna o UsuarioInfo referente ao Usuário identificado por 'nome' e 'senha'.
   * @throws Exception Em caso de exceção na tentativa de acesso ao usuário ou
   *                   do nome e/ou senha serem inválidos.
   */
  public UsuarioInfo logon(String nome,
                           String senha) throws Exception {
    // localiza o usuário
    UsuarioInfo result = getByNome(nome);
    if ((result == null) || (!result.getSenha().equals(senha)))
       throw new Exception("Nome e/ou senha inválidos.");
    // retorna
    return result;
  }

  /**
   * Atualiza o usuário identificado por 'usuarioInfo' e retorna true em caso
   * de sucesso.
   * @param usuarioInfo UsuarioInfo contendo as informações do usuário que se
   *                    deseja atualizar.
   * @return Atualiza o usuário identificado por 'usuarioInfo' e retorna true
   *         em caso de sucesso.
   * @throws Exception Em caso de exceção na tentativa de acesso ao banco de
   *                   dados.
   */
  public boolean update(UsuarioInfo usuarioInfo) throws Exception {
    // nomes dos campos
    String[] fields = {FIELD_NOME,
                       FIELD_SENHA,
                       FIELD_NIVEL};
    // prepara atualização
    PreparedStatement updateStatement = prepareUpdate(TABLE_USUARIO,
                                                      fields,
                                                      FIELD_CODIGO + "=?");
    try {
      // põe os valores
      updateStatement.setString(1, usuarioInfo.getNome());
      updateStatement.setString(2, usuarioInfo.getSenha());
      updateStatement.setInt(3, usuarioInfo.getNivel());
      updateStatement.setInt(4, usuarioInfo.getCodigo());
      // atualiza
      return updateStatement.execute();
    }
    finally {
      // fecha o Statement
      updateStatement.close();
    } // try-finally
  }

  public void setConnection(Connection connection) {
    super.setConnection(connection);
    // verifica a existencia do usuário Administrador
    try {
      checkAdminExists();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    } // try-catch
  }

}
