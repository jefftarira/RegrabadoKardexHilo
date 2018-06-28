package MigrarProduccion;

import MigrarProduccion.MODELS.DetalleProduccion;
import MigrarProduccion.MODELS.OrdenProduccion;
import MigrarProduccion.MODELS.Usuarios;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatosDAO {

  private final ConexionMysql conM;
  private final ConexionPostgres conP;

  private final String sCabOpsMarketsoft = "select\n"
          + "  o.ordpronumero,\n"
          + "  o.ordprofecha,\n"
          + "  o.ordprofecharequerida,\n"
          + "  o.ordpronumlote,\n"
          + "  o.ordpronumpersonas,\n"
          + "  o.ordproconcepto,\n"
          + "  o.ordproestado,\n"
          + "  o.productoscodigo,\n"
          + "  p.productosnombre,\n"
          + "  p.productosunidadmedida,\n"
          + "  o.ordprototalproducir,\n"
          + "  o.ordprohorasproduccion,\n"
          + "  o.ordproreferencia,\n"
          + "  o.ordprocreateuser,\n"
          + "  o.ordprohorasprods,\n"
          + "  o.ordpronumperss,\n"
          + "  o.ordprohorasproda,\n"
          + "  o.ordpronumpersa,\n"
          + "  o.ordprohorasprodt,\n"
          + "  o.ordpronumperst,\n"
          + "  o.ordprocodigocolor\n"
          + "from ordpro o, productos p\n"
          + "where o.ordprofecha >= '01-10-2015'\n"
          + "and o.productoscodigo = p.productoscodigo ";

  private final String sDetOpsMarketsoft = "select\n"
          + "  d.ordpronumero,\n"
          + "  d.mpaproductoscodigo,\n"
          + "  p.productosnombre,\n"
          + "  d.mpacantidad,\n"
          + "  p.productosunidadmedida,\n"
          + "  d.detproareaaplica\n"
          + "from ordprodetpro d, productos p\n"
          + "where d.mpaproductoscodigo = p.productoscodigo";

  private final String sUsersSIP = "select\n"
          + "  id,\n"
          + "  usuario\n"
          + "from usuarios ";

  public DatosDAO() {
    conM = new ConexionMysql();
    conP = new ConexionPostgres();
  }

  public ArrayList<OrdenProduccion> getCabOpMarketsoft() throws ClassNotFoundException, SQLException {
    ArrayList<OrdenProduccion> aOps = new ArrayList<>();

    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sCabOpsMarketsoft);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      OrdenProduccion op = new OrdenProduccion();
      op.setId(rs.getInt("ordpronumero"));
      op.setNombreEstado(rs.getString("ordproestado").trim().toUpperCase());
      op.setNombreUsuario(rs.getString("ordprocreateuser").trim().toUpperCase());
      op.setFechaEmision(rs.getTimestamp("ordprofecha"));
      op.setFechaInicio(rs.getDate("ordprofecha"));
      op.setFechaFin(rs.getDate("ordprofecharequerida"));
      op.setCodigoProducto(rs.getString("productoscodigo").trim().toUpperCase());
      op.setNombreProducto(rs.getString("productosnombre").trim().toUpperCase());
      op.setMedida(rs.getString("productosunidadmedida").trim().toUpperCase());
      op.setCantidad(rs.getBigDecimal("ordprototalproducir"));
      op.setColor(rs.getString("ordprocodigocolor") == null ? "SIN DEFINIR" : rs.getString("ordprocodigocolor").trim().toUpperCase());
      op.setObservaciones(rs.getString("ordproconcepto").trim().toUpperCase()
              + " " + rs.getString("ordproreferencia").trim().toUpperCase());
      op.setHorasRotomoldeo(rs.getBigDecimal("ordprohorasproduccion"));
      op.setHorasSoldadura(rs.getBigDecimal("ordprohorasprods"));
      op.setHorasAcabado(rs.getBigDecimal("ordprohorasproda"));
      op.setHorasTaller(rs.getBigDecimal("ordprohorasprodt"));
      op.setPersonasRotomoldeo(new BigDecimal(rs.getInt("ordpronumpersonas")));
      op.setPersonasSoldadura(new BigDecimal(rs.getInt("ordpronumperss")));
      op.setPersonasAcabado(new BigDecimal(rs.getInt("ordpronumpersa")));
      op.setPersonasTaller(new BigDecimal(rs.getInt("ordpronumperst")));

      aOps.add(op);
    }

    rs.close();
    conP.cerrar();
    return aOps;
  }

  public ArrayList<DetalleProduccion> getDetsOpMarketsoft() throws ClassNotFoundException, SQLException {
    ArrayList<DetalleProduccion> aDets = new ArrayList<>();

    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sDetOpsMarketsoft);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      DetalleProduccion det = new DetalleProduccion();
      det.setIdOrdenProduccion(rs.getInt("ordpronumero"));
      det.setCodigoProducto(rs.getString("mpaproductoscodigo").trim().toUpperCase());
      det.setNombreProducto(rs.getString("productosnombre").trim().toUpperCase());
      det.setMedida(rs.getString("productosunidadmedida").trim().toUpperCase());
      det.setCantidad(rs.getBigDecimal("mpacantidad"));

      String area = rs.getString("detproareaaplica").trim().toUpperCase();
      if(area.equals("NA") || area.equals("RM") || area.equals("TT")){
        det.setIdArea(1);
      }
      if(area.equals("SO")){
        det.setIdArea(2);
      }
      if(area.equals("AC")){
        det.setIdArea(3);
      }
      if(area.equals("TA")){
        det.setIdArea(4);
      }      

      aDets.add(det);
    }

    rs.close();
    conP.cerrar();
    return aDets;
  }

  public ArrayList<Usuarios> getUsuariosSIP() throws ClassNotFoundException, SQLException {
    ArrayList<Usuarios> aUsers = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sUsersSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      Usuarios u = new Usuarios();
      u.setId(rs.getInt("id"));
      u.setUsuario(rs.getString("usuario").trim().toUpperCase());

      aUsers.add(u);
    }

    rs.close();
    conM.cerrar();
    return aUsers;
  }

}
