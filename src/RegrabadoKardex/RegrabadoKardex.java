package RegrabadoKardex;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegrabadoKardex {

  public static void main(String arg[]) throws ClassNotFoundException, SQLException, InterruptedException {
    final ArrayList<Kardex> aDocs = new ArrayList<>();
    ArrayList<Movimiento> aMovs = null;

    Kardex k;
    final Date fechaIni = new Date((2017 - 1900), 9, 1);
    final Date fechaFin = new Date((2017 - 1900), 10, 30);

    DatosDAO db = new DatosDAO();
    aMovs = db.getMovimientos(fechaIni, fechaFin);

    final ArrayList<Thread> threads = new ArrayList<>();

    for (int i = 0; i < aMovs.size(); i++) {

      Thread t1 = null;
      Thread t2 = null;
      Thread t3 = null;
      RegrabadoProducto rp1 = null;
      RegrabadoProducto rp2 = null;
      RegrabadoProducto rp3 = null;

      if (i < aMovs.size()) {
        Movimiento m1 = aMovs.get(i);
        rp1 = new RegrabadoProducto(m1, fechaIni, fechaFin);
        t1 = new Thread(rp1);
        t1.start();
      }

      i++;
      if (i < aMovs.size()) {
        Movimiento m2 = aMovs.get(i);
        rp2 = new RegrabadoProducto(m2, fechaIni, fechaFin);
        t2 = new Thread(rp2);
        t2.start();
      }

      i++;
      if (i < aMovs.size()) {
        Movimiento m3 = aMovs.get(i);
        rp3 = new RegrabadoProducto(m3, fechaIni, fechaFin);
        t3 = new Thread(rp3);
        t3.start();
      }

      if (t1 != null) {        
        t1.join();        
        aDocs.addAll(rp1.getResultado());
      }

      if (t2 != null) {
        t2.join();
        aDocs.addAll(rp2.getResultado());
      }

      if (t3 != null) {
        t3.join();
        aDocs.addAll(rp3.getResultado());
      }

    }

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
      System.out.printf("%-10s%-12s%-10s%-10s%-12s%-18s%-15s"
              + "%-12s%-12s%-20s"
              + "%-12s%-12s%-20s"
              + "%-12s%-12s\n",
              c.getTbodcodigo(), c.getKardexfecha(), c.getKardextipotrx(), c.getKardexnumero(), c.getKardexusuario(), c.getKardextipo(), c.getProductoscodigo(),
              c.getKardexcantidad(), re.getRound(c.getKardexpreciocompra(), 4), re.getRound(c.getKardexcostototal(), 4),
              re.getRound(c.getKardexstock(), 4), re.getRound(c.getKardexcostopromedio(), 4), re.getRound(c.getKardexcostototalstock(), 4),
              re.getRound(c.getKardexcantidad_a(), 4), re.getRound(c.getKardexcostopromedio_a(), 4));
    });

    System.out.println("Actualizando Base de datos");
    int rAfectados = db.saveChanges(aDocs, fechaIni, fechaFin);
    System.out.println("Se actualizaron " + rAfectados + " registros");
  }

}
