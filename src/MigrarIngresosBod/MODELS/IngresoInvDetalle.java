package MigrarIngresosBod.MODELS;

import java.math.BigDecimal;

public class IngresoInvDetalle {

  private int id;
  private int idIngresoInv;
  private int idUsuario;
  private int idDivision;
  private String codigoProducto;
  private String nombreProducto;
  private String medida;
  private BigDecimal cantidad;
  private BigDecimal costoUnitario;
  private BigDecimal costoTotal;
  private String status;

  private String nombreDivision;
  private String nombreUsuario;

  public IngresoInvDetalle() {
  }

  public IngresoInvDetalle(int id, int idIngresoInv, int idUsuario, int idDivision,
          String codigoProducto, String nombreProducto, String medida,
          BigDecimal cantidad, BigDecimal costoUnitario, BigDecimal costoTotal,
          String status) {
    this.id = id;
    this.idIngresoInv = idIngresoInv;
    this.idUsuario = idUsuario;
    this.idDivision = idDivision;
    this.codigoProducto = codigoProducto;
    this.nombreProducto = nombreProducto;
    this.medida = medida;
    this.cantidad = cantidad;
    this.costoUnitario = costoUnitario;
    this.costoTotal = costoTotal;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdIngresoInv() {
    return idIngresoInv;
  }

  public void setIdIngresoInv(int idIngresoInv) {
    this.idIngresoInv = idIngresoInv;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public int getIdDivision() {
    return idDivision;
  }

  public void setIdDivision(int idDivision) {
    this.idDivision = idDivision;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getNombreDivision() {
    return nombreDivision;
  }

  public void setNombreDivision(String nombreDivision) {
    this.nombreDivision = nombreDivision;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

}
