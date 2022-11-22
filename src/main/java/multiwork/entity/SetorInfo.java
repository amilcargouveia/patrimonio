
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informa��es contidas pela entidade Setor.
 */
public class SetorInfo {

  private int codigo;
  private String descricao;

  /**
   * Construtor padr�o.
   * @param codigo C�digo.
   * @param descricao Descri��o.
   */
  public SetorInfo(
           int codigo,
           String descricao
         ) {
    // guarda nossos dados
    this.codigo = codigo;
    this.descricao = descricao;
   }

  /**
   * Retorna o valor de C�digo.
   * @return Retorna o valor de C�digo.
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Retorna o valor de Descri��o.
   * @return Retorna o valor de Descri��o.
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Define o valor de C�digo.
   * @param codigo Novo valor para C�digo.
   */
  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  /**
   * Define o valor de Descri��o.
   * @param descricao Novo valor para Descri��o.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
