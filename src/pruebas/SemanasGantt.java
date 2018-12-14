package pruebas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SemanasGantt {

  public static void main(String args[]) throws ParseException {

    Calendar currentCalendar = Calendar.getInstance();
    currentCalendar.set(Calendar.YEAR, 2018);
    currentCalendar.set(Calendar.MONTH, 11);
    currentCalendar.set(Calendar.DAY_OF_MONTH, 23);

    Calendar firstCalendar = currentCalendar.getInstance();

    currentCalendar.setTime(currentCalendar.getTime());
    firstCalendar.setTime(currentCalendar.getTime());

    int numberWeekOfYear = currentCalendar.get(Calendar.WEEK_OF_YEAR);
    int numberDayofWeek = currentCalendar.get(Calendar.DAY_OF_WEEK);
    int firstDayofWeek = currentCalendar.getFirstDayOfWeek();

    SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");

    if (numberDayofWeek == 1) {
      firstCalendar.add(Calendar.DAY_OF_YEAR, -6);
    } else {
      firstCalendar.add(Calendar.DAY_OF_YEAR, (numberDayofWeek - firstDayofWeek) * -1);
    }

    Calendar lastCalendar = firstCalendar.getInstance();
    lastCalendar.setTime(firstCalendar.getTime());
    lastCalendar.add(Calendar.DAY_OF_YEAR, 13);

    java.sql.Date fechaActual =  new java.sql.Date(currentCalendar.getTimeInMillis());
    java.sql.Date fechaPrimera =  new java.sql.Date(firstCalendar.getTimeInMillis());
    java.sql.Date fechaUltima =  new java.sql.Date(lastCalendar.getTimeInMillis());

    System.out.println("Fecha actual: " + formatDate.format(fechaActual));
    System.out.println("Fecha Primera: " + formatDate.format(fechaPrimera));
    System.out.println("Fecha ultima: " + formatDate.format(fechaUltima));

    System.out.println("semana numero: " + numberWeekOfYear);
    System.out.println("Dia de la semana: " + numberDayofWeek);
    System.out.println("Primer dia de la semana: " + firstDayofWeek);

  }

}
