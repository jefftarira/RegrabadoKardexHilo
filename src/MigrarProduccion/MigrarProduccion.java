package MigrarProduccion;

import MigrarProduccion.MODELS.DetalleProduccion;
import MigrarProduccion.MODELS.OpEstadoHistorial;
import MigrarProduccion.MODELS.OpOt;
import MigrarProduccion.MODELS.OrdenProduccion;
import MigrarProduccion.MODELS.Usuarios;
import java.sql.SQLException;
import java.util.ArrayList;

public class MigrarProduccion {

  public static void main(String args[]) throws ClassNotFoundException, SQLException {

    System.out.println("Comenzando proceso de migranción de producción");

    DatosDAO DB = new DatosDAO();

    ArrayList<OrdenProduccion> prodCab = DB.getCabOpMarketsoft();
    ArrayList<DetalleProduccion> prodDet = DB.getDetsOpMarketsoft();
    ArrayList<Usuarios> usuarios = DB.getUsuariosSIP();
    ArrayList<OpOt> listaOpOt = DB.getListaOpOtSIP();
    ArrayList<OpEstadoHistorial> historial = new ArrayList<>();

    boolean outUser = false;

    for (OrdenProduccion p : prodCab) {
      for (Usuarios u : usuarios) {
        if (p.getNombreUsuario().equals(u.getUsuario())) {
          p.setIdUsuario(u.getId());
        }
      }

      if (p.getIdUsuario() == 0) {
        outUser = true;
        System.out.println("No se encontro usuario para la producción # " + p.getId());
      }

      if (p.getNombreEstado().trim().equals("CREADA") || p.getNombreEstado().trim().equals("ATENDIDA")) {
        p.setIdEstado(13);
        OpEstadoHistorial his = new OpEstadoHistorial(0,
                p.getIdUsuario(),
                "",
                p.getId(),
                p.getIdEstado(),
                "",
                new java.sql.Timestamp(p.getFechaInicio().getTime()),
                "",
                "A");
        historial.add(his);
      }
      if (p.getNombreEstado().trim().equals("CERRADA")) {
        p.setIdEstado(15);
        OpEstadoHistorial his = new OpEstadoHistorial(0,
                p.getIdUsuario(),
                "",
                p.getId(),
                13,
                "",
                new java.sql.Timestamp(p.getFechaInicio().getTime()),
                "",
                "A");
        historial.add(his);
        his = new OpEstadoHistorial(0,
                p.getIdUsuario(),
                "",
                p.getId(),
                p.getIdEstado(),
                "",
                new java.sql.Timestamp(p.getFechaInicio().getTime()),
                "",
                "A");
        historial.add(his);
      }
      if (p.getNombreEstado().trim().equals("SUSPENSA")) {
        p.setIdEstado(16);
        OpEstadoHistorial his = new OpEstadoHistorial(0,
                p.getIdUsuario(),
                "",
                p.getId(),
                13,
                "",
                new java.sql.Timestamp(p.getFechaInicio().getTime()),
                "",
                "A");
        historial.add(his);
        his = new OpEstadoHistorial(0,
                p.getIdUsuario(),
                "",
                p.getId(),
                p.getIdEstado(),
                "",
                new java.sql.Timestamp(p.getFechaInicio().getTime()),
                "",
                "A");
        historial.add(his);
      }

    }

    if (outUser == false) {
      int resultado = DB.saveDataSIP(prodCab, prodDet, historial, listaOpOt);
      System.out.println("Se migraron " + resultado + " Ordenes de producción");
    }

    System.out.println("NO. total de producciones: " + prodCab.size());
    System.out.println("Total detalles : " + prodDet.size());

  }

}
