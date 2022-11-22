
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informações contidas pela entidade Setor.
 */
public class SetorInfo {

  private int codigo;
  private String descricao;

  /**
   * Construtor padrão.
   * @param codigo Código.
   * @param descricao Descrição.
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
   * Retorna o valor de Código.
   * @return Retorna o valor de Código.
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Retorna o valor de Descrição.
   * @return Retorna o valor de Descrição.
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Define o valor de Código.
   * @param codigo Novo valor para Código.
   */
  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  /**
   * Define o valor de Descrição.
   * @param descricao Novo valor para Descrição.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
