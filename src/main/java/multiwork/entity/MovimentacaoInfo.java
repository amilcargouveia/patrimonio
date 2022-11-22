
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informações contidas pela entidade Movimentação.
 */
public class MovimentacaoInfo {

  private int codigo;
  private int codigoBem;
  private Timestamp data;
  private int codigoSetorOrigem;
  private int codigoSetorDestino;
  private String descricao;

  /**
   * Construtor padrão.
   * @param codigo Código.
   * @param codigoBem Código Bem.
   * @param data Data.
   * @param codigoSetorOrigem Código Setor Origem.
   * @param codigoSetorDestino Código Setor Destino.
   * @param descricao Descrição.
   */
  public MovimentacaoInfo(
           int codigo,
           int codigoBem,
           Timestamp data,
           int codigoSetorOrigem,
           int codigoSetorDestino,
           String descricao
         ) {
    // guarda nossos dados
    this.codigo = codigo;
    this.codigoBem = codigoBem;
    this.data = data;
    this.codigoSetorOrigem = codigoSetorOrigem;
    this.codigoSetorDestino = codigoSetorDestino;
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
   * Retorna o valor de Código Setor Origem.
   * @return Retorna o valor de Código Setor Origem
   */
  public int getCodigoSetorOrigem() {
    return codigoSetorOrigem;
  }

  /**
   * Retorna o valor de Código Setor Destino.
   * @return Retorna o valor de Código Setor Destino
   */
  public int getCodigoSetorDestino() {
    return codigoSetorDestino;
  }
  /**
   * Retorna o valor de Código Bem.
   * @return Retorna o valor de Código Bem.
   */
  public int getCodigoBem() {
    return codigoBem;
  }

  /**
   * Retorna o valor de Data.
   * @return Retorna o valor de Data.
   */
  public Timestamp getData() {
    return data;
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
   * Define o valor de Código Setor Destino.
   * @param codigoSetor Novo valor para Código Setor Destino.
   */
  public void setCodigoSetorDestino(int codigoSetorDestino) {
    this.codigoSetorDestino = codigoSetorDestino;
  }


  /**
   * Define o valor de Código Setor Origem.
   * @param codigoSetor Novo valor para Código Setor Origem.
   */
  public void setCodigoSetorOrigem(int codigoSetorOrigem) {
    this.codigoSetorOrigem = codigoSetorOrigem;
  }

  /**
   * Define o valor de Código Bem.
   * @param codigoBem Novo valor para Código Bem.
   */
  public void setCodigoBem(int codigoBem) {
    this.codigoBem = codigoBem;
  }

  /**
   * Define o valor de Data.
   * @param data Novo valor para Data.
   */
  public void setData(Timestamp data) {
    this.data = data;
  }

  /**
   * Define o valor de Descrição.
   * @param descricao Novo valor para Descrição.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
