package MigrarEgresosBod.MODELS;

public class Division {

  private int id;
  private String idMarketsoft;
  private String descripcion;
  private String status;

  public Division() {
  }

  public Division(int id, String idMarketsoft, String descripcion, String status) {
    this.id = id;
    this.idMarketsoft = idMarketsoft;
    this.descripcion = descripcion;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIdMarketsoft() {
    return idMarketsoft;
  }

  public void setIdMarketsoft(String idMarketsoft) {
    this.idMarketsoft = idMarketsoft;
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
