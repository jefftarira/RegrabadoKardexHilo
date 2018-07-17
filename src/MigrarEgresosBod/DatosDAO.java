package MigrarEgresosBod;

import MigrarEgresosBod.MODELS.EgresoInv;
import MigrarEgresosBod.MODELS.BodegaInv;
import MigrarEgresosBod.MODELS.Division;
import MigrarEgresosBod.MODELS.EgresoInvDetalle;
import MigrarEgresosBod.MODELS.EgresoInvDetalleProduccion;
import MigrarEgresosBod.MODELS.EgresoInvHistorial;
import MigrarEgresosBod.MODELS.EgresoInvVendedor;
import MigrarEgresosBod.MODELS.MovimientoInv;
import MigrarEgresosBod.MODELS.OpDetalle;
import MigrarEgresosBod.MODELS.Usuarios;
import MigrarEgresosBod.MODELS.Vendedor;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatosDAO {

  private final ConexionMysql conM;
  private final ConexionPostgres conP;

  public DatosDAO() {
    conM = new ConexionMysql();
    conP = new ConexionPostgres();
  }

  private final String sUsersSIP = "select\n"
          + "  id,\n"
          + "  usuario\n"
          + "from usuarios ";

  private final String sCabEgrMarketsoft = "select\n"
          + "  c.notegrcodigodiv,\n"
          + "  c.notegrnumero,\n"
          + "  c.notegrcreateuser,\n"
          + "  (CASE WHEN substring(trim(c.notegrnumorden) from 1 for 1) = 'O'\n"
          + "    THEN to_number(substring(trim(c.notegrnumorden) from 3), '999999999') :: int\n"
          + "   ELSE to_number(trim(c.notegrnumorden), '9999999999') :: int\n"
          + "   END) as notegrnumorden,\n"
          + "  c.notegrfecha,\n"
          + "  c.notegrtipo,\n"
          + "  c.notegrconcepto,\n"
          + "  c.notegrentregadopor,\n"
          + "  c.notegrbodega1,\n"
          + "  c.notegrbodega2,\n"
          + "  c.notegrcontab,\n"
          + "  c.notegrvendedorresp,\n"
          + "  c.notegrdepartamento\n"
          + "from notegr c\n"
          + "order by c.notegrfecha,  c.notegrnumero, c.notegrcreateuser ";

  private final String sDetEgrMarketsoft = "select\n"
          + "  d.notegrcodigodiv,\n"
          + "  d.notegrnumero,\n"
          + "  d.notegrcreateuser,\n"
          + "  d.productoscodigo,\n"
          + "  p.productosnombre,\n"
          + "  p.productosunidadmedida,\n"
          + "  d.detntecantidad,\n"
          + "  d.detntepreciounitario,\n"
          + "  d.detntepreciototal\n"
          + "from notegrdetnte d, productos p\n"
          + "where d.productoscodigo = p.productoscodigo ";

  private final String sDivisionSIP = "select\n"
          + "  id,\n"
          + "  idmarketsoft,\n"
          + "  descripcion,\n"
          + "  status\n"
          + "from divisiones ";

  private final String sTipoMovimientosSIP = "select\n"
          + "  id,\n"
          + "  descripcion,\n"
          + "  documento,\n"
          + "  status\n"
          + "from tiposmovimientosinv\n"
          + "where documento ='NTE'";

  private final String sBodegaInvSIP = "select\n"
          + "  id,\n"
          + "  idmarketsoft,\n"
          + "  descripcion,\n"
          + "  status\n"
          + "from bodegasinv";

  private final String sVendedoresSIP = "select\n"
          + "  id,\n"
          + "  idVendedorMarketsoft\n"
          + "from vendedores\n"
          + "where id != 4 AND id != 5\n"
          + "      and status = 'A'";

  private final String sOpDetalleSIP = "select\n"
          + "  d.id,\n"
          + "  d.idOrdenProduccion,\n"
          + "  d.codigoProducto,\n"
          + "  d.cantidad\n"
          + "from opdetalle d\n"
          + "where status = 'A'";

  private final String sMaxIdDetalle = "select max(id) maxid from egresoinvdetalle";

  private final String iEgresoInv = "insert into egresoinv (id, idUsuario, idDivision, idEstado,\n"
          + "                       idBodegaEgr, idMovimiento, fechaEmision, fechaKardex,\n"
          + "                       observaciones, buscar)\n"
          + "values (?, ?, ?, ?,\n"
          + "        ?, ?, ?, ?,\n"
          + "        ?, ?)";

  private final String iEgresoInvBodega2 = "insert into egresoinvbodega2 (idEgresoInv, idUsuario, idDivision, idBodegaIng)\n"
          + "values (?, ?, ?, ?)";

  private final String iEgresoInvDetalle = "insert into egresoinvdetalle (id, idEgresoInv, idUsuario, idDivision,\n"
          + "                              codigoProducto, nombreProducto, medida, cantidad,\n"
          + "                              costoUnitario, costoTotal)\n"
          + "values (?, ?, ?, ?,\n"
          + "        ?, ?, ?, ?,\n"
          + "        ?, ?)";

  private final String iEgresoInvHistorial = "insert into egresoinvhistorial (idEgresoInv, idUsuario, idDivision, idUsuarioHis,\n"
          + "                                idEstado, descripcion, fecha)\n"
          + "values (?, ?, ?, ?,\n"
          + "        ?, ?, ?)";

  private final String iEgresoVendedores = "insert into egresoinvvendedor (idEgresoInv, idUsuario, idDivision, idVendedor)\n"
          + "values (?, ?, ?, ?)";

  private final String iEgresoInvDetalleOP = "insert into egresoinvdetalle_ordenproduccion (idEgresoInvDetalle, idOpDetalle)\n"
          + "values (?, ?)";

  public ArrayList<EgresoInv> getCabEgrMarketsoft()
          throws ClassNotFoundException, SQLException {
    ArrayList<EgresoInv> lista = new ArrayList<>();
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sCabEgrMarketsoft);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      EgresoInv egr = new EgresoInv();
      egr.setNombreDivision(rs.getString("notegrcodigodiv").trim());
      egr.setId(rs.getInt("notegrnumero"));
      egr.setNombreUsuario(rs.getString("notegrcreateuser").trim());
      egr.setIdProduccion(rs.getInt("notegrnumorden"));
      egr.setNombreEstado(rs.getString("notegrcontab").trim());
      egr.setFechaEmision(rs.getTimestamp("notegrfecha"));
      egr.setFechaKardex(rs.getDate("notegrfecha"));
      egr.setNombreMovimiento(rs.getString("notegrtipo").trim());
      egr.setObservaciones(rs.getString("notegrconcepto").trim());
      egr.setNombreBodegaEgr(rs.getString("notegrbodega1").trim());
      egr.setNombreBodegaIng(rs.getString("notegrbodega2").trim());
      egr.setResponsable(rs.getString("notegrvendedorresp") == null ? "" : rs.getString("notegrvendedorresp").trim());
      lista.add(egr);
    }

    rs.close();
    conP.cerrar();

    return lista;
  }

  public ArrayList<EgresoInvDetalle> getDetEgrMarketsoft()
          throws ClassNotFoundException, SQLException {
    ArrayList<EgresoInvDetalle> lista = new ArrayList<>();
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sDetEgrMarketsoft);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      EgresoInvDetalle egr = new EgresoInvDetalle();
      egr.setNombreDivision(rs.getString("notegrcodigodiv").trim());
      egr.setIdEgresoInv(rs.getInt("notegrnumero"));
      egr.setNombreUsuario(rs.getString("notegrcreateuser").trim());
      egr.setCodigoProducto(rs.getString("productoscodigo").trim());
      egr.setNombreProducto(rs.getString("productosnombre").trim());
      egr.setMedida(rs.getString("productosunidadmedida").trim());
      egr.setCantidad(rs.getBigDecimal("detntecantidad") == null ? BigDecimal.ZERO : rs.getBigDecimal("detntecantidad"));
      egr.setCostoUnitario(rs.getBigDecimal("detntepreciounitario"));
      egr.setCostoTotal(rs.getBigDecimal("detntepreciototal"));
      lista.add(egr);
    }

    rs.close();
    conP.cerrar();

    return lista;
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

  public ArrayList<Division> getDivisionesSIP() throws ClassNotFoundException, SQLException {
    ArrayList<Division> lista = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sDivisionSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      Division d = new Division();
      d.setId(rs.getInt("id"));
      d.setIdMarketsoft(rs.getString("idmarketsoft"));

      lista.add(d);
    }

    rs.close();
    conM.cerrar();
    return lista;
  }

  public ArrayList<Vendedor> getVendedoresSIP() throws ClassNotFoundException, SQLException {
    ArrayList<Vendedor> lista = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sVendedoresSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      Vendedor v = new Vendedor();
      v.setId(rs.getInt("id"));
      v.setIdVendedorMarketsoft(rs.getString("idVendedorMarketsoft"));
      lista.add(v);
    }

    rs.close();
    conM.cerrar();
    return lista;
  }

  public ArrayList<MovimientoInv> getTiposMovSIP() throws ClassNotFoundException, SQLException {
    ArrayList<MovimientoInv> lista = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sTipoMovimientosSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      MovimientoInv m = new MovimientoInv();
      m.setId(rs.getInt("id"));
      m.setDescripcion(rs.getString("descripcion"));
      lista.add(m);
    }

    rs.close();
    conM.cerrar();
    return lista;
  }

  public ArrayList<BodegaInv> getBodegasInvSIP() throws ClassNotFoundException, SQLException {
    ArrayList<BodegaInv> lista = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sBodegaInvSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      BodegaInv o = new BodegaInv();
      o.setId(rs.getInt("id"));
      o.setIdMarketsoft(rs.getString("idmarketsoft"));
      o.setDescripcion(rs.getString("descripcion"));
      lista.add(o);
    }

    rs.close();
    conM.cerrar();
    return lista;
  }

  public ArrayList<OpDetalle> getDetalleOpSIP() throws ClassNotFoundException, SQLException {
    ArrayList<OpDetalle> lista = new ArrayList<>();
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sOpDetalleSIP);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      OpDetalle op = new OpDetalle();
      op.setId(rs.getInt("id"));
      op.setIdOrdenProduccion(rs.getInt("idOrdenProduccion"));
      op.setCodigoProducto(rs.getString("codigoProducto"));
      op.setCantidad(rs.getBigDecimal("cantidad"));
      lista.add(op);
    }

    rs.close();
    conM.cerrar();
    return lista;
  }

  public int getMaxIdDetalleEgresoInv() throws ClassNotFoundException, SQLException {
    PreparedStatement ps;
    conM.conectar();
    ps = conM.prepareStatement(sMaxIdDetalle);
    ResultSet rs = ps.executeQuery();
    rs.next();
    int value = rs.getInt("maxid");
    rs.close();
    conM.cerrar();
    return value;
  }

  public int saveDataSIP(ArrayList<EgresoInv> cabs, ArrayList<EgresoInvDetalle> dets,
          ArrayList<EgresoInvVendedor> vendedores, ArrayList<EgresoInvHistorial> historial,
          ArrayList<EgresoInvDetalleProduccion> detprod)
          throws ClassNotFoundException, SQLException {

    int respuesta = 0;
    PreparedStatement ps;
    conM.conectar();
    conM.autoCommit(false);

    try {
      for (EgresoInv egr : cabs) {
        ps = conM.prepareStatement(iEgresoInv);
        ps.setInt(1, egr.getId());
        ps.setInt(2, egr.getIdUsuario());
        ps.setInt(3, egr.getIdDivision());
        ps.setInt(4, egr.getIdEstado());
        ps.setInt(5, egr.getIdBodegaEgr());
        ps.setInt(6, egr.getIdMovimiento());
        ps.setTimestamp(7, egr.getFechaEmision());
        ps.setDate(8, egr.getFechaKardex());
        ps.setString(9, egr.getObservaciones());
        ps.setString(10, egr.getId() + "");
        ps.executeUpdate();

        if (egr.getIdBodegaIng() > 0) {
          ps = conM.prepareStatement(iEgresoInvBodega2);
          ps.setInt(1, egr.getId());
          ps.setInt(2, egr.getIdUsuario());
          ps.setInt(3, egr.getIdDivision());
          ps.setInt(4, egr.getIdBodegaIng());
          ps.executeUpdate();
        }
      }

      for (EgresoInvDetalle det : dets) {
        System.out.println(det.getNombreDivision() + "   " + det.getNombreUsuario() + "  " + det.getIdEgresoInv());

        ps = conM.prepareStatement(iEgresoInvDetalle);
        ps.setInt(1, det.getId());
        ps.setInt(2, det.getIdEgresoInv());
        ps.setInt(3, det.getIdUsuario());
        ps.setInt(4, det.getIdDivision());
        ps.setString(5, det.getCodigoProducto());
        ps.setString(6, det.getNombreProducto());
        ps.setString(7, det.getMedida());
        ps.setBigDecimal(8, det.getCantidad());
        ps.setBigDecimal(9, det.getCostoUnitario());
        ps.setBigDecimal(10, det.getCostoTotal());
        ps.executeUpdate();
      }

      for (EgresoInvVendedor ven : vendedores) {

        ps = conM.prepareStatement(iEgresoVendedores);
        ps.setInt(1, ven.getIdEgresoInv());
        ps.setInt(2, ven.getIdUsuario());
        ps.setInt(3, ven.getIdDivision());
        ps.setInt(4, ven.getIdVendedor());
        ps.executeUpdate();
      }

      for (EgresoInvHistorial his : historial) {
        ps = conM.prepareStatement(iEgresoInvHistorial);
        ps.setInt(1, his.getIdEgresoInv());
        ps.setInt(2, his.getIdUsuario());
        ps.setInt(3, his.getIdDivision());
        ps.setInt(4, his.getIdUsuarioHis());
        ps.setInt(5, his.getIdEstado());
        ps.setString(6, his.getDescripcion());
        ps.setTimestamp(7, his.getFecha());
        ps.executeUpdate();
      }

      for (EgresoInvDetalleProduccion ep : detprod) {
        ps = conM.prepareStatement(iEgresoInvDetalleOP);
        ps.setInt(1, ep.getIdEgresoInvDetalle());
        ps.setInt(2, ep.getIdOpDetalle());
        ps.executeUpdate();
      }

      conM.Commit();
      conM.cerrar();
    } catch (SQLException ex) {
      conM.Rollback();
      conM.cerrar();
      ex.printStackTrace();
    }

    return respuesta;

  }

}
