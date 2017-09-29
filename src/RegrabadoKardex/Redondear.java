
package RegrabadoKardex;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Redondear {
  
  
  public Double getRound(Double num, int decimals) {
    BigDecimal bd = new BigDecimal(num).setScale(decimals, RoundingMode.HALF_EVEN);
    Double d = bd.doubleValue();
    
    return d;
  }
  
}
