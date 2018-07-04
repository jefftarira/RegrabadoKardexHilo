package MigrarProduccion.MODELS;

public class OpOt {

  private int id;
  private int idOrdenTrabajo;
  private int idOrdenProduccion;

  public OpOt() {
  }

  public OpOt(int id, int idOrdenTrabajo, int idOrdenProduccion) {
    this.id = id;
    this.idOrdenTrabajo = idOrdenTrabajo;
    this.idOrdenProduccion = idOrdenProduccion;
  }

  public OpOt(int idOrdenTrabajo, int idOrdenProduccion) {
    this.idOrdenTrabajo = idOrdenTrabajo;
    this.idOrdenProduccion = idOrdenProduccion;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdOrdenTrabajo() {
    return idOrdenTrabajo;
  }

  public void setIdOrdenTrabajo(int idOrdenTrabajo) {
    this.idOrdenTrabajo = idOrdenTrabajo;
  }

  public int getIdOrdenProduccion() {
    return idOrdenProduccion;
  }

  public void setIdOrdenProduccion(int idOrdenProduccion) {
    this.idOrdenProduccion = idOrdenProduccion;
  }

}
