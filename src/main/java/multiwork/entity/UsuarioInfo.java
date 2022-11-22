package multiwork.entity;

/**
 * Representa as informações contidas pela entidade Usuario.
 */
public class UsuarioInfo {

  private int    codigo             = 0;
  private int    nivel              = 0;
  private String nome               = "";
  private String senha              = "";

  /**
   * Construtor padrão.
   * @param codigo Código do usuário.
   * @param nome Nome do usuário.
   * @param senha Senha do usuário.
   * @param nivel Nível do usuário: 0-Operador, 1-Inspetor, 2-Gerente.
   */
  public UsuarioInfo(int    codigo,
                     String nome,
                     String senha,
                     int    nivel) {
    // guarda nossos dados
    this.codigo             = codigo;
    this.nome               = nome;
    this.senha              = senha;
    this.nivel              = nivel;
  }

  public int getCodigo() {
    return codigo;
  }

  public int getNivel() {
    return nivel;
  }

  public String getNome() {
    return nome;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setNivel(int nivel) {
    this.nivel = nivel;
  }

  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

}
