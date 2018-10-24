package RegrabadoKardex;

import MigrarIngresosBod.MODELS.*;

public class MovimientoInv {

  private int id;
  private String descripcion;
  private String documento;
  private String status;

  public MovimientoInv() {
  }

  public MovimientoInv(int id, String descripcion, String documento, String status) {
    this.id = id;
    this.descripcion = descripcion;
    this.documento = documento;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDocumento() {
    return documento;
  }

  public void setDocumento(String documento) {
    this.documento = documento;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
