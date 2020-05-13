package RegrabadoKardex;

import RegrabadoKardex.Models.Movimiento;
import RegrabadoKardex.Models.Materiales;
import RegrabadoKardex.Models.FactorCosto;
import RegrabadoKardex.Models.Usuarios;
import RegrabadoKardex.Models.Kardex;
import RegrabadoKardex.Models.MovimientoInv;
import RegrabadoKardex.Models.Division;
import RegrabadoKardex.Models.Bodega;
import RegrabadoKardex.DB.PoolMysql;
import RegrabadoKardex.DB.PoolPostgres;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class DatosDAO {

  private final PoolMysql poolM;
  private final PoolPostgres poolP;

  DatosDAO() {
    poolP = new PoolPostgres();
    poolM = new PoolMysql();
  }

  ArrayList<Kardex> getDocumentos(String codProducto, Date fechaIni, Date fechaFin)
          throws SQLException {

    ArrayList<Kardex> aDoc = new ArrayList<>();
    Kardex k;
    ResultSet rs;
    PreparedStatement ps;
    Connection conP = poolP.getDataSource().getConnection();

    String sDocumentos = " select     kardexcodigodiv,kardexanno,kardextipotrx,kardexnumero,kardexlinea,kardexorden, "
            + " kardexfecha,productotodo,productoscodigo,tbodcodigo,tbodcodigo2,kardexlote,kardexcaducidad,kardexdescripcion, "
            + " kardextipo,kardexcodigoven1,kardexpreciocompra,kardexprecioventa,kardexvalordescuento, "
            + " kardexcantidad,kardexusuario,kardexnumref,kardexregorden "
            + " from( "
            + " ( "
            + "   select  d.facturacodigodiv as kardexcodigodiv,d.facturaanno as kardexanno,'FAC'::text as kardextipotrx, "
            + "           d.facturanumero as kardexnumero,d.detfaclinea as kardexlinea,1 as kardexorden, "
            + "           c.facturafecha as kardexfecha,' '::text as productotodo,d.productoscodigo as productoscodigo,d.tbodcodigo as tbodcodigo, "
            + "           ''::text as tbodcodigo2,''::text as kardexlote,c.srifacfechafue as kardexcaducidad,'FACTURA'::text as kardexdescripcion, "
            + "           'VENTA'::text as kardextipo,c.vendedorescodigo as kardexcodigoven1,d.detfacprecio as kardexpreciocompra, "
            + "           d.detfacprecio as kardexprecioventa, d.detfacvalordescuento as kardexvalordescuento, "
            + "           d.detfaccantidad*-1 as kardexcantidad,c.facturacreateuser as kardexusuario, "
            + "           0 as kardexnumref,50 as kardexregorden "
            + "   from   facturadetfac d,factura c "
            + "   where  c.facturanumero = d.facturanumero "
            + "   and    c.facturacodigodiv = d.facturacodigodiv "
            + "   and    (c.facturacontabilizado = 'PO' or c.facturacontabilizado = 'SI') "
            + "   and    c.facturafecha >=  ? "
            + "   and    c.facturafecha <= ? "
            + "   and    d.productoscodigo = ? "
            + " ) "
            + " UNION "
            + " ( "
            + "   select  d.notingcodigodiv as notingcodigodiv,d.notinganno as kardexanno,'NTI' as kardextipotrx,"
            + "           d.notingnumero as kardexnumero,d.detntiline as kardexlinea,1 as kardexorden, "
            + "       	c.notingfecha as kardexfecha, "
            + "           ( select  o.mpaproductoscodigo "
            + "             from    ordprodetpro o  "
            + "             where	o.ordpronumero = notingcegnum "
            + "             and   o.mpaproductoscodigo = d.productoscodigo limit 1 "
            + "           ) as productotodo, "
            + "           d.productoscodigo as productoscodigo,d.detnticodigobodega as tbodcodigo, "
            + "           '' as tbodcodigo2,c.notingloteprod as kardexlote,c.notingfechacaducidad as kardexcaducidad,c.notingconcepto as kardexdescripcion, "
            + "           c.notingtipo as kardextipo,'' as kardexcodigoven1,d.detntipreciounitario as kardexpreciocompra, "
            + "           0 as kardexprecioventa, 0 as kardexvalordescuento,d.detnticantidad as kardexcantidad,c.notingcreateuser as kardexusuario, "
            + "           c.notingcegnum as kardexnumref,10 as kardexregorden "
            + "   from    notingdetnti d,noting c "
            + "   where   c.notingnumero = d.notingnumero "
            + "   and     c.notingcreateuser = d.notingcreateuser "
            + "   and     c.notingcodigodiv = d.notingcodigodiv "
            + "   and     (c.notingcontab = 'SI' or c.notingcontab = 'PO') "
            + "   and     c.notingfecha >= ? "
            + "   and     c.notingfecha <= ? "
            + "   and     d.productoscodigo = ? "
            + " )  "
            + " UNION "
            + " (  "
            + "   select  d.notegrcodigodiv as kardexcodigodiv,d.notegranno as kardexanno,'NTE' as kardextipotrx, "
            + "           d.notegrnumero as kardexnumero,d.detnteline as kardexlinea,1 as kardexorden, "
            + "           c.notegrfecha as kardexfecha, "
            + "           ( select o.productoscodigo "
            + "             from ordpro o "
            + "             where o.ordpronumero = (CASE	WHEN substring(trim(c.notegrnumorden) from 1 for 1) = 'O' "
            + "                                   			THEN to_number(substring(trim(c.notegrnumorden) from 3), '999999999')::int "
            + "                                           ELSE to_number(trim(c.notegrnumorden), '9999999999')::int "
            + "                                     END) "
            + "             limit 1 "
            + "          ) as productotodo, "
            + "          d.productoscodigo as productoscodigo,d.detntebodega1 as tbodcodigo, "
            + "          d.detntebodega2 as tbodcodigo2,c.notegrloteprod as kardexlote,c.notegrfechacaducidad as kardexcaducidad, "
            + "          c.notegrconcepto as kardexdescripcion,c.notegrtipo as kardextipo, "
            + "          '' as kardexcodigoven1,d.detntepreciounitario as kardexpreciocompra, "
            + "          0 as kardexprecioventa, 0 as kardexvalordescuento, "
            + "          d.detntecantidad*-1 as kardexcantidad,c.notegrcreateuser as kardexusuario,"
            + "          CASE	WHEN substring(trim(c.notegrnumorden) from 1 for 1) = 'O' "
            + "                 THEN to_number(substring(trim(c.notegrnumorden) from 3), '999999999')::int "
            + "             	ELSE to_number(trim(c.notegrnumorden), '9999999999')::int "
            + "          END as kardexnumref,40 as kardexregorden "
            + "          from   notegrdetnte d,notegr c "
            + "          where  c.notegrnumero = d.notegrnumero "
            + "          and    c.notegrcreateuser = d.notegrcreateuser "
            + "          and    c.notegrcodigodiv = d.notegrcodigodiv "
            + "          and    (c.notegrcontab = 'SI' or c.notegrcontab = 'PO') "
            + "          and    c.notegrfecha >= ? "
            + "          and    c.notegrfecha <= ? "
            + "          and    d.productoscodigo = ? "
            + " )"
            + " ) as ss order by kardexfecha ";
    ps = getPreparedStatement(codProducto, fechaIni, fechaFin, conP, sDocumentos);
    ps.setDate(4, fechaIni);
    ps.setDate(5, fechaFin);
    ps.setString(6, codProducto.trim().toUpperCase());
    ps.setDate(7, fechaIni);
    ps.setDate(8, fechaFin);
    ps.setString(9, codProducto.trim().toUpperCase());
    rs = ps.executeQuery();

    while (rs.next()) {
      int orden = rs.getInt("kardexregorden");
      String productotodo = rs.getString("productotodo") == null ? "" : rs.getString("productotodo").trim();
      if (rs.getString("kardextipotrx").trim().equals("NTI")
              && rs.getString("kardextipo").trim().equals("PRODUCCION")
              && productotodo.equals(rs.getString("productoscodigo").trim())) {
        orden = 30;
      }
      if (rs.getString("kardextipotrx").trim().equals("NTE")
              && rs.getString("kardextipo").trim().equals("PRODUCCION")
              && productotodo.equals(rs.getString("productoscodigo").trim())) {
        orden = 20;
      }

      k = new Kardex(
              rs.getString("kardexcodigodiv").trim(),
              rs.getString("kardexanno").trim(),
              rs.getString("kardextipotrx").trim(),
              rs.getInt("kardexnumero"),
              rs.getInt("kardexlinea"),
              rs.getString("kardexorden").trim(),
              rs.getDate("kardexfecha"),
              rs.getString("productotodo") == null ? "" : rs.getString("productotodo").trim(),
              rs.getString("productoscodigo").trim(),
              rs.getString("tbodcodigo").trim(),
              rs.getString("tbodcodigo2").trim(),
              rs.getString("kardexlote").trim(),
              rs.getDate("kardexcaducidad"),
              rs.getString("kardexdescripcion").trim(),
              rs.getString("kardextipo").trim(),
              rs.getString("kardexcodigoven1"),
              rs.getBigDecimal("kardexpreciocompra"),
              rs.getBigDecimal("kardexprecioventa"),
              rs.getBigDecimal("kardexvalordescuento"),
              rs.getBigDecimal("kardexcantidad"),
              rs.getString("kardexusuario").trim(),
              rs.getInt("kardexnumref"),
              orden
      );
      aDoc.add(k);
      Collections.sort(aDoc);
    }
    rs.close();
    conP.close();
    return aDoc;
  }

  ArrayList<Movimiento> getMovimientos(Date fechaIni, Date fechaFin) throws SQLException {
    ArrayList<Movimiento> aMov = new ArrayList<>();
    Movimiento m;
    ResultSet rs;
    PreparedStatement ps;
    Connection conP = poolP.getDataSource().getConnection();
    String sMovimientos = " select  productoscodigo,sum(total) total"
            + " from(( "
            + "	select	d.productoscodigo as productoscodigo,count(*) as total "
            + "	from	facturadetfac d,factura c "
            + "	where	c.facturanumero = d.facturanumero "
            + "	and	c.facturacodigodiv = d.facturacodigodiv "
            + "	and	(c.facturacontabilizado = 'PO' or c.facturacontabilizado = 'SI') "
            + "	and	c.facturafecha >= ?  "
            + "	and	c.facturafecha <= ? "
            + "	group	by 1 "
            + " ) "
            + " UNION "
            + " ( 	select	d.productoscodigo as productoscodigo,count(*) as total "
            + "	from	notingdetnti d,noting c "
            + "	where	c.notingnumero = d.notingnumero "
            + "	and	c.notingcreateuser = d.notingcreateuser "
            + "	and	c.notingcodigodiv = d.notingcodigodiv "
            + "	and	(c.notingcontab = 'SI' or c.notingcontab = 'PO') "
            + "	and	c.notingfecha >= ? "
            + "	and	c.notingfecha <= ? "
            + "	group	by 1 "
            + " ) "
            + " UNION "
            + " ( 	select	d.productoscodigo as productoscodigo,count(*) as total "
            + "	from	notegrdetnte d,notegr c "
            + "	where	c.notegrnumero = d.notegrnumero "
            + "	and	c.notegrcreateuser = d.notegrcreateuser "
            + "	and	c.notegrcodigodiv = d.notegrcodigodiv "
            + "	and	(c.notegrcontab = 'SI' or c.notegrcontab = 'PO') "
            + "	and	c.notegrfecha >= ? "
            + "	and	c.notegrfecha <= ? "
            + "	group	by 1 "
            + " ) "
            + " )as ss  group by 1 order by 1 ";
    ps = conP.prepareStatement(sMovimientos);
    ps.setDate(1, fechaIni);
    ps.setDate(2, fechaFin);
    ps.setDate(3, fechaIni);
    ps.setDate(4, fechaFin);
    ps.setDate(5, fechaIni);
    ps.setDate(6, fechaFin);
    rs = ps.executeQuery();
    while (rs.next()) {
      m = new Movimiento();
      m.setProductoscodigo(rs.getString("productoscodigo").trim());
      m.setTotal(rs.getInt("total"));
      aMov.add(m);
    }
    rs.close();
    conP.close();
    return aMov;
  }

  Bodega getSaldoIniSaldosInv(String codigoProducto, String codigoBodega)
          throws SQLException {
    Bodega b = new Bodega();

    PreparedStatement ps;
    Connection conP = poolP.getDataSource().getConnection();
    String sSaldoIniSaldosInv = " select	productoscodigo,saldosiinvbodegadefault,saldosiinvcantidad,saldosiinvcosto "
            + " from	saldosiinv "
            + " where	productoscodigo = ? "
            + " and	saldosiinvbodegadefault = ? "
            + " limit 1 ";
    ps = conP.prepareStatement(sSaldoIniSaldosInv);
    ps.setString(1, codigoProducto.trim().toUpperCase());
    ps.setString(2, codigoBodega.trim());
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      b.setCodigo(rs.getString("saldosiinvbodegadefault").trim());
      b.setCantidad(rs.getBigDecimal("saldosiinvcantidad"));
      b.setCostoUnitario(rs.getBigDecimal("saldosiinvcosto"));
    }
    rs.close();
    conP.close();
    return b;
  }

  Bodega getSaldoMovAnt(String codigoProducto, String codigoBodega, Date fecha) throws SQLException {
    Bodega b = new Bodega();

    ResultSet rs;
    PreparedStatement ps;
    Connection conP = poolP.getDataSource().getConnection();
    String sSaldoMovAnt = " select productoscodigo,tbodcodigo,kardexstock,kardexcostopromedio,kardexcostototalstock "
            + " from	kardex where productoscodigo=? and kardexfecha < ? and tbodcodigo = ? "
            + " order	by kardexfecha desc, kardexcodigosec desc limit 1 ";
    ps = conP.prepareStatement(sSaldoMovAnt);
    ps.setString(1, codigoProducto.trim().toUpperCase());
    ps.setDate(2, fecha);
    ps.setString(3, codigoBodega.trim());
    rs = ps.executeQuery();
    while (rs.next()) {
      b.setCodigo(rs.getString("tbodcodigo").trim());
      b.setCantidad(rs.getBigDecimal("kardexstock"));
      b.setCostoUnitario(rs.getBigDecimal("kardexcostopromedio"));
      b.setCostoTotal(rs.getBigDecimal("kardexcostototalstock"));
    }
    rs.close();
    conP.close();
    return b;
  }

  ArrayList<FactorCosto> getFactores(String codigoProducto, Date fechaIni, Date fechaFin)
          throws SQLException {
    ArrayList<FactorCosto> aFac = new ArrayList<>();

    Connection conP = poolP.getDataSource().getConnection();
    String sFactores = "select o.ordpronumero,p.productoscodigo,p.tscatcodigo,f.tscatmanodeobra,f.tscatggproduccion,f.tscatgas, "
            + "	o.ordpronumpersonas,o.ordprohorasproduccion,o.ordpronumperss,o.ordprohorasprods, "
            + "	o.ordpronumpersa,o.ordprohorasproda,o.ordpronumperst,o.ordprohorasprodt "
            + "          from productos p, tscat f, ordpro o, noting i "
            + "          where p.tscatcodigo = f.tscatcodigo "
            + "          and p.productoscodigo = o.productoscodigo "
            + "          and o.ordpronumero = i.notingcegnum "
            + "          and i.notingcodigodiv = '004' "
            + "          and trim(i.notingtipo) ='PRODUCCION' "
            + "          and i.notingfecha>= ? "
            + "          and i.notingfecha<= ? "
            + "          and o.productoscodigo = ? ";
    PreparedStatement ps = getPreparedStatement(codigoProducto, fechaIni, fechaFin, conP, sFactores);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      FactorCosto ft = new FactorCosto();
      ft.setOrdenNumero(rs.getInt("ordpronumero"));
      ft.setCodigoProducto(rs.getString("productoscodigo").trim());
      ft.setCategoria(rs.getString("tscatcodigo").trim());
      ft.setFactorMod(rs.getBigDecimal("tscatmanodeobra"));
      ft.setFactorCif(rs.getBigDecimal("tscatggproduccion"));
      ft.setFactorGas(rs.getBigDecimal("tscatgas"));
      ft.setPersonasRot(rs.getInt("ordpronumpersonas"));
      ft.setHorasRot(rs.getBigDecimal("ordprohorasproduccion"));
      ft.setPersonasSol(rs.getInt("ordpronumperss"));
      ft.setHorasSol(rs.getBigDecimal("ordprohorasprods"));
      ft.setPersonasAca(rs.getInt("ordpronumpersa"));
      ft.setHorasAca(rs.getBigDecimal("ordprohorasproda"));
      ft.setPersonasTal(rs.getInt("ordpronumperst"));
      ft.setHorasTal(rs.getBigDecimal("ordprohorasprodt"));

      aFac.add(ft);
    }
    rs.close();
    conP.close();
    return aFac;
  }

  ArrayList<Materiales> getMateriales(String codigoProducto) throws SQLException {

    ArrayList<Materiales> aMats = new ArrayList<>();

    ResultSet rs;
    PreparedStatement ps;
    Connection conP = poolP.getDataSource().getConnection();
    String sMaterialesProduccion = " select o.ordpronumero,o.productoscodigo as productoc,d.productoscodigo as productod, "
            + "	p.tcatcodigo,d.detntecantidad,d.detntepreciounitario, "
            + "	c.notegrnumero,c.notegrcreateuser,c.notegrcodigodiv "
            + " from notegr c, notegrdetnte d, productos p, ordpro o\n"
            + " where c.notegrcodigodiv = '004' "
            + " and c.notegrcodigodiv = d.notegrcodigodiv "
            + " and c.notegrtipo = 'PRODUCCION' "
            + " and c.notegrnumero = d.notegrnumero "
            + " and c.notegrcreateuser = d.notegrcreateuser "
            + " and p.productoscodigo = d.productoscodigo "
            + " and o.ordpronumero = CASE	WHEN substring(trim(c.notegrnumorden) from 1 for 1) = 'O' THEN to_number(substring(trim(c.notegrnumorden) from 3), '999999999')::int "
            + "				ELSE to_number(trim(c.notegrnumorden), '9999999999')::int "
            + "		     END "
            + " and o.productoscodigo = ? "
            + " order by o.ordpronumero ";
    ps = conP.prepareStatement(sMaterialesProduccion);
    ps.setString(1, codigoProducto);
    rs = ps.executeQuery();
    while (rs.next()) {
      Materiales mt = new Materiales();
      mt.setOrdenNumero(rs.getInt("ordpronumero"));
      mt.setProductoC(rs.getString("productoc").trim());
      mt.setProductoscodigo(rs.getString("productod").trim());
      mt.setTcatcodigo(rs.getString("tcatcodigo").trim());
      mt.setDetntecantidad(rs.getBigDecimal("detntecantidad"));
      mt.setDetntepreciounitario(rs.getBigDecimal("detntepreciounitario"));
      mt.setNotegrnumero(rs.getInt("notegrnumero"));
      mt.setNotegrcreateuser(rs.getString("notegrcreateuser").trim());
      mt.setNotegrcodigodiv(rs.getString("notegrcodigodiv").trim());
      aMats.add(mt);
    }
    rs.close();
    conP.close();
    return aMats;
  }

  ArrayList<Usuarios> getUsuariosSIP() throws SQLException {
    ArrayList<Usuarios> aUsers = new ArrayList<>();
    PreparedStatement ps;
    Connection conM = poolM.getDataSource().getConnection();
    String sUsersSIP = "select "
            + "  id, "
            + "  usuario "
            + "from usuarios ";
    ps = conM.prepareStatement(sUsersSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      Usuarios u = new Usuarios();
      u.setId(rs.getInt("id"));
      u.setUsuario(rs.getString("usuario").trim().toUpperCase());

      aUsers.add(u);
    }

    rs.close();
    conM.close();
    return aUsers;
  }

  ArrayList<Division> getDivisionesSIP() throws SQLException {
    ArrayList<Division> lista = new ArrayList<>();
    PreparedStatement ps;
    Connection conM = poolM.getDataSource().getConnection();
    String sDivisionSIP = "select "
            + "  id, "
            + "  idmarketsoft, "
            + "  descripcion,\n"
            + "  status\n"
            + "from divisiones ";
    ps = conM.prepareStatement(sDivisionSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      Division d = new Division();
      d.setId(rs.getInt("id"));
      d.setIdMarketsoft(rs.getString("idmarketsoft"));

      lista.add(d);
    }

    rs.close();
    conM.close();
    return lista;
  }

  ArrayList<MovimientoInv> getTiposMovSIP() throws SQLException {
    ArrayList<MovimientoInv> lista = new ArrayList<>();
    PreparedStatement ps;
    Connection conM = poolM.getDataSource().getConnection();
    String sTipoMovimientosSIP = "select "
            + "  id, "
            + "  descripcion, "
            + "  documento, "
            + "  status "
            + "from tiposmovimientosinv "
            + " where status = 'A' ";
    ps = conM.prepareStatement(sTipoMovimientosSIP);
    ResultSet rs = ps.executeQuery();

    while (rs.next()) {
      MovimientoInv m = new MovimientoInv();
      m.setId(rs.getInt("id"));
      m.setDescripcion(rs.getString("descripcion"));
      m.setDocumento(rs.getString("documento"));
      lista.add(m);
    }

    rs.close();
    conM.close();
    return lista;
  }

  int saveChanges(ArrayList<Kardex> aKardex, Date fechaIni, Date fechaFin)
          throws SQLException {
    int rAfectados = 0;
    int rEliminados;
    int maxKardexSec = 0;
    int cNoting = 0;
    int cNotIngdet = 0;
    int cNotIngdetSip = 0;
    int cNotEgr;
    int cNotegrdet = 0;
    int cNotegrdetSip = 0;

    Date fechaCerrado = new Date((2015 - 1900), 8, 30);
    PreparedStatement ps;
    Connection conP = poolP.getDataSource().getConnection();
    conP.setAutoCommit(false);

    Connection conM = poolM.getDataSource().getConnection();
    conM.setAutoCommit(false);
    try {

      //Se eliminan datos de kardex
      String dKardex = " delete from kardex where kardexfecha>= ? and kardexfecha<= ? ";
      ps = conP.prepareStatement(dKardex);
      ps.setDate(1, fechaIni);
      ps.setDate(2, fechaFin);
      rEliminados = ps.executeUpdate();

      //Se obtiene el registro maximo de kardexcodigo sec;
      String sMaxKardexCodigo = " select max(kardexcodigosec) as maxreg from kardex ";
      ps = conP.prepareStatement(sMaxKardexCodigo);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        maxKardexSec = rs.getInt("maxreg");
      }
      rs.close();

      //Se ingresan datos al kardex;
      maxKardexSec++;
      for (Kardex k : aKardex) {
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);

        String iKardex = " INSERT INTO public.kardex( "
                + "            kardexcodigodiv, kardexanno, kardextipotrx, kardexnumero, kardexlinea, "
                + "            kardexcodigosec, kardexorden, kardexfecha, kardexhora, productoscodigo, "
                + "            tbodcodigo, kardexlote, kardexcaducidad, kardexdescripcion, kardextipo, "
                + "            kardexcodigoven1, kardexpreciocompra, kardexprecioventa, kardexvalordescuento, "
                + "            kardexcantidad, kardexcostopromedio, kardexcostototal, kardexstock, "
                + "            kardexcantidad_a, kardexcostopromedio_a, kardexcostototalstock, "
                + "            kardexcreateuser, kardexcreatedate, kardexcreatepgm, kardexupdateuser, "
                + "            kardexupdatedate, kardexupdatetime, kardexupdatepgm, kardexusuario) "
                + "    VALUES (?, ?, ?, ?, ?, "
                + "            ?, ?, ?, ?, ?, "
                + "            ?, ?, ?, ?, ?, "
                + "            ?, ?, ?, ?, "
                + "            ?, ?, ?, ?, "
                + "            ?, ?, ?, "
                + "            ?, CURRENT_DATE, ?, ?, "
                + "            CURRENT_DATE, ?, ?, ?) ";

        ps = conP.prepareStatement(iKardex);
        ps.setString(1, k.getKardexcodigodiv().trim());
        ps.setString(2, k.getKardexanno().trim());
        ps.setString(3, k.getKardextipotrx().trim());
        ps.setInt(4, k.getKardexnumero());
        ps.setInt(5, k.getKardexlinea());
        ps.setInt(6, maxKardexSec);
        ps.setString(7, k.getKardexorden().trim());
        ps.setDate(8, k.getKardexfecha());
        ps.setString(9, (hora + ":" + minutos + ":" + segundos));
        ps.setString(10, k.getProductoscodigo().trim());
        ps.setString(11, k.getTbodcodigo().trim());
        ps.setString(12, k.getKardexlote().trim());
        ps.setDate(13, k.getKardexcaducidad());
        ps.setString(14, k.getKardexdescripcion().trim());
        ps.setString(15, k.getKardextipo().trim());
        ps.setString(16, k.getKardexcodigoven1());
        ps.setBigDecimal(17, k.getKardexpreciocompra());
        ps.setBigDecimal(18, k.getKardexprecioventa());
        ps.setBigDecimal(19, k.getKardexvalordescuento());
        ps.setBigDecimal(20, k.getKardexcantidad());
        ps.setBigDecimal(21, k.getKardexcostopromedio());
        ps.setBigDecimal(22, k.getKardexcostototal());
        ps.setBigDecimal(23, k.getKardexstock());
        ps.setBigDecimal(24, k.getKardexcantidad_a());
        ps.setBigDecimal(25, k.getKardexcostopromedio_a());
        ps.setBigDecimal(26, k.getKardexcostototalstock());
        ps.setString(27, "JTARIRA");
        ps.setString(28, "TKardex_BC");
        ps.setString(29, "JTARIRA");
        ps.setString(30, (hora + ":" + minutos + ":" + segundos));
        ps.setString(31, "TKardex_BC");
        ps.setString(32, k.getKardexusuario().trim());
        maxKardexSec++;
        rAfectados += ps.executeUpdate();

        if (k.getKardextipotrx().trim().equals("NTI")) {
          if (k.getKardextipo().trim().equals("PRODUCCION") && k.getKardexfecha().compareTo(fechaCerrado) > 0) {
            String uNotIng = " update noting "
                    + " set notingpreciototal = ? "
                    + " where trim(notingcodigodiv) = ? "
                    + " and  notingnumero = ? "
                    + " and trim(notingcreateuser) = ? "
                    + " and trim(notingtipo)= ? ";
            ps = conP.prepareStatement(uNotIng);
            ps.setBigDecimal(1, k.getKardexcostototal());
            ps.setString(2, k.getKardexcodigodiv().trim());
            ps.setInt(3, k.getKardexnumero());
            ps.setString(4, k.getKardexusuario().trim());
            ps.setString(5, k.getKardextipo().trim());
            cNoting += ps.executeUpdate();

            String uNotIngDet = " update notingdetnti "
                    + " set detntipreciounitario = ?, detntipreciototal = ? "
                    + " where notingnumero= ? "
                    + "  and trim(notingcreateuser)= ? "
                    + " and trim(notingcodigodiv)= ?"
                    + " and trim(productoscodigo)  = ? ";

            ps = getPreparedStatement(conP, k, uNotIngDet);
            ps.setString(4, k.getKardexusuario().trim());
            ps.setString(5, k.getKardexcodigodiv().trim());
            ps.setString(6, k.getProductoscodigo().trim());
            cNotIngdet += ps.executeUpdate();

            String uIngresInvDetalleSip = "update ingresoinvdetalle "
                    + "set costoUnitario = ?, "
                    + "  costoTotal = ? "
                    + "where idIngresoInv = ? "
                    + "and idUsuario = ? "
                    + "and idDivision = ? "
                    + "and codigoProducto = ? "
                    + "      and cantidad = ? "
                    + "and status = 'A'";

            ps = getPreparedStatement(conM, k, uIngresInvDetalleSip);
            ps.setInt(4, k.getIdUsuario());
            ps.setInt(5, k.getIdDivision());
            ps.setString(6, k.getProductoscodigo().trim());
            ps.setBigDecimal(7, k.getKardexcantidad());
            cNotIngdetSip += ps.executeUpdate();
          }
        }
        if (k.getKardextipotrx().trim().equals("NTE")) {
          String uNotEgrDet = " UPDATE notegrdetnte "
                  + "SET detntepreciounitario = ?, detntepreciototal = ?, detntek2cost = ? "
                  + "WHERE notegrnumero = ? "
                  + "      AND trim(notegrcreateuser) =? "
                  + "      AND trim(notegrcodigodiv) =? "
                  + "      AND trim(productoscodigo) = ? "
                  + "      AND detnteline = ? ";
          ps = conP.prepareStatement(uNotEgrDet);
          ps.setBigDecimal(1, k.getKardexpreciocompra());
          ps.setBigDecimal(2, k.getKardexcostototal());
          ps.setBigDecimal(3, k.getKardexpreciocompra());
          ps.setInt(4, k.getKardexnumero());
          ps.setString(5, k.getKardexusuario());
          ps.setString(6, k.getKardexcodigodiv().trim());
          ps.setString(7, k.getProductoscodigo().trim());
          ps.setInt(8, k.getKardexlinea());
          cNotegrdet += ps.executeUpdate();

          String uEgresoInvDetalleSip = "update egresoinvdetalle "
                  + "set costoUnitario = ?, "
                  + "  costoTotal      = ? "
                  + "where idEgresoInv = ? "
                  + "      and idUsuario = ? "
                  + "      and idDivision = ? "
                  + "      and codigoProducto = ?"
                  + "      and cantidad = ? "
                  + "      and status = 'A' ";

          ps = getPreparedStatement(conM, k, uEgresoInvDetalleSip);
          ps.setInt(4, k.getIdUsuario());
          ps.setInt(5, k.getIdDivision());
          ps.setString(6, k.getProductoscodigo().trim());
          ps.setBigDecimal(7, k.getKardexcantidad().negate());
          cNotegrdetSip += ps.executeUpdate();
        }
      }

      String uNotEgrs = "UPDATE notegr c "
              + "SET notegrpreciototal = (SELECT CASE "
              + "                                WHEN count(*) = 0 "
              + "                                  THEN 0.00 "
              + "                                ELSE sum(d.detntepreciototal) "
              + "                                END "
              + "                         FROM notegrdetnte d "
              + "                         WHERE c.notegrnumero = d.notegrnumero "
              + "                               AND trim(c.notegrcreateuser) = trim(d.notegrcreateuser) "
              + "                               AND trim(c.notegrcodigodiv) = trim(d.notegrcodigodiv)) "
              + "WHERE c.notegrfecha >= ? "
              + "      AND c.notegrfecha <= ? "
              + "      AND (c.notegrcontab = 'SI' OR c.notegrcontab = 'PO')";
      
      ps = conP.prepareStatement(uNotEgrs);
      ps.setDate(1, fechaIni);
      ps.setDate(2, fechaFin);
      cNotEgr = ps.executeUpdate();

      System.out.println("Se eliminaron " + rEliminados + " registros del kardex.\n"
              + "Se ingresaron " + rAfectados + " registros al Kardex.\n"
              + "Se actualizaron " + cNoting + " registros de notas de ingreso por produccion.\n"
              + "Se actualizaron " + cNotIngdet + " registros del detalle de notas de ingreso por produccion.\n"
              + "Se actualizaron " + cNotEgr + " notas de egreso .\n"
              + "Se actualizaron " + cNotegrdet + " registros del detalle de notas de egreso .\n"
              + "Se actualizaron " + cNotIngdetSip + " registros del detalle de notas de ingreso por produccion SIP.\n"
              + "Se actualizaron " + cNotegrdetSip + " registros del detalle de notas de Egreso  SIP.\n");

      conP.commit();
      conP.close();

      conM.commit();
      conM.close();

      return rAfectados;

    } catch (SQLException ex) {
      conP.rollback();
      conP.close();

      conM.rollback();
      conM.close();

      ex.printStackTrace();
      return -1;
    }
  }

  private PreparedStatement getPreparedStatement(String codProducto, Date fechaIni, Date fechaFin, Connection conP,
          String sDocumentos) throws SQLException {
    PreparedStatement ps;
    ps = conP.prepareStatement(sDocumentos);
    ps.setDate(1, fechaIni);
    ps.setDate(2, fechaFin);
    ps.setString(3, codProducto.trim().toUpperCase());
    return ps;
  }

  private PreparedStatement getPreparedStatement(Connection conP, Kardex k, String uNotIngDet) throws SQLException {
    PreparedStatement ps;
    ps = conP.prepareStatement(uNotIngDet);
    ps.setBigDecimal(1, k.getKardexpreciocompra());
    ps.setBigDecimal(2, k.getKardexcostototal());
    ps.setInt(3, k.getKardexnumero());
    return ps;
  }
}
