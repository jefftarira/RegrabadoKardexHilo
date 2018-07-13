package MigrarIngresosBod.MODELS;

import java.sql.Timestamp;

public class IngresoInvHistorial {

  private int id;
  private int idIngresoInv;
  private int idUsuario;
  private int idDivision;
  private int idUsuarioHis;
  private String nombreUsuarioHis;
  private int idEstado;
  private String nombreEstado;
  private String descripcion;
  private Timestamp fecha;
  private String status;

  public IngresoInvHistorial() {
  }

  public IngresoInvHistorial(int id, int idIngresoInv, int idUsuario, int idDivision,
          int idUsuarioHis, String nombreUsuarioHis, int idEstado, String nombreEstado,
          String descripcion, Timestamp fecha, String status) {
    this.id = id;
    this.idIngresoInv = idIngresoInv;
    this.idUsuario = idUsuario;
    this.idDivision = idDivision;
    this.idUsuarioHis = idUsuarioHis;
    this.nombreUsuarioHis = nombreUsuarioHis;
    this.idEstado = idEstado;
    this.nombreEstado = nombreEstado;
    this.descripcion = descripcion;
    this.fecha = fecha;
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

  public int getIdUsuarioHis() {
    return idUsuarioHis;
  }

  public void setIdUsuarioHis(int idUsuarioHis) {
    this.idUsuarioHis = idUsuarioHis;
  }

  public String getNombreUsuarioHis() {
    return nombreUsuarioHis;
  }

  public void setNombreUsuarioHis(String nombreUsuarioHis) {
    this.nombreUsuarioHis = nombreUsuarioHis;
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

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Timestamp getFecha() {
    return fecha;
  }

  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
