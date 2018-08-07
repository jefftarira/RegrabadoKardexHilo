package RegrabadoKardex;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorRegrabado implements Callable<ArrayList> {

  Movimiento m;
  Date fechaIni;
  Date fechaFin;

  public ExecutorRegrabado(Movimiento m, Date fechaIni, Date fechaFin) {
    this.m = m;
    this.fechaIni = fechaIni;
    this.fechaFin = fechaFin;
  }

  @Override
  public ArrayList call() throws Exception {
    RegrabadoProducto1 rp = new RegrabadoProducto1(m, fechaIni, fechaFin);
    return rp.regrabadoProducto();
  }

  private static ArrayList<Kardex> aDocs = new ArrayList<>();
  private static ArrayList<Movimiento> aMovs = new ArrayList<>();

  public static void main(String[] args)
          throws ParseException, ClassNotFoundException, SQLException, InterruptedException, ExecutionException {
    String iniDate = "01-01-2018";
    String finDate = "31-01-2018";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    java.util.Date dateIni = sdf.parse(iniDate);
    Date fechaIni = new Date(dateIni.getTime());

    java.util.Date dateFin = sdf.parse(finDate);
    Date fechaFin = new Date(dateFin.getTime());

    DatosDAO db = new DatosDAO();
    aMovs = db.getMovimientos(fechaIni, fechaFin);

    System.out.println(aMovs.size());

    ExecutorService executorService = Executors.newFixedThreadPool(10);
    Future<ArrayList>[] futures = new Future[aMovs.size()];
    for (int i = 0; i < aMovs.size(); i++) {
      Movimiento m = aMovs.get(i);
      futures[i] = executorService.submit(new ExecutorRegrabado(m, fechaIni, fechaFin));
    }
    executorService.shutdown();

    System.out.println("termino de procesar hilos");

    for (int i = 0; i < futures.length; i++) {
      aDocs.addAll(futures[i].get());
    }
  }

}
