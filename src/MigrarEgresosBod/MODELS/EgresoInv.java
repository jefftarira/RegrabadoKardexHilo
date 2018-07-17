package MigrarEgresosBod.MODELS;

import java.sql.Date;
import java.sql.Timestamp;

public class EgresoInv {

  private int id;
  private int idUsuario;
  private String nombreUsuario;
  private int idDivision;
  private String nombreDivision;
  private int idEstado;
  private String nombreEstado;
  private int idBodegaEgr;
  private String nombreBodegaEgr;
  private int idBodegaIng;
  private String nombreBodegaIng;
  private int idMovimiento;
  private String nombreMovimiento;
  private int idProduccion;
  private Timestamp fechaEmision;
  private Date fechaKardex;
  private String observaciones;
  private String buscar;
  private String status;

  private String responsable;

  public EgresoInv() {
  }

  public EgresoInv(int id, int idUsuario, String nombreUsuario, int idDivision,
          String nombreDivision, int idEstado, String nombreEstado, int idBodegaEgr,
          String nombreBodegaEgr, int idBodegaIng, String nombreBodegaIng,
          int idMovimiento, String nombreMovimiento, Timestamp fechaEmision,
          Date fechaKardex, String observaciones, String buscar, String status) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.nombreUsuario = nombreUsuario;
    this.idDivision = idDivision;
    this.nombreDivision = nombreDivision;
    this.idEstado = idEstado;
    this.nombreEstado = nombreEstado;
    this.idBodegaEgr = idBodegaEgr;
    this.nombreBodegaEgr = nombreBodegaEgr;
    this.idBodegaIng = idBodegaIng;
    this.nombreBodegaIng = nombreBodegaIng;
    this.idMovimiento = idMovimiento;
    this.nombreMovimiento = nombreMovimiento;
    this.fechaEmision = fechaEmision;
    this.fechaKardex = fechaKardex;
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

  public int getIdBodegaEgr() {
    return idBodegaEgr;
  }

  public void setIdBodegaEgr(int idBodegaEgr) {
    this.idBodegaEgr = idBodegaEgr;
  }

  public String getNombreBodegaEgr() {
    return nombreBodegaEgr;
  }

  public void setNombreBodegaEgr(String nombreBodegaEgr) {
    this.nombreBodegaEgr = nombreBodegaEgr;
  }

  public int getIdBodegaIng() {
    return idBodegaIng;
  }

  public void setIdBodegaIng(int idBodegaIng) {
    this.idBodegaIng = idBodegaIng;
  }

  public String getNombreBodegaIng() {
    return nombreBodegaIng;
  }

  public void setNombreBodegaIng(String nombreBodegaIng) {
    this.nombreBodegaIng = nombreBodegaIng;
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

  public int getIdProduccion() {
    return idProduccion;
  }

  public void setIdProduccion(int idProduccion) {
    this.idProduccion = idProduccion;
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

  public String getResponsable() {
    return responsable;
  }

  public void setResponsable(String responsable) {
    this.responsable = responsable;
  }

}
