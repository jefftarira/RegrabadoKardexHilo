package MigrarIngresosBod.MODELS;

import java.sql.Date;
import java.sql.Timestamp;

public class IngresoInv {

  private int id;
  private int idUsuario;
  private String nombreUsuario;
  private int idDivision;
  private String nombreDivision;
  private int idProduccion;
  private int idEstado;
  private String nombreEstado;
  private int idBodega;
  private String nombreBodega;
  private int idMovimiento;
  private String nombreMovimiento;
  private Timestamp fechaEmision;
  private Date fechaKardex;
  private String idCliente;
  private String nombreCliente;
  private String idProveedor;
  private String nombreProveedor;
  private String observaciones;
  private String buscar;
  private String status;

  public IngresoInv() {
  }

  public IngresoInv(int id, int idUsuario, String nombreUsuario, int idDivision,
          String nombreDivision, int idProduccion, int idEstado, String nombreEstado,
          int idBodega, String nombreBodega, int idMovimiento, String nombreMovimiento,
          Timestamp fechaEmision, Date fechaKardex, String idCliente,
          String nombreCliente, String idProveedor, String nombreProveedor,
          String observaciones, String buscar, String status) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.nombreUsuario = nombreUsuario;
    this.idDivision = idDivision;
    this.nombreDivision = nombreDivision;
    this.idProduccion = idProduccion;
    this.idEstado = idEstado;
    this.nombreEstado = nombreEstado;
    this.idBodega = idBodega;
    this.nombreBodega = nombreBodega;
    this.idMovimiento = idMovimiento;
    this.nombreMovimiento = nombreMovimiento;
    this.fechaEmision = fechaEmision;
    this.fechaKardex = fechaKardex;
    this.idCliente = idCliente;
    this.nombreCliente = nombreCliente;
    this.idProveedor = idProveedor;
    this.nombreProveedor = nombreProveedor;
    this.observaciones = observaciones;
    this.buscar = buscar;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public int getIdDivision() {
    return idDivision;
  }

  public void setIdDivision(int idDivision) {
    this.idDivision = idDivision;
  }

  public String getNombreDivision() {
    return nombreDivision;
  }

  public void setNombreDivision(String nombreDivision) {
    this.nombreDivision = nombreDivision;
  }

  public int getIdProduccion() {
    return idProduccion;
  }

  public void setIdProduccion(int idProduccion) {
    this.idProduccion = idProduccion;
  }

  public int getIdEstado() {
    return idEstado;
  }

  public void setIdEstado(int idEstado) {
    this.idEstado = idEstado;
  }

  public String getNombreEstado() {
    return nombreEstado;
  }

  public void setNombreEstado(String nombreEstado) {
    this.nombreEstado = nombreEstado;
  }

  public int getIdBodega() {
    return idBodega;
  }

  public void setIdBodega(int idBodega) {
    this.idBodega = idBodega;
  }

  public String getNombreBodega() {
    return nombreBodega;
  }

  public void setNombreBodega(String nombreBodega) {
    this.nombreBodega = nombreBodega;
  }

  public int getIdMovimiento() {
    return idMovimiento;
  }

  public void setIdMovimiento(int idMovimiento) {
    this.idMovimiento = idMovimiento;
  }

  public String getNombreMovimiento() {
    return nombreMovimiento;
  }

  public void setNombreMovimiento(String nombreMovimiento) {
    this.nombreMovimiento = nombreMovimiento;
  }

  public Timestamp getFechaEmision() {
    return fechaEmision;
  }

  public void setFechaEmision(Timestamp fechaEmision) {
    this.fechaEmision = fechaEmision;
  }

  public Date getFechaKardex() {
    return fechaKardex;
  }

  public void setFechaKardex(Date fechaKardex) {
    this.fechaKardex = fechaKardex;
  }

  public String getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(String idCliente) {
    this.idCliente = idCliente;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public String getIdProveedor() {
    return idProveedor;
  }

  public void setIdProveedor(String idProveedor) {
    this.idProveedor = idProveedor;
  }

  public String getNombreProveedor() {
    return nombreProveedor;
  }

  public void setNombreProveedor(String nombreProveedor) {
    this.nombreProveedor = nombreProveedor;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public String getBuscar() {
    return buscar;
  }

  public void setBuscar(String buscar) {
    this.buscar = buscar;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
