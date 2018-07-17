package MigrarEgresosBod.MODELS;

public class Vendedor {

  private int id;
  private String idVendedorMarketsoft;

  public Vendedor(int id, String idVendedorMarketsoft) {
    this.id = id;
    this.idVendedorMarketsoft = idVendedorMarketsoft;
  }

  public Vendedor() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIdVendedorMarketsoft() {
    return idVendedorMarketsoft;
  }

  public void setIdVendedorMarketsoft(String idVendedorMarketsoft) {
    this.idVendedorMarketsoft = idVendedorMarketsoft;
  }

}
