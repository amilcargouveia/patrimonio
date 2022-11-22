
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informações contidas pela entidade Fornecedor.
 */
public class FornecedorInfo {

  private int codigo;
  private String nome;
  private String endereco;
  private String municipio;
  private String estado;
  private String cnpj;
  private String inscricaoEstadual;
  private String inscricaoMuncipal;

  /**
   * Construtor padrão.
   * @param codigo Código.
   * @param nome Nome.
   * @param endereco Endereço.
   * @param municipio Município.
   * @param estado Estado.
   * @param cnpj CNPJ.
   * @param inscricaoEstadual Inscrição Estadual.
   * @param inscricaoMuncipal Inscrição Muncipal.
   */
  public FornecedorInfo(
           int codigo,
           String nome,
           String endereco,
           String municipio,
           String estado,
           String cnpj,
           String inscricaoEstadual,
           String inscricaoMuncipal
         ) {
    // guarda nossos dados
    this.codigo = codigo;
    this.nome = nome;
    this.endereco = endereco;
    this.municipio = municipio;
    this.estado = estado;
    this.cnpj = cnpj;
    this.inscricaoEstadual = inscricaoEstadual;
    this.inscricaoMuncipal = inscricaoMuncipal;
   }

  /**
   * Retorna o valor de Código.
   * @return Retorna o valor de Código.
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * Retorna o valor de Nome.
   * @return Retorna o valor de Nome.
   */
  public String getNome() {
    return nome;
  }

  /**
   * Retorna o valor de Endereço.
   * @return Retorna o valor de Endereço.
   */
  public String getEndereco() {
    return endereco;
  }

  /**
   * Retorna o valor de Município.
   * @return Retorna o valor de Município.
   */
  public String getMunicipio() {
    return municipio;
  }

  /**
   * Retorna o valor de Estado.
   * @return Retorna o valor de Estado.
   */
  public String getEstado() {
    return estado;
  }

  /**
   * Retorna o valor de CNPJ.
   * @return Retorna o valor de CNPJ.
   */
  public String getCnpj() {
    return cnpj;
  }

  /**
   * Retorna o valor de Inscrição Estadual.
   * @return Retorna o valor de Inscrição Estadual.
   */
  public String getInscricaoEstadual() {
    return inscricaoEstadual;
  }

  /**
   * Retorna o valor de Inscrição Muncipal.
   * @return Retorna o valor de Inscrição Muncipal.
   */
  public String getInscricaoMuncipal() {
    return inscricaoMuncipal;
  }

  /**
   * Define o valor de Código.
   * @param codigo Novo valor para Código.
   */
  public void setCodigo(int codigo) {
    this.codigo = codigo;
  }

  /**
   * Define o valor de Nome.
   * @param nome Novo valor para Nome.
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Define o valor de Endereço.
   * @param endereco Novo valor para Endereço.
   */
  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  /**
   * Define o valor de Município.
   * @param municipio Novo valor para Município.
   */
  public void setMunicipio(String municipio) {
    this.municipio = municipio;
  }

  /**
   * Define o valor de Estado.
   * @param estado Novo valor para Estado.
   */
  public void setEstado(String estado) {
    this.estado = estado;
  }

  /**
   * Define o valor de CNPJ.
   * @param cnpj Novo valor para CNPJ.
   */
  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  /**
   * Define o valor de Inscrição Estadual.
   * @param inscricaoEstadual Novo valor para Inscrição Estadual.
   */
  public void setInscricaoEstadual(String inscricaoEstadual) {
    this.inscricaoEstadual = inscricaoEstadual;
  }

  /**
   * Define o valor de Inscrição Muncipal.
   * @param inscricaoMuncipal Novo valor para Inscrição Muncipal.
   */
  public void setInscricaoMuncipal(String inscricaoMuncipal) {
    this.inscricaoMuncipal = inscricaoMuncipal;
  }

}
