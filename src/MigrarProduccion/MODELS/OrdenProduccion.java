package MigrarProduccion.MODELS;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class OrdenProduccion {

  private int id;
  private int idEstado;
  private String nombreEstado;
  private int idUsuario;
  private String nombreUsuario;
  private int idOrdenTrabajo;
  private Timestamp fechaEmision;
  private Date fechaInicio;
  private Date fechaFin;
  private String codigoProducto;
  private String nombreProducto;
  private BigDecimal cantidad;
  private String medida;
  private String color;
  private String observaciones;
  private BigDecimal horasRotomoldeo;
  private BigDecimal horasSoldadura;
  private BigDecimal horasAcabado;
  private BigDecimal horasTaller;
  private BigDecimal horasPulverizado;
  private BigDecimal personasRotomoldeo;
  private BigDecimal personasSoldadura;
  private BigDecimal personasAcabado;
  private BigDecimal personasTaller;
  private BigDecimal personasPulverizado;
  private String buscar;
  private String status;

  public OrdenProduccion() {
  }

  public OrdenProduccion(int id, int idEstado, String nombreEstado, int idUsuario,
          String nombreUsuario, int idOrdenTrabajo, Timestamp fechaEmision,
          Date fechaInicio, Date fechaFin, String codigoProducto, String nombreProducto,
          BigDecimal cantidad, String medida, String color, String observaciones,
          BigDecimal horasRotomoldeo, BigDecimal horasSoldadura, BigDecimal horasAcabado,
          BigDecimal horasTaller, BigDecimal horasPulverizado, BigDecimal personasRotomoldeo,
          BigDecimal personasSoldadura, BigDecimal personasAcabado, BigDecimal personasTaller,
          BigDecimal personasPulverizado, String buscar, String status) {
    this.id = id;
    this.idEstado = idEstado;
    this.nombreEstado = nombreEstado;
    this.idUsuario = idUsuario;
    this.nombreUsuario = nombreUsuario;
    this.idOrdenTrabajo = idOrdenTrabajo;
    this.fechaEmision = fechaEmision;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.codigoProducto = codigoProducto;
    this.nombreProducto = nombreProducto;
    this.cantidad = cantidad;
    this.medida = medida;
    this.color = color;
    this.observaciones = observaciones;
    this.horasRotomoldeo = horasRotomoldeo;
    this.horasSoldadura = horasSoldadura;
    this.horasAcabado = horasAcabado;
    this.horasTaller = horasTaller;
    this.horasPulverizado = horasPulverizado;
    this.personasRotomoldeo = personasRotomoldeo;
    this.personasSoldadura = personasSoldadura;
    this.personasAcabado = personasAcabado;
    this.personasTaller = personasTaller;
    this.personasPulverizado = personasPulverizado;
    this.buscar = buscar;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public int getIdOrdenTrabajo() {
    return idOrdenTrabajo;
  }

  public void setIdOrdenTrabajo(int idOrdenTrabajo) {
    this.idOrdenTrabajo = idOrdenTrabajo;
  }

  public Timestamp getFechaEmision() {
    return fechaEmision;
  }

  public void setFechaEmision(Timestamp fechaEmision) {
    this.fechaEmision = fechaEmision;
  }

  public Date getFechaInicio() {
    return fechaInicio;
  }

  public void setFechaInicio(Date fechaInicio) {
    this.fechaInicio = fechaInicio;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
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

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

  public String getMedida() {
    return medida;
  }

  public void setMedida(String medida) {
    this.medida = medida;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public BigDecimal getHorasRotomoldeo() {
    return horasRotomoldeo;
  }

  public void setHorasRotomoldeo(BigDecimal horasRotomoldeo) {
    this.horasRotomoldeo = horasRotomoldeo;
  }

  public BigDecimal getHorasSoldadura() {
    return horasSoldadura;
  }

  public void setHorasSoldadura(BigDecimal horasSoldadura) {
    this.horasSoldadura = horasSoldadura;
  }

  public BigDecimal getHorasAcabado() {
    return horasAcabado;
  }

  public void setHorasAcabado(BigDecimal horasAcabado) {
    this.horasAcabado = horasAcabado;
  }

  public BigDecimal getHorasTaller() {
    return horasTaller;
  }

  public void setHorasTaller(BigDecimal horasTaller) {
    this.horasTaller = horasTaller;
  }

  public BigDecimal getHorasPulverizado() {
    return horasPulverizado;
  }

  public void setHorasPulverizado(BigDecimal horasPulverizado) {
    this.horasPulverizado = horasPulverizado;
  }

  public BigDecimal getPersonasRotomoldeo() {
    return personasRotomoldeo;
  }

  public void setPersonasRotomoldeo(BigDecimal personasRotomoldeo) {
    this.personasRotomoldeo = personasRotomoldeo;
  }

  public BigDecimal getPersonasSoldadura() {
    return personasSoldadura;
  }

  public void setPersonasSoldadura(BigDecimal personasSoldadura) {
    this.personasSoldadura = personasSoldadura;
  }

  public BigDecimal getPersonasAcabado() {
    return personasAcabado;
  }

  public void setPersonasAcabado(BigDecimal personasAcabado) {
    this.personasAcabado = personasAcabado;
  }

  public BigDecimal getPersonasTaller() {
    return personasTaller;
  }

  public void setPersonasTaller(BigDecimal personasTaller) {
    this.personasTaller = personasTaller;
  }

  public BigDecimal getPersonasPulverizado() {
    return personasPulverizado;
  }

  public void setPersonasPulverizado(BigDecimal personasPulverizado) {
    this.personasPulverizado = personasPulverizado;
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
