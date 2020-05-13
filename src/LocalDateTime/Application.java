package LocalDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public final class Application {

  public static void main(String[] args) {
    Supplier handler = () -> {

      final LocalDateTime time = LocalDateTime.now();
      final String dateFormatted = time.format(DateTimeFormatter.ISO_TIME);
      return "Tiempo Actual es " + dateFormatted;
    };

    System.out.println(handler.get());
  }

}
