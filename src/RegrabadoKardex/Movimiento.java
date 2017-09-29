
package RegrabadoKardex;

import java.sql.Date;

public class Movimiento {

  public String getProductoscodigo() {
    return productoscodigo;
  }

  public void setProductoscodigo(String productoscodigo) {
    this.productoscodigo = productoscodigo;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
  
  private String productoscodigo ;
  private int total;
  
  
  
}
