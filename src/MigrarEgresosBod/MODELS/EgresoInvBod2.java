package MigrarEgresosBod.MODELS;

public class EgresoInvBod2 {

  private int id;
  private int idEgresoInv;
  private int idUsuario;
  private int idDivision;
  private int idBodegaIng;
  private String status;

  public EgresoInvBod2() {
  }

  public EgresoInvBod2(int id, int idEgresoInv, int idUsuario, int idDivision,
          int idBodegaIng, String status) {
    this.id = id;
    this.idEgresoInv = idEgresoInv;
    this.idUsuario = idUsuario;
    this.idDivision = idDivision;
    this.idBodegaIng = idBodegaIng;
    this.status = status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdEgresoInv() {
    return idEgresoInv;
  }

  public void setIdEgresoInv(int idEgresoInv) {
    this.idEgresoInv = idEgresoInv;
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

  public int getIdBodegaIng() {
    return idBodegaIng;
  }

  public void setIdBodegaIng(int idBodegaIng) {
    this.idBodegaIng = idBodegaIng;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
