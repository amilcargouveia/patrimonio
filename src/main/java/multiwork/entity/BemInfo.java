
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informações contidas pela entidade Bem.
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
   * Construtor padrão.
   * @param codigo Código.
   * @param codigoDeBarras Código de Barras.
   * @param tombo Tombo.
   * @param numeroSerie Número Série.
   * @param codigoGrupo Código Grupo.
   * @param descricao Descrição.
   * @param codigoSetor Código Setor.
   * @param valorDaCompra Valor da Compra.
   * @param notaFiscal Nota Fiscal.
   * @param codigoFornecedor Código Fornecedor.
   * @param dataInclusao Data Inclusão.
   * @param dataGarantia Data Gerantia.
   * @param proprietario Proprietário.
   * @param particular Particular.
   * @param ultimaMovimentacao Última Movimentação.
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
   * Retorna o valor de Código.
   * @return Retorna o valor de Código.
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Retorna o valor de Código de Barras.
   * @return Retorna o valor de Código de Barras.
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
   * Retorna o valor de Número Série.
   * @return Retorna o valor de Número Série.
   */
  public String getNumeroSerie() {
    return numeroSerie;
  }

  /**
   * Retorna o valor de Código Grupo.
   * @return Retorna o valor de Código Grupo.
   */
  public int getCodigoGrupo() {
    return codigoGrupo;
  }

  /**
   * Retorna o valor de Descrição.
   * @return Retorna o valor de Descrição.
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * Retorna o valor de Código Setor.
   * @return Retorna o valor de Código Setor.
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
   * Retorna o valor de Código Fornecedor.
   * @return Retorna o valor de Código Fornecedor.
   */
  public int getCodigoFornecedor() {
    return codigoFornecedor;
  }

  /**
   * Retorna o valor de Data Inclusão.
   * @return Retorna o valor de Data Inclusão.
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
   * Retorna o valor de Proprietário.
   * @return Retorna o valor de Proprietário.
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
   * Retorna o valor de Última Movimentação.
   * @return Retorna o valor de Última Movimentação.
   */
  public Timestamp getUltimaMovimentacao() {
    return ultimaMovimentacao;
  }

  /**
   * Define o valor de Código.
   * @param codigo Novo valor para Código.
   */
  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  /**
   * Define o valor de Código de Barras.
   * @param codigoDeBarras Novo valor para Código de Barras.
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
   * Define o valor de Código Grupo.
   * @param codigoGrupo Novo valor para Código Grupo.
   */
  public void setCodigoGrupo(int codigoGrupo) {
    this.codigoGrupo = codigoGrupo;
  }

  /**
   * Define o valor de Descrição.
   * @param descricao Novo valor para Descrição.
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * Define o valor de Código Setor.
   * @param codigoSetor Novo valor para Código Setor.
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
   * Define o valor de Código Fornecedor.
   * @param codigoFornecedor Novo valor para Código Fornecedor.
   */
  public void setCodigoFornecedor(int codigoFornecedor) {
    this.codigoFornecedor = codigoFornecedor;
  }

  /**
   * Define o valor de Data Inclusão.
   * @param dataInclusao Novo valor para Data Inclusão.
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
   * Define o valor de Proprietário.
   * @param proprietario Novo valor para Proprietário.
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
   * Define o valor de Última Movimentação.
   * @param ultimaMovimentacao Novo valor para Última Movimentação.
   */
  public void setUltimaMovimentacao(Timestamp ultimaMovimentacao) {
    this.ultimaMovimentacao = ultimaMovimentacao;
  }

}
