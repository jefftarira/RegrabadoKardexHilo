package MigrarIngresosBod;

import MigrarIngresosBod.MODELS.BodegaInv;
import MigrarIngresosBod.MODELS.Division;
import MigrarIngresosBod.MODELS.IngresoInv;
import MigrarIngresosBod.MODELS.IngresoInvDetalle;
import MigrarIngresosBod.MODELS.IngresoInvHistorial;
import MigrarIngresosBod.MODELS.MovimientoInv;
import MigrarIngresosBod.MODELS.Usuarios;
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

  private final String sCabIngMarketsoft = "select\n"
          + "  c.notingcodigodiv,\n"
          + "  c.notingnumero,\n"
          + "  c.notingcreateuser,\n"
          + "  c.notingcegnum,\n"
          + "  c.notingfecha,\n"
          + "  c.notingtipo,\n"
          + "  c.notingconcepto,\n"
          + "  c.notingbodega,\n"
          + "  c.proveedorescodigo,\n"
          + "  (select pr.proveedoresrazonsocial\n"
          + "   from proveedores pr\n"
          + "   where c.proveedorescodigo = pr.proveedorescodigo) as proveedorNombre,\n"
          + "  c.clientescodigo,\n"
          + "  (select cl.clientesnombre\n"
          + "   from clientes cl\n"
          + "   where cl.clientescodigo = c.clientescodigo)       as clienteNombre,\n"
          + "  c.notingcontab,\n"
          + "  c.notingdescripcioncompleta\n"
          + "from noting c\n"
          + "order by c.notingfecha, c.notingnumero, c.notingcreateuser ";

  private final String sDetIngMarketsoft = "select\n"
          + "  d.notingcodigodiv,\n"
          + "  d.notingnumero,\n"
          + "  d.notingcreateuser,\n"
          + "  d.productoscodigo,\n"
          + "  p.productosnombre,\n"
          + "  p.productosunidadmedida,\n"
          + "  d.detnticantidad,\n"
          + "  d.detntipreciounitario,\n"
          + "  d.detntipreciototal\n"
          + "from notingdetnti d, productos p\n"
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
          + "where documento ='NTI'";

  private final String sBodegaInvSIP = "select\n"
          + "  id,\n"
          + "  idmarketsoft,\n"
          + "  descripcion,\n"
          + "  status\n"
          + "from bodegasinv";

  private final String iIngresoInv = "insert into ingresoinv (id, idUsuario, idDivision, idEstado, idBodega,\n"
          + "                        idMovimiento, fechaEmision, fechaKardex, idCliente,\n"
          + "                        nombreCliente, idProveedor, nombreProveedor, observaciones,\n"
          + "                        buscar)\n"
          + "values (?, ?, ?, ?, ?,\n"
          + "           ?, ?, ?, ?,\n"
          + "           ?, ?, ?, ?,\n"
          + "        ?) ";

  private final String iIngresoInvDetalle = "insert into ingresoinvdetalle (idIngresoInv, idUsuario, idDivision,\n"
          + "                               codigoProducto, nombreProducto, medida, cantidad,\n"
          + "                               costoUnitario, costoTotal)\n"
          + "values (?, ?, ?,\n"
          + "  ?, ?, ?, ?,\n"
          + "  ?, ?)";
  private final String iIngresoInvHistorial = "insert into ingresoinvhistorial (idIngresoInv, idUsuario, idDivision, idUsuarioHis,\n"
          + "                                 idEstado, descripcion, fecha)\n"
          + "values (?, ?, ?, ?,\n"
          + "        ?, ?, ?)";

  private final String iInvInvProd = "insert into ingresoinv_ordenproduccion \n"
          + " (idIngresoInv, idUsuario, idDivision, idOrdenProduccion) \n"
          + " values (?, ?, ?, ?)";

  public ArrayList<IngresoInv> getCabIngMarketsoft()
          throws ClassNotFoundException, SQLException {
    ArrayList<IngresoInv> lista = new ArrayList<>();
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sCabIngMarketsoft);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      IngresoInv ing = new IngresoInv();
      ing.setNombreDivision(rs.getString("notingcodigodiv").trim());
      ing.setId(rs.getInt("notingnumero"));
      ing.setNombreUsuario(rs.getString("notingcreateuser").trim());
      ing.setIdProduccion(rs.getInt("notingcegnum"));
      ing.setFechaEmision(rs.getTimestamp("notingfecha"));
      ing.setFechaKardex(rs.getDate("notingfecha"));
      ing.setNombreMovimiento(rs.getString("notingtipo").trim());
      ing.setObservaciones(rs.getString("notingconcepto").trim());
      ing.setNombreBodega(rs.getString("notingbodega").trim());
      ing.setIdProveedor(rs.getString("proveedorescodigo") == null ? "" : rs.getString("proveedorescodigo").trim());
      ing.setNombreProveedor(rs.getString("proveedorNombre") == null ? "" : rs.getString("proveedorNombre").trim());
      ing.setIdCliente(rs.getString("clientescodigo") == null ? "" : rs.getString("clientescodigo").trim());
      ing.setNombreCliente(rs.getString("clienteNombre") == null ? "" : rs.getString("clienteNombre").trim());
      ing.setNombreEstado(rs.getString("notingcontab").trim());

      lista.add(ing);
    }

    rs.close();
    conP.cerrar();

    return lista;
  }

  public ArrayList<IngresoInvDetalle> getDetIngMarketsoft()
          throws ClassNotFoundException, SQLException {
    ArrayList<IngresoInvDetalle> lista = new ArrayList<>();
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sDetIngMarketsoft);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {

      IngresoInvDetalle ing = new IngresoInvDetalle();

      ing.setNombreDivision(rs.getString("notingcodigodiv").trim());
      ing.setIdIngresoInv(rs.getInt("notingnumero"));
      ing.setNombreUsuario(rs.getString("notingcreateuser").trim());
      ing.setCodigoProducto(rs.getString("productoscodigo").trim());
      ing.setNombreProducto(rs.getString("productosnombre").trim());
      ing.setMedida(rs.getString("productosunidadmedida").trim());
      ing.setCantidad(rs.getBigDecimal("detnticantidad") == null ? BigDecimal.ZERO : rs.getBigDecimal("detnticantidad"));
      ing.setCostoUnitario(rs.getBigDecimal("detntipreciounitario"));
      ing.setCostoTotal(rs.getBigDecimal("detntipreciototal"));
      lista.add(ing);
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

  public int saveDataSIP(ArrayList<IngresoInv> cabs, ArrayList<IngresoInvDetalle> dets,
          ArrayList<IngresoInvHistorial> historial) throws ClassNotFoundException, SQLException {

    int respuesta = 0;
    PreparedStatement ps;
    conM.conectar();
    conM.autoCommit(false);

    try {
      for (IngresoInv ing : cabs) {
        ps = conM.prepareStatement(iIngresoInv);
        ps.setInt(1, ing.getId());
        ps.setInt(2, ing.getIdUsuario());
        ps.setInt(3, ing.getIdDivision());
        ps.setInt(4, ing.getIdEstado());
        ps.setInt(5, ing.getIdBodega());
        ps.setInt(6, ing.getIdMovimiento());
        ps.setTimestamp(7, ing.getFechaEmision());
        ps.setDate(8, ing.getFechaKardex());
        ps.setString(9, ing.getIdCliente());
        ps.setString(10, ing.getNombreCliente());
        ps.setString(11, ing.getIdProveedor());
        ps.setString(12, ing.getNombreProveedor());
        ps.setString(13, ing.getObservaciones());
        ps.setString(14, ing.getId()
                + " " + ing.getIdCliente()
                + (ing.getIdProduccion() == 0 ? "" : " " + ing.getIdProduccion())
                + " " + ing.getNombreCliente()
                + " " + ing.getIdProveedor()
                + " " + ing.getNombreProveedor()
                + " " + ing.getObservaciones());
        ps.executeUpdate();

        if (ing.getIdProduccion() != 0) {
          ps = conM.prepareStatement(iInvInvProd);
          ps.setInt(1, ing.getId());
          ps.setInt(2, ing.getIdUsuario());
          ps.setInt(3, ing.getIdDivision());
          ps.setInt(4, ing.getIdProduccion());
          ps.executeUpdate();

        }

      }

      for (IngresoInvDetalle det : dets) {

        System.out.println("guardando detalle " + det.getIdIngresoInv() + " Usuario " + det.getIdUsuario() + " Division " + det.getIdDivision());
        ps = conM.prepareStatement(iIngresoInvDetalle);
        ps.setInt(1, det.getIdIngresoInv());
        ps.setInt(2, det.getIdUsuario());
        ps.setInt(3, det.getIdDivision());
        ps.setString(4, det.getCodigoProducto());
        ps.setString(5, det.getNombreProducto());
        ps.setString(6, det.getMedida());
        ps.setBigDecimal(7, det.getCantidad());
        ps.setBigDecimal(8, det.getCostoUnitario());
        ps.setBigDecimal(9, det.getCostoTotal());
        ps.executeUpdate();
      }

      for (IngresoInvHistorial his : historial) {
        ps = conM.prepareStatement(iIngresoInvHistorial);
        ps.setInt(1, his.getIdIngresoInv());
        ps.setInt(2, his.getIdUsuario());
        ps.setInt(3, his.getIdDivision());
        ps.setInt(4, his.getIdUsuarioHis());
        ps.setInt(5, his.getIdEstado());
        ps.setString(6, his.getDescripcion());
        ps.setTimestamp(7, his.getFecha());
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
