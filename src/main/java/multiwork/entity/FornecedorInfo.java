
package multiwork.entity;

import java.sql.*;

/**
 * Representa as informa��es contidas pela entidade Fornecedor.
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
   * Construtor padr�o.
   * @param codigo C�digo.
   * @param nome Nome.
   * @param endereco Endere�o.
   * @param municipio Munic�pio.
   * @param estado Estado.
   * @param cnpj CNPJ.
   * @param inscricaoEstadual Inscri��o Estadual.
   * @param inscricaoMuncipal Inscri��o Muncipal.
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
   * Retorna o valor de C�digo.
   * @return Retorna o valor de C�digo.
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
   * Retorna o valor de Endere�o.
   * @return Retorna o valor de Endere�o.
   */
  public String getEndereco() {
    return endereco;
  }

  /**
   * Retorna o valor de Munic�pio.
   * @return Retorna o valor de Munic�pio.
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
   * Retorna o valor de Inscri��o Estadual.
   * @return Retorna o valor de Inscri��o Estadual.
   */
  public String getInscricaoEstadual() {
    return inscricaoEstadual;
  }

  /**
   * Retorna o valor de Inscri��o Muncipal.
   * @return Retorna o valor de Inscri��o Muncipal.
   */
  public String getInscricaoMuncipal() {
    return inscricaoMuncipal;
  }

  /**
   * Define o valor de C�digo.
   * @param codigo Novo valor para C�digo.
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
   * Define o valor de Endere�o.
   * @param endereco Novo valor para Endere�o.
   */
  public void setEndereco(String endereco) {
    this.endereco = endereco;
  }

  /**
   * Define o valor de Munic�pio.
   * @param municipio Novo valor para Munic�pio.
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
   * Define o valor de Inscri��o Estadual.
   * @param inscricaoEstadual Novo valor para Inscri��o Estadual.
   */
  public void setInscricaoEstadual(String inscricaoEstadual) {
    this.inscricaoEstadual = inscricaoEstadual;
  }

  /**
   * Define o valor de Inscri��o Muncipal.
   * @param inscricaoMuncipal Novo valor para Inscri��o Muncipal.
   */
  public void setInscricaoMuncipal(String inscricaoMuncipal) {
    this.inscricaoMuncipal = inscricaoMuncipal;
  }

}
