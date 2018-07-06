package MigrarProduccion;

import MigrarProduccion.MODELS.DetalleProduccion;
import MigrarProduccion.MODELS.OpEstadoHistorial;
import MigrarProduccion.MODELS.OpOt;
import MigrarProduccion.MODELS.OrdenProduccion;
import MigrarProduccion.MODELS.Usuarios;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

  private final String sListaOpOtSIP = "select\n"
          + "  l.idOrdenTrabajo,\n"
          + "  l.idOrdenProduccion,\n"
          + "  count(*)\n"
          + "from ot_listaop l\n"
          + "where l.status = 'A'\n"
          + "group by 1, 2 ";

  private final String iOpSIP = " INSERT INTO ordenproduccion\n"
          + "(id, idEstado, idUsuario, fechaEmision, fechaInicio, fechaFin,\n"
          + " codigoProducto, nombreProducto, cantidad, medida, color,\n"
          + " horasRotomoldeo, horasSoldadura, horasTaller, horasAcabado, horasPulverizado,\n"
          + " personasRotomoldeo, personasSoldadura, personasTaller, personasAcabado, personasPulverizado,\n"
          + " observaciones, buscar, status)\n"
          + "  value\n"
          + "  (?, ?, ?, ?, ?, ?,\n"
          + "      ?, ?, ?, ?, ?,\n"
          + "    ?, ?, ?, ?, ?,\n"
          + "    ?, ?, ?, ?, ?,\n"
          + "   ?, ?, ?) ";

  private final String iDetProSIP = "INSERT INTO opdetalle\n"
          + "(idOrdenProduccion,\n"
          + " idArea,\n"
          + " llevafactura,\n"
          + " codigoProducto,\n"
          + " nombreProducto,\n"
          + " cantidad,\n"
          + " medida, \n"
          + " status)\n"
          + "VALUES\n"
          + "  (?,\n"
          + "   ?,\n"
          + "   ?,\n"
          + "   ?,\n"
          + "   ?,\n"
          + "   ?,\n"
          + "   ?,   \n"
          + "   'A') ";

  private final String iOpHisSIP = " INSERT INTO opestadohistorial "
          + " (idOrdenProduccion, idUsuario, idEstado, fecha, descripcion, status) "
          + " VALUES "
          + " (?, ?, ?, ?, ?, 'A')";

  private final String iOpOt = "insert into ordenproduccion_ordentrababajo (idOrdenProducccion, idOrdenTrabajo)\n"
          + "values (?, ?)";

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
      op.setHorasPulverizado(BigDecimal.ZERO);
      op.setPersonasRotomoldeo(new BigDecimal(rs.getInt("ordpronumpersonas")));
      op.setPersonasSoldadura(new BigDecimal(rs.getInt("ordpronumperss")));
      op.setPersonasAcabado(new BigDecimal(rs.getInt("ordpronumpersa")));
      op.setPersonasTaller(new BigDecimal(rs.getInt("ordpronumperst")));
      op.setPersonasPulverizado(BigDecimal.ZERO);

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
      if (area.equals("NA") || area.equals("RM") || area.equals("TT")) {
        det.setIdArea(1);
      }
      if (area.equals("SO")) {
        det.setIdArea(2);
      }
      if (area.equals("AC")) {
        det.setIdArea(3);
      }
      if (area.equals("TA")) {
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

  public ArrayList<OpOt> getListaOpOtSIP() throws ClassNotFoundException, SQLException {
    ArrayList<OpOt> lista = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sListaOpOtSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      lista.add(new OpOt(
              rs.getInt("idOrdenTrabajo"),
              rs.getInt("idOrdenProduccion")
      ));
    }
    rs.close();
    conM.cerrar();
    return lista;
  }

  public int saveDataSIP(ArrayList<OrdenProduccion> prodCab, ArrayList<DetalleProduccion> prodDet,
          ArrayList<OpEstadoHistorial> historial, ArrayList<OpOt> listaOpOt)
          throws ClassNotFoundException, SQLException {
    int resCab = 0;
    int resDet = 0;

    PreparedStatement ps;
    conM.conectar();
    conM.autoCommit(false);
    try {
      for (OrdenProduccion op : prodCab) {
        ps = conM.prepareStatement(iOpSIP);
        ps.setInt(1, op.getId());
        ps.setInt(2, op.getIdEstado());
        ps.setInt(3, op.getIdUsuario());
        ps.setDate(4, op.getFechaInicio());
        ps.setDate(5, op.getFechaInicio());
        ps.setDate(6, op.getFechaFin());

        ps.setString(7, op.getCodigoProducto().trim().toUpperCase());
        ps.setString(8, op.getNombreProducto().trim().toUpperCase());
        ps.setBigDecimal(9, op.getCantidad());
        ps.setString(10, op.getMedida().trim().toUpperCase());
        ps.setString(11, op.getColor().trim().toUpperCase());

        ps.setBigDecimal(12, op.getHorasRotomoldeo());
        ps.setBigDecimal(13, op.getHorasSoldadura());
        ps.setBigDecimal(14, op.getHorasTaller());
        ps.setBigDecimal(15, op.getHorasAcabado());
        ps.setBigDecimal(16, op.getHorasPulverizado());

        ps.setBigDecimal(17, op.getPersonasRotomoldeo());
        ps.setBigDecimal(18, op.getPersonasSoldadura());
        ps.setBigDecimal(19, op.getPersonasTaller());
        ps.setBigDecimal(20, op.getPersonasAcabado());
        ps.setBigDecimal(21, op.getPersonasPulverizado());

        ps.setString(22, op.getObservaciones());
        ps.setString(23, op.getId()
                + " " + op.getCodigoProducto().trim().toUpperCase()
                + " " + op.getNombreProducto().trim().toUpperCase()
                + " " + op.getColor().toUpperCase()
                + " " + op.getIdOrdenTrabajo());
        ps.setString(24, "A");
        resCab += ps.executeUpdate();
      }

      for (DetalleProduccion det : prodDet) {
        ps = conM.prepareStatement(iDetProSIP);
        ps.setInt(1, det.getIdOrdenProduccion());
        ps.setInt(2, det.getIdArea());
        ps.setBoolean(3, false);
        ps.setString(4, det.getCodigoProducto().trim().toUpperCase());
        ps.setString(5, det.getNombreProducto().trim().toUpperCase());
        ps.setBigDecimal(6, det.getCantidad());
        ps.setString(7, det.getMedida().trim().toUpperCase());
        ps.executeUpdate();
      }

      for (OpEstadoHistorial his : historial) {
        ps = conM.prepareStatement(iOpHisSIP);
        ps.setInt(1, his.getIdOrdenProduccion());
        ps.setInt(2, his.getIdUsuario());
        ps.setInt(3, his.getIdEstado());
        ps.setTimestamp(4, his.getFecha());
        ps.setString(5, his.getDescripcion().trim().toUpperCase());
        ps.executeUpdate();
      }

      for (OpOt opot : listaOpOt) {
        System.out.println("Procesando lista OP OT : OP# "+opot.getIdOrdenProduccion()+"  OT# "+opot.getIdOrdenTrabajo());
        ps = conM.prepareStatement(iOpOt);
        ps.setInt(1, opot.getIdOrdenProduccion());
        ps.setInt(2, opot.getIdOrdenTrabajo());        
        ps.executeUpdate();
      }

      conM.Commit();
      conM.cerrar();

    } catch (SQLException ex) {
      conM.Rollback();
      conM.cerrar();
      ex.printStackTrace();
    }
    return resCab;

  }

}
