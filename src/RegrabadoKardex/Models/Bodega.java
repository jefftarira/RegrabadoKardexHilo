package RegrabadoKardex.Models;

import java.math.BigDecimal;
import java.sql.Date;

public class Bodega {

  private String codigo;
  private BigDecimal cantidad;
  private BigDecimal costoUnitario;
  private BigDecimal costoTotal;
  private boolean cargaInicial;
  private int predeterminada;
  private Date fecha;

  public Bodega() {
  }

  public Bodega(String codigo, BigDecimal cantidad, BigDecimal costoUnitario, BigDecimal costoTotal, boolean cargaInicial) {
    this.setCodigo(codigo);
    this.setCantidad(cantidad);
    this.setCostoUnitario(costoUnitario);
    this.setCostoTotal(costoTotal);
    this.setCargaInicial(cargaInicial);
  }

  public Bodega(String codigo, BigDecimal cantidad, BigDecimal costoUnitario, BigDecimal costoTotal, Date fecha) {
    this.setCodigo(codigo);
    this.setCantidad(cantidad);
    this.setCostoUnitario(costoUnitario);
    this.setCostoTotal(costoTotal);
    this.setFecha(fecha);
  }

  public Bodega(String codigo, Date fecha) {
    this.setCodigo(codigo);
    this.setFecha(fecha);
  }

  public int getPredeterminada() {
    return predeterminada;
  }

  public void setPredeterminada(int predeterminada) {
    this.predeterminada = predeterminada;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public BigDecimal getCostoUnitario() {
    return costoUnitario;
  }

  public void setCostoUnitario(BigDecimal costoUnitario) {
    this.costoUnitario = costoUnitario;
  }

  public BigDecimal getCostoTotal() {
    return costoTotal;
  }

  public void setCostoTotal(BigDecimal costoTotal) {
    this.costoTotal = costoTotal;
  }

  public boolean isCargaInicial() {
    return cargaInicial;
  }

  public void setCargaInicial(boolean cargaInicial) {
    this.cargaInicial = cargaInicial;
  }
}
