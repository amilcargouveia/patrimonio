package multiwork.entity;

/**
 * Representa as informa��es contidas pela entidade Usuario.
 */
public class UsuarioInfo {

  private int    codigo             = 0;
  private int    nivel              = 0;
  private String nome               = "";
  private String senha              = "";

  /**
   * Construtor padr�o.
   * @param codigo C�digo do usu�rio.
   * @param nome Nome do usu�rio.
   * @param senha Senha do usu�rio.
   * @param nivel N�vel do usu�rio: 0-Operador, 1-Inspetor, 2-Gerente.
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
