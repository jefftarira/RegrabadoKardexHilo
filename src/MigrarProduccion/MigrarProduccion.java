package MigrarProduccion;

import MigrarProduccion.MODELS.DetalleProduccion;
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

    }

    if (outUser == false) {

    }

    System.out.println("NO. total de producciones: " + prodCab.size());
    System.out.println("Total detalles : " + prodDet.size());

  }

}
