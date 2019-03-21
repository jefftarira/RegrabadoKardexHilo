package RegrabadoKardex.Models;

import java.math.BigDecimal;

public class SaldoBodega {

  private BigDecimal saldo;
  private BigDecimal costoU;
  private BigDecimal costoT;

  public SaldoBodega() {
  }

  public SaldoBodega(BigDecimal saldo, BigDecimal costoU, BigDecimal costoT) {
    this.saldo = saldo;
    this.costoU = costoU;
    this.costoT = costoT;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }

  public BigDecimal getCostoU() {
    return costoU;
  }

  public void setCostoU(BigDecimal costoU) {
    this.costoU = costoU;
  }

  public BigDecimal getCostoT() {
    return costoT;
  }

  public void setCostoT(BigDecimal costoT) {
    this.costoT = costoT;
  }

}
