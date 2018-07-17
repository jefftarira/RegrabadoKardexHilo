package MigrarEgresosBod.MODELS;

public class EgresoInvVendedor {

  private int id;
  private int idEgresoInv;
  private int idUsuario;
  private int idDivision;
  private int idVendedor;
  private String status;

  public EgresoInvVendedor() {
  }

  public EgresoInvVendedor(int id, int idEgresoInv, int idUsuario, int idDivision,
          int idVendedor, String status) {
    this.id = id;
    this.idEgresoInv = idEgresoInv;
    this.idUsuario = idUsuario;
    this.idDivision = idDivision;
    this.idVendedor = idVendedor;
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

  public int getIdVendedor() {
    return idVendedor;
  }

  public void setIdVendedor(int idVendedor) {
    this.idVendedor = idVendedor;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
