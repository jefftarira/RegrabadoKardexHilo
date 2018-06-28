package MigrarProduccion.MODELS;

import java.math.BigDecimal;

public class DetalleProduccion {

  private int id;
  private int idOrdenProduccion;
  private int idArea;
  private String nombreArea;
  private boolean llevaFactura;
  private String codigoProducto;
  private String nombreProducto;
  private String medida;
  private BigDecimal cantidad;
  private int egreso;
  private String userEB;
  private String divisionEB;
  private String status;

  public DetalleProduccion() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdOrdenProduccion() {
    return idOrdenProduccion;
  }

  public void setIdOrdenProduccion(int idOrdenProduccion) {
    this.idOrdenProduccion = idOrdenProduccion;
  }

  public int getIdArea() {
    return idArea;
  }

  public void setIdArea(int idArea) {
    this.idArea = idArea;
  }

  public String getNombreArea() {
    return nombreArea;
  }

  public void setNombreArea(String nombreArea) {
    this.nombreArea = nombreArea;
  }

  public boolean isLlevaFactura() {
    return llevaFactura;
  }

  public void setLlevaFactura(boolean llevaFactura) {
    this.llevaFactura = llevaFactura;
  }

  public String getCodigoProducto() {
    return codigoProducto;
  }

  public void setCodigoProducto(String codigoProducto) {
    this.codigoProducto = codigoProducto;
  }

  public String getNombreProducto() {
    return nombreProducto;
  }

  public void setNombreProducto(String nombreProducto) {
    this.nombreProducto = nombreProducto;
  }

  public String getMedida() {
    return medida;
  }

  public void setMedida(String medida) {
    this.medida = medida;
  }

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public int getEgreso() {
    return egreso;
  }

  public void setEgreso(int egreso) {
    this.egreso = egreso;
  }

  public String getUserEB() {
    return userEB;
  }

  public void setUserEB(String userEB) {
    this.userEB = userEB;
  }

  public String getDivisionEB() {
    return divisionEB;
  }

  public void setDivisionEB(String divisionEB) {
    this.divisionEB = divisionEB;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
