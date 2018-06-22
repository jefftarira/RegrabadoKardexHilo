package pruebas;

import java.time.Instant;
import java.util.Date;

public class PruebasDateInstant {

  public static void main(String args[]) {
    
    Date dt = Date.from(Instant.now());
    System.out.println(dt);
  }
}
