package RegrabadoKardex;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Redondear {

  public static final BigDecimal getRound(BigDecimal num, int decimals) {
    return num.setScale(decimals, RoundingMode.HALF_EVEN);    
  }

}
