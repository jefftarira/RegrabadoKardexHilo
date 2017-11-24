package pruebas;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RedondearBigDecimal {

  public static void main(String[] args) {
    BigDecimal num = new BigDecimal("13.445");
    num = num.divide(new BigDecimal("3"),30, RoundingMode.HALF_UP);
    int decimales = 2;

    System.out.println("NUMERO   " + num);
    System.out.println("UP" + "  " + num.setScale(decimales, RoundingMode.UP));
    System.out.println("DOWN" + "  " + num.setScale(decimales, RoundingMode.DOWN));
    System.out.println("CEILING" + "  " + num.setScale(decimales, RoundingMode.CEILING));
    System.out.println("FLOOR" + "  " + num.setScale(decimales, RoundingMode.FLOOR));
    System.out.println("HALF_UP" + "  " + num.setScale(decimales, RoundingMode.HALF_UP));
    System.out.println("HALF_DOWN" + "  " + num.setScale(decimales, RoundingMode.HALF_DOWN));
    System.out.println("HALF_EVEN" + "  " + num.setScale(decimales, RoundingMode.HALF_EVEN));
  }

}
