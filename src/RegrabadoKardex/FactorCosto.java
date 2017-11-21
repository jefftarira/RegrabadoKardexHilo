package RegrabadoKardex;

import java.math.BigDecimal;

public class FactorCosto {

  private int ordenNumero;
  private String codigoProducto;
  private String categoria;
  private BigDecimal factorMod;
  private BigDecimal factorCif;
  private BigDecimal factorGas;
  private BigDecimal costoMateriales;
  private BigDecimal costoFactores;
  private int personasRot;
  private BigDecimal horasRot;
  private int personasSol;
  private BigDecimal horasSol;
  private int personasAca;
  private BigDecimal horasAca;
  private int personasTal;
  private BigDecimal horasTal;

  public int getOrdenNumero() {
    return ordenNumero;
  }

  public void setOrdenNumero(int ordenNumero) {
    this.ordenNumero = ordenNumero;
  }

  public int getPersonasRot() {
    return personasRot;
  }

  public void setPersonasRot(int personasRot) {
    this.personasRot = personasRot;
  }

  public BigDecimal getHorasRot() {
    return horasRot;
  }

  public void setHorasRot(BigDecimal horasRot) {
    this.horasRot = horasRot;
  }

  public int getPersonasSol() {
    return personasSol;
  }

  public void setPersonasSol(int personasSol) {
    this.personasSol = personasSol;
  }

  public BigDecimal getHorasSol() {
    return horasSol;
  }

  public void setHorasSol(BigDecimal horasSol) {
    this.horasSol = horasSol;
  }

  public int getPersonasAca() {
    return personasAca;
  }

  public void setPersonasAca(int personasAca) {
    this.personasAca = personasAca;
  }

  public BigDecimal getHorasAca() {
    return horasAca;
  }

  public void setHorasAca(BigDecimal horasAca) {
    this.horasAca = horasAca;
  }

  public int getPersonasTal() {
    return personasTal;
  }

  public void setPersonasTal(int personasTal) {
    this.personasTal = personasTal;
  }

  public BigDecimal getHorasTal() {
    return horasTal;
  }

  public void setHorasTal(BigDecimal horasTal) {
    this.horasTal = horasTal;
  }

  public String getCodigoProducto() {
    return codigoProducto;
  }

  public void setCodigoProducto(String codigoProducto) {
    this.codigoProducto = codigoProducto;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public BigDecimal getFactorMod() {
    return factorMod;
  }

  public void setFactorMod(BigDecimal factorMod) {
    this.factorMod = factorMod;
  }

  public BigDecimal getFactorCif() {
    return factorCif;
  }

  public void setFactorCif(BigDecimal factorCif) {
    this.factorCif = factorCif;
  }

  public BigDecimal getFactorGas() {
    return factorGas;
  }

  public void setFactorGas(BigDecimal factorGas) {
    this.factorGas = factorGas;
  }

  public BigDecimal getCostoMateriales() {
    return costoMateriales;
  }

  public void setCostoMateriales(BigDecimal costoMateriales) {
    this.costoMateriales = costoMateriales;
  }

  public BigDecimal getCostoFactores() {
    return costoFactores;
  }

  public void setCostoFactores(BigDecimal costoFactores) {
    this.costoFactores = costoFactores;
  }

}
