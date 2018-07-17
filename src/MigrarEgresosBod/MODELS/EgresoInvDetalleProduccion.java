package MigrarEgresosBod.MODELS;

public class EgresoInvDetalleProduccion {

  private int id;
  private int idEgresoInvDetalle;
  private int idOpDetalle;
  private String status;

  public EgresoInvDetalleProduccion() {
  }

  public EgresoInvDetalleProduccion(int id, int idEgresoInvDetalle, int idOpDetalle,
          String status) {
    this.id = id;
    this.idEgresoInvDetalle = idEgresoInvDetalle;
    this.idOpDetalle = idOpDetalle;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdEgresoInvDetalle() {
    return idEgresoInvDetalle;
  }

  public void setIdEgresoInvDetalle(int idEgresoInvDetalle) {
    this.idEgresoInvDetalle = idEgresoInvDetalle;
  }

  public int getIdOpDetalle() {
    return idOpDetalle;
  }

  public void setIdOpDetalle(int idOpDetalle) {
    this.idOpDetalle = idOpDetalle;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
