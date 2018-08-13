package MigrarEgresosBod;

import MigrarEgresosBod.MODELS.EgresoInv;
import MigrarEgresosBod.MODELS.BodegaInv;
import MigrarEgresosBod.MODELS.Division;
import MigrarEgresosBod.MODELS.EgresoInvDetalle;
import MigrarEgresosBod.MODELS.EgresoInvHistorial;
import MigrarEgresosBod.MODELS.EgresoInvVendedor;
import MigrarEgresosBod.MODELS.MovimientoInv;
import MigrarEgresosBod.MODELS.OpDetalle;
import MigrarEgresosBod.MODELS.Usuarios;
import MigrarEgresosBod.MODELS.Vendedor;
import java.sql.SQLException;
import java.util.ArrayList;

public class MigrarEgresosBodega {

  public static void main(String args[]) throws ClassNotFoundException, SQLException {
    System.out.println("Comenzando proceso de migranción de Egresos de Bodega");

    DatosDAO DB = new DatosDAO();

    ArrayList<EgresoInv> cabs = DB.getCabEgrMarketsoft();

    ArrayList<Usuarios> usuarios = DB.getUsuariosSIP();
    ArrayList<Division> divs = DB.getDivisionesSIP();
    ArrayList<MovimientoInv> movs = DB.getTiposMovSIP();
    ArrayList<BodegaInv> bods = DB.getBodegasInvSIP();
    ArrayList<Vendedor> vends = DB.getVendedoresSIP();

    ArrayList<EgresoInvDetalle> dets = DB.getDetEgrMarketsoft();
    ArrayList<EgresoInvHistorial> historial = new ArrayList<>();
    ArrayList<EgresoInvVendedor> vendedores = new ArrayList<>();

    ArrayList<OpDetalle> dProds = DB.getDetalleOpSIP();
    int MaxIdDetalle = DB.getMaxIdDetalleEgresoInv();

    for (EgresoInv e : cabs) {

      //  Modificando estados 
      e.setIdEstado(21);

      // Completando datos de usuarios;
      for (Usuarios u : usuarios) {
        if (e.getNombreUsuario().equals(u.getUsuario())) {
          e.setIdUsuario(u.getId());
        }
      }

      // Completando datos de divisiones
      for (Division d : divs) {
        if (d.getIdMarketsoft().trim().equals(e.getNombreDivision())) {
          e.setIdDivision(d.getId());
        }
      }

      // Completando datos de tipos de movimientos
      for (MovimientoInv m : movs) {
        if (m.getDescripcion().trim().equals(e.getNombreMovimiento().trim())) {
          e.setIdMovimiento(m.getId());
        }
      }

      // Completando datos de tipos de movimientos
      for (MovimientoInv m : movs) {
        if (m.getDescripcion().trim().equals(e.getNombreMovimiento().trim())) {
          e.setIdMovimiento(m.getId());
        }
      }

      // Completando datos de bodegas
      for (BodegaInv b : bods) {
        if (b.getIdMarketsoft().trim().equals(e.getNombreBodegaEgr().trim())) {
          e.setIdBodegaEgr(b.getId());
        }
        if (b.getIdMarketsoft().trim().equals(e.getNombreBodegaIng().trim())) {
          e.setIdBodegaIng(b.getId());
        }
      }

//      Creados datos de Vendedores responsables     
      if (e.getResponsable().trim().length() > 0) {
        for (Vendedor v : vends) {
          if (v.getIdVendedorMarketsoft().trim().equals(e.getResponsable().trim())) {
            EgresoInvVendedor ev = new EgresoInvVendedor();
            ev.setIdEgresoInv(e.getId());
            ev.setIdDivision(e.getIdDivision());
            ev.setIdUsuario(e.getIdUsuario());
            ev.setIdVendedor(v.getId());
            vendedores.add(ev);
          }
        }
      }

      // Completando datos de historial
      EgresoInvHistorial his = new EgresoInvHistorial();
      his.setIdEgresoInv(e.getId());
      his.setIdUsuario(e.getIdUsuario());
      his.setIdDivision(e.getIdDivision());
      his.setIdUsuarioHis(e.getIdUsuario());
      his.setIdEstado(21);
      his.setDescripcion("");
      his.setFecha(e.getFechaEmision());
      historial.add(his);

      for (EgresoInvDetalle det : dets) {
        if (e.getNombreDivision().trim().equals(det.getNombreDivision().trim())
                && e.getNombreUsuario().trim().equals(det.getNombreUsuario())
                && e.getId() == det.getIdEgresoInv()) {
          det.setIdDivision(e.getIdDivision());
          det.setIdUsuario(e.getIdUsuario());
          det.setIdProduccion(e.getIdProduccion());
        }
      }
    }

    for (EgresoInvDetalle det : dets) {
      if (det.getIdDivision() == 0 || det.getIdUsuario() == 0) {
        System.out.println("No se encontro usuario "
                + det.getIdUsuario() + " " + det.getNombreUsuario()
                + " o division " + det.getIdDivision() + " " + det.getNombreDivision()
                + " del Ingreso " + det.getIdEgresoInv());
      }
      MaxIdDetalle++;
      det.setId(MaxIdDetalle);

    }

    System.out.println("Guardando información en Base datos");
    int resultado = DB.saveDataSIP(cabs, dets, vendedores, historial);

    System.out.println("Se procesaron " + cabs.size() + " egresos de bodega");
    System.out.println("Se procesaron " + dets.size() + " detalles egresos de bodega");

  }

}
