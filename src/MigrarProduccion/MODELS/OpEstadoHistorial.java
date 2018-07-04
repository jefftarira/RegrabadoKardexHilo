package MigrarProduccion.MODELS;

import java.sql.Timestamp;

public class OpEstadoHistorial {

  private int id;
  private int idUsuario;
  private String nombreUsuario;
  private int idOrdenProduccion;
  private int idEstado;
  private String nombreEstado;
  private Timestamp fecha;
  private String descripcion;
  private String status;

  public OpEstadoHistorial() {
  }

  public OpEstadoHistorial(
          int id, int idUsuario, String nombreUsuario,
          int idOrdenProduccion, int idEstado, String nombreEstado,
          Timestamp fecha, String descripcion, String status
  ) {
    this.id = id;
    this.idUsuario = idUsuario;
    this.nombreUsuario = nombreUsuario;
    this.idOrdenProduccion = idOrdenProduccion;
    this.idEstado = idEstado;
    this.nombreEstado = nombreEstado;
    this.fecha = fecha;
    this.descripcion = descripcion;
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

  public int getIdOrdenProduccion() {
    return idOrdenProduccion;
  }

  public void setIdOrdenProduccion(int idOrdenProduccion) {
    this.idOrdenProduccion = idOrdenProduccion;
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

  public Timestamp getFecha() {
    return fecha;
  }

  public void setFecha(Timestamp fecha) {
    this.fecha = fecha;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
