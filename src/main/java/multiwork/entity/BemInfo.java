
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informa��es contidas pela entidade Bem.
 */
public class BemInfo {

  private int codigo;
  private String codigoDeBarras;
  private String tombo;
  private String numeroSerie;
  private int codigoGrupo;
  private String descricao;
  private int codigoSetor;
  private double valorDaCompra;
  private int notaFiscal;
  private int codigoFornecedor;
  private Timestamp dataInclusao;
  private Timestamp dataGarantia;
  private String proprietario;
  private int particular;
  private Timestamp ultimaMovimentacao;

  /**
   * Construtor padr�o.
   * @param codigo C�digo.
   * @param codigoDeBarras C�digo de Barras.
   * @param tombo Tombo.
   * @param numeroSerie N�mero S�rie.
   * @param codigoGrupo C�digo Grupo.
   * @param descricao Descri��o.
   * @param codigoSetor C�digo Setor.
   * @param valorDaCompra Valor da Compra.
   * @param notaFiscal Nota Fiscal.
   * @param codigoFornecedor C�digo Fornecedor.
   * @param dataInclusao Data Inclus�o.
   * @param dataGarantia Data Gerantia.
   * @param proprietario Propriet�rio.
   * @param particular Particular.
   * @param ultimaMovimentacao �ltima Movimenta��o.
   */
  public BemInfo(
           int codigo,
           String codigoDeBarras,
           String tombo,
           String numeroSerie,
           int codigoGrupo,
           String descricao,
           int codigoSetor,
           double valorDaCompra,
           int notaFiscal,
           int codigoFornecedor,
           Timestamp dataInclusao,
           Timestamp dataGarantia,
           String proprietario,
           int particular,
           Timestamp ultimaMovimentacao
         ) {
    // guarda nossos dados
    this.codigo = codigo;
    this.codigoDeBarras = codigoDeBarras;
    this.tombo = tombo;
    this.numeroSerie = numeroSerie;
    this.codigoGrupo = codigoGrupo;
    this.descricao = descricao;
    this.codigoSetor = codigoSetor;
    this.valorDaCompra = valorDaCompra;
    this.notaFiscal = notaFiscal;
    this.codigoFornecedor = codigoFornecedor;
    this.dataInclusao = dataInclusao;
    this.dataGarantia = dataGarantia;
    this.proprietario = proprietario;
    this.particular = particular;
    this.ultimaMovimentacao = ultimaMovimentacao;
  }

  /**
   * Retorna o valor de C�digo.
   * @return Retorna o valor de C�digo.
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Retorna o valor de C�digo de Barras.
   * @return Retorna o valor de C�digo de Barras.
   */
  public String getCodigoDeBarras() {
    return codigoDeBarras;
  }

  /**
   * Retorna o valor de Tombo.
   * @return Retorna o valor de Tombo.
   */
  public String getTombo() {
    return tombo;
  }

  /**
   * Retorna o valor de N�mero S�rie.
   * @return Retorna o valor de N�mero S�rie.
   */
  public String getNumeroSerie() {
    return numeroSerie;
  }

  /**
   * Retorna o valor de C�digo Grupo.
   * @return Retorna o valor de C�digo Grupo.
   */
  public int getCodigoGrupo() {
    return codigoGrupo;
  }

  /**
   * Retorna o valor de Descri��o.
   * @return Retorna o valor de Descri��o.
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Retorna o valor de C�digo Setor.
   * @return Retorna o valor de C�digo Setor.
   */
  public int getCodigoSetor() {
    return codigoSetor;
  }

  /**
   * Retorna o valor de Valor da Compra.
   * @return Retorna o valor de Valor da Compra.
   */
  public double getValorDaCompra() {
    return valorDaCompra;
  }

  /**
   * Retorna o valor de Nota Fiscal.
   * @return Retorna o valor de Nota Fiscal.
   */
  public int getNotaFiscal() {
    return notaFiscal;
  }

  /**
   * Retorna o valor de C�digo Fornecedor.
   * @return Retorna o valor de C�digo Fornecedor.
   */
  public int getCodigoFornecedor() {
    return codigoFornecedor;
  }

  /**
   * Retorna o valor de Data Inclus�o.
   * @return Retorna o valor de Data Inclus�o.
   */
  public Timestamp getDataInclusao() {
    return dataInclusao;
  }

  /**
   * Retorna o valor de Data Garantia.
   * @return Retorna o valor de Data Garantia.
   */
  public Timestamp getDataGarantia() {
    return dataGarantia;
  }

  /**
   * Retorna o valor de Propriet�rio.
   * @return Retorna o valor de Propriet�rio.
   */
  public String getProprietario() {
    return proprietario;
  }

  /**
   * Retorna o valor de Particular.
   * @return Retorna o valor de Particular.
   */
  public int getParticular() {
    return particular;
  }

  /**
   * Retorna o valor de �ltima Movimenta��o.
   * @return Retorna o valor de �ltima Movimenta��o.
   */
  public Timestamp getUltimaMovimentacao() {
    return ultimaMovimentacao;
  }

  /**
   * Define o valor de C�digo.
   * @param codigo Novo valor para C�digo.
   */
  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  /**
   * Define o valor de C�digo de Barras.
   * @param codigoDeBarras Novo valor para C�digo de Barras.
   */
  public void setCodigoDeBarras(String codigoDeBarras) {
    this.codigoDeBarras = codigoDeBarras;
  }

  /**
   * Define o valor de Tombo.
   * @param tombo Novo valor para Tombo.
   */
  public void setTombo(String tombo) {
    this.tombo = tombo;
  }

  /**
   * Define o valor de C�digo Grupo.
   * @param codigoGrupo Novo valor para C�digo Grupo.
   */
  public void setCodigoGrupo(int codigoGrupo) {
    this.codigoGrupo = codigoGrupo;
  }

  /**
   * Define o valor de Descri��o.
   * @param descricao Novo valor para Descri��o.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * Define o valor de C�digo Setor.
   * @param codigoSetor Novo valor para C�digo Setor.
   */
  public void setCodigoSetor(int codigoSetor) {
    this.codigoSetor = codigoSetor;
  }

  /**
   * Define o valor de Valor da Compra.
   * @param valorDaCompra Novo valor para Valor da Compra.
   */
  public void setValorDaCompra(double valorDaCompra) {
    this.valorDaCompra = valorDaCompra;
  }

  /**
   * Define o valor de Nota Fiscal.
   * @param notaFiscal Novo valor para Nota Fiscal.
   */
  public void setNotaFiscal(int notaFiscal) {
    this.notaFiscal = notaFiscal;
  }

  /**
   * Define o valor de C�digo Fornecedor.
   * @param codigoFornecedor Novo valor para C�digo Fornecedor.
   */
  public void setCodigoFornecedor(int codigoFornecedor) {
    this.codigoFornecedor = codigoFornecedor;
  }

  /**
   * Define o valor de Data Inclus�o.
   * @param dataInclusao Novo valor para Data Inclus�o.
   */
  public void setDataInclusao(Timestamp dataInclusao) {
    this.dataInclusao = dataInclusao;
  }

  /**
   * Define o valor de Data Garantia.
   * @param dataGarantia Novo valor para Data Garantia.
   */
  public void setDataGarantia(Timestamp dataGarantia) {
    this.dataGarantia = dataGarantia;
  }

  /**
   * Define o valor de Propriet�rio.
   * @param proprietario Novo valor para Propriet�rio.
   */
  public void setProprietario(String proprietario) {
    this.proprietario = proprietario;
  }

  /**
   * Define o valor de Particular.
   * @param particular Novo valor para Particular.
   */
  public void setParticular(int particular) {
    this.particular = particular;
  }

  /**
   * Define o valor de �ltima Movimenta��o.
   * @param ultimaMovimentacao Novo valor para �ltima Movimenta��o.
   */
  public void setUltimaMovimentacao(Timestamp ultimaMovimentacao) {
    this.ultimaMovimentacao = ultimaMovimentacao;
  }

}
