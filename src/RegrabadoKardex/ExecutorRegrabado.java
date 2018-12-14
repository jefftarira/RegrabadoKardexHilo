package RegrabadoKardex;

import RegrabadoKardex.DB.ConexionPoolMysql;
import RegrabadoKardex.DB.ConexionPoolPostgres;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
          throws ParseException, ClassNotFoundException, SQLException,
          InterruptedException, ExecutionException {

    ConexionPoolPostgres poolP = new ConexionPoolPostgres();
    ConexionPoolMysql poolM = new ConexionPoolMysql();

    String iniDate = "01-01-2018";
    String finDate = "31-12-2018";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    java.util.Date dateIni = sdf.parse(iniDate);
    Date fechaIni = new Date(dateIni.getTime());

    java.util.Date dateFin = sdf.parse(finDate);
    Date fechaFin = new Date(dateFin.getTime());

    DatosDAO db = new DatosDAO();
    aMovs = db.getMovimientos(fechaIni, fechaFin);

    System.out.println(aMovs.size());

//    ExecutorService executorService = Executors.newFixedThreadPool(8);
    ExecutorService executorService = Executors.newWorkStealingPool();
    Future<ArrayList>[] futures = new Future[aMovs.size()];
    for (int i = 0; i < aMovs.size(); i++) {
      Movimiento m = aMovs.get(i);
      futures[i] = executorService.submit(new ExecutorRegrabado(m, fechaIni, fechaFin));
    }
    executorService.shutdown();

    for (int i = 0; i < futures.length; i++) {
      ArrayList<Kardex> data = futures[i].get();
      if (data == null) {
        System.out.println("error en data" + futures[i].get());
      }
      aDocs.addAll(data);
    }

    DatosDAO DB = new DatosDAO();

    ArrayList<Usuarios> usuarios = DB.getUsuariosSIP();
    ArrayList<Division> divs = DB.getDivisionesSIP();
    ArrayList<MovimientoInv> movs = DB.getTiposMovSIP();

    Redondear re = new Redondear();
    System.out.println("Total de registros en kardex : " + aDocs.size());
    System.out.printf("%-10s%-12s%-10s%-10s%-12s%-18s%-15s"
            + "%-12s%-12s%-20s"
            + "%-12s%-12s%-20s"
            + "%-12s%-12s\n",
            "Bodega", "Fecha", "Tipo", "Numero", "Usuario", "Documento", "Producto",
            "Cantidad", "CostoU", "CostoTotal",
            "Cantidad", "CostoPro", "CostoTotal",
            "CantidadAnt", "CostoUAnt");

    aDocs.forEach((c) -> {

      // Completando datos de usuarios;
      for (Usuarios u : usuarios) {
        if (c.getKardexusuario().trim().toUpperCase().equals(u.getUsuario().trim().toUpperCase())) {
          c.setIdUsuario(u.getId());
        }
      }

      // Completando datos de divisiones
      for (Division d : divs) {
        if (d.getIdMarketsoft().trim().equals(c.getKardexcodigodiv())) {
          c.setIdDivision(d.getId());
        }
      }

      // Completando datos de tipos de movimientos
      for (MovimientoInv m : movs) {
        if (m.getDescripcion().toUpperCase().trim().equals(c.getKardextipo().toUpperCase().trim())
                && m.getDocumento().toUpperCase().trim().equals(c.getKardextipotrx().toUpperCase().trim())) {
          c.setIdTipo(m.getId());
        }
      }

      System.out.printf("%-10s%-12s%-10s%-10s%-12s%-18s%-15s"
              + "%-14s%-14s%-22s"
              + "%-14s%-14s%-22s"
              + "%-14s%-14s\n",
              c.getTbodcodigo(), c.getKardexfecha(), c.getKardextipotrx(), c.getKardexnumero(), c.getKardexusuario(), c.getKardextipo(), c.getProductoscodigo(),
              c.getKardexcantidad(), c.getKardexpreciocompra(), c.getKardexcostototal(),
              c.getKardexstock(), c.getKardexcostopromedio(), c.getKardexcostototalstock(),
              c.getKardexcantidad_a(), c.getKardexcostopromedio_a());
    });

    System.out.println("Total de registros en kardex : " + aDocs.size());
    System.out.println("Actualizando Base de datos");
    int rAfectados = db.saveChanges(aDocs, fechaIni, fechaFin);
    System.out.println("Se actualizaron " + rAfectados + " registros");
  }

}
