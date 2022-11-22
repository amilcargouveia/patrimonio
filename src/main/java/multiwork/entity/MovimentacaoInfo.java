
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informa��es contidas pela entidade Movimenta��o.
 */
public class MovimentacaoInfo {

  private int codigo;
  private int codigoBem;
  private Timestamp data;
  private int codigoSetorOrigem;
  private int codigoSetorDestino;
  private String descricao;

  /**
   * Construtor padr�o.
   * @param codigo C�digo.
   * @param codigoBem C�digo Bem.
   * @param data Data.
   * @param codigoSetorOrigem C�digo Setor Origem.
   * @param codigoSetorDestino C�digo Setor Destino.
   * @param descricao Descri��o.
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
   * Retorna o valor de C�digo.
   * @return Retorna o valor de C�digo.
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Retorna o valor de C�digo Setor Origem.
   * @return Retorna o valor de C�digo Setor Origem
   */
  public int getCodigoSetorOrigem() {
    return codigoSetorOrigem;
  }

  /**
   * Retorna o valor de C�digo Setor Destino.
   * @return Retorna o valor de C�digo Setor Destino
   */
  public int getCodigoSetorDestino() {
    return codigoSetorDestino;
  }
  /**
   * Retorna o valor de C�digo Bem.
   * @return Retorna o valor de C�digo Bem.
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
   * Define o valor de C�digo Setor Destino.
   * @param codigoSetor Novo valor para C�digo Setor Destino.
   */
  public void setCodigoSetorDestino(int codigoSetorDestino) {
    this.codigoSetorDestino = codigoSetorDestino;
  }


  /**
   * Define o valor de C�digo Setor Origem.
   * @param codigoSetor Novo valor para C�digo Setor Origem.
   */
  public void setCodigoSetorOrigem(int codigoSetorOrigem) {
    this.codigoSetorOrigem = codigoSetorOrigem;
  }

  /**
   * Define o valor de C�digo Bem.
   * @param codigoBem Novo valor para C�digo Bem.
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
   * Define o valor de Descri��o.
   * @param descricao Novo valor para Descri��o.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

}
