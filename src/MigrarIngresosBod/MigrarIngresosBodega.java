package MigrarIngresosBod;

import MigrarIngresosBod.MODELS.BodegaInv;
import MigrarIngresosBod.MODELS.Division;
import MigrarIngresosBod.MODELS.IngresoInv;
import MigrarIngresosBod.MODELS.IngresoInvDetalle;
import MigrarIngresosBod.MODELS.IngresoInvHistorial;
import MigrarIngresosBod.MODELS.MovimientoInv;
import MigrarIngresosBod.MODELS.Usuarios;
import java.sql.SQLException;
import java.util.ArrayList;

public class MigrarIngresosBodega {

  public static void main(String args[]) throws ClassNotFoundException, SQLException {
    System.out.println("Comenzando proceso de migranción de ingresos de bodega");

    DatosDAO DB = new DatosDAO();

    ArrayList<IngresoInv> cabs = DB.getCabIngMarketsoft();
    ArrayList<Usuarios> usuarios = DB.getUsuariosSIP();
    ArrayList<Division> divs = DB.getDivisionesSIP();
    ArrayList<MovimientoInv> movs = DB.getTiposMovSIP();
    ArrayList<BodegaInv> bods = DB.getBodegasInvSIP();
    ArrayList<IngresoInvDetalle> dets = DB.getDetIngMarketsoft();
    ArrayList<IngresoInvHistorial> historial = new ArrayList<>();

    for (IngresoInv i : cabs) {

      //  Modificando estados 
      i.setIdEstado(17);

      // Completando datos de usuarios;
      for (Usuarios u : usuarios) {
        if (i.getNombreUsuario().equals(u.getUsuario())) {
          i.setIdUsuario(u.getId());
        }
      }

      // Completando datos de divisiones
      for (Division d : divs) {
        if (d.getIdMarketsoft().trim().equals(i.getNombreDivision())) {
          i.setIdDivision(d.getId());
        }
      }

      // Completando datos de tipos de movimientos
      for (MovimientoInv m : movs) {
        if (m.getDescripcion().trim().equals(i.getNombreMovimiento().trim())) {
          i.setIdMovimiento(m.getId());
        }
      }

      // Completando datos de tipos de movimientos
      for (MovimientoInv m : movs) {
        if (m.getDescripcion().trim().equals(i.getNombreMovimiento().trim())) {
          i.setIdMovimiento(m.getId());
        }
      }

      // Completando datos de bodegas
      for (BodegaInv b : bods) {
        if (b.getIdMarketsoft().trim().equals(i.getNombreBodega().trim())) {
          i.setIdBodega(b.getId());
        }
      }

      IngresoInvHistorial his = new IngresoInvHistorial();
      his.setIdIngresoInv(i.getId());
      his.setIdUsuario(i.getIdUsuario());
      his.setIdDivision(i.getIdDivision());
      his.setIdUsuarioHis(i.getIdUsuario());
      his.setIdEstado(17);
      his.setDescripcion("");

      historial.add(his);

      for (IngresoInvDetalle det : dets) {
        if (i.getNombreDivision().trim().equals(det.getNombreDivision().trim())
                && i.getNombreUsuario().trim().equals(det.getNombreUsuario())
                && i.getId() == det.getIdIngresoInv()) {
          det.setIdDivision(i.getIdDivision());
          det.setIdUsuario(i.getIdUsuario());
        }
      }
    }

    for (IngresoInvDetalle det : dets) {
      if (det.getIdDivision() == 0 || det.getIdUsuario() == 0) {
        System.out.println("No se encontro usuario "
                + det.getIdUsuario() + " " + det.getNombreUsuario()
                + " o division " + det.getIdDivision() + " " + det.getNombreDivision()
                + " del Ingreso " + det.getIdIngresoInv());
      }
    }

    int resultado = DB.saveDataSIP(cabs, dets, historial);

    System.out.println("Se procesaron " + cabs.size() + " ingresos de bodega");
    System.out.println("Se procesaron " + dets.size() + " detalles ingresos de bodega");

  }

}
