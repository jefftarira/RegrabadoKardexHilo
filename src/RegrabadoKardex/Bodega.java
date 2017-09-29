package RegrabadoKardex;

import java.sql.Date;

public class Bodega {

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

  public Double getCantidad() {
    return cantidad;
  }

  public void setCantidad(Double cantidad) {
    this.cantidad = cantidad;
  }

  public Double getCostoUnitario() {
    return costoUnitario;
  }

  public void setCostoUnitario(Double costoUnitario) {
    this.costoUnitario = costoUnitario;
  }

  public Double getCostoTotal() {
    return costoTotal;
  }

  public void setCostoTotal(Double costoTotal) {
    this.costoTotal = costoTotal;
  }

  public boolean isCargaInicial() {
    return cargaInicial;
  }

  public void setCargaInicial(boolean cargaInicial) {
    this.cargaInicial = cargaInicial;
  }

  public Bodega(String codigo, Double cantidad, Double costoUnitario, Double costoTotal, boolean cargaInicial) {
    this.setCodigo(codigo);
    this.setCantidad(cantidad);
    this.setCostoUnitario(costoUnitario);
    this.setCostoTotal(costoTotal);
    this.setCargaInicial(cargaInicial);
  }

  public Bodega(String codigo, Double cantidad, Double costoUnitario, Double costoTotal, Date fecha) {
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
  
  public Bodega(){
    
  }

  private String codigo;
  private Double cantidad;
  private Double costoUnitario;
  private Double costoTotal;
  private boolean cargaInicial;
  private int predeterminada;
  private Date fecha;

}
