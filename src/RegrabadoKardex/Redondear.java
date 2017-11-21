package RegrabadoKardex;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Redondear {

  public BigDecimal getRound(BigDecimal num, int decimals) {
    BigDecimal bd = num.setScale(decimals, RoundingMode.HALF_EVEN);
    return bd;
  }

}
