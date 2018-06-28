package MigrarProduccion.MODELS;

import java.sql.Timestamp;

public class Usuarios {

  private int id;
  private int idTema;
  private String usuario;
  private String clave;
  private boolean cambiarClave;
  private String nombres;
  private String apellidos;
  private String mail;
  private String cargo;
  private Timestamp fechaRegistro;
  private int usuarioRegistro;
  private Timestamp fechaActualizacion;
  private int usuarioActualizacion;
  private String buscar;
  private String status;

  public Usuarios() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public boolean getCambiarClave() {
    return cambiarClave;
  }

  public void setCambiarClave(boolean cambiarClave) {
    this.cambiarClave = cambiarClave;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public Timestamp getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Timestamp fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public int getUsuarioRegistro() {
    return usuarioRegistro;
  }

  public void setUsuarioRegistro(int usuarioRegistro) {
    this.usuarioRegistro = usuarioRegistro;
  }

  public Timestamp getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Timestamp fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public int getUsuarioActualizacion() {
    return usuarioActualizacion;
  }

  public void setUsuarioActualizacion(int usuarioActualizacion) {
    this.usuarioActualizacion = usuarioActualizacion;
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

  public String getCargo() {
    return cargo;
  }

  public void setCargo(String cargo) {
    this.cargo = cargo;
  }

  public int getIdTema() {
    return idTema;
  }

  public void setIdTema(int idTema) {
    this.idTema = idTema;
  }

}
