package RegrabadoKardex;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;

public class DatosDAO {

  private ConexionPostgres conP;

  private String sDocumentos = " select     kardexcodigodiv,kardexanno,kardextipotrx,kardexnumero,kardexlinea,kardexorden, "
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

  private String sMovimientos = " select  productoscodigo,sum(total) total\n"
          + " from(( \n"
          + "	select	d.productoscodigo as productoscodigo,count(*) as total\n"
          + "	from	facturadetfac d,factura c \n"
          + "	where	c.facturanumero = d.facturanumero \n"
          + "	and	c.facturacodigodiv = d.facturacodigodiv \n"
          + "	and	(c.facturacontabilizado = 'PO' or c.facturacontabilizado = 'SI')\n"
          + "	and	c.facturafecha >= ? \n"
          + "	and	c.facturafecha <= ? \n"
          + "	group	by 1\n"
          + " ) \n"
          + " UNION\n"
          + " ( 	select	d.productoscodigo as productoscodigo,count(*) as total\n"
          + "	from	notingdetnti d,noting c \n"
          + "	where	c.notingnumero = d.notingnumero \n"
          + "	and	c.notingcreateuser = d.notingcreateuser \n"
          + "	and	c.notingcodigodiv = d.notingcodigodiv \n"
          + "	and	(c.notingcontab = 'SI' or c.notingcontab = 'PO') \n"
          + "	and	c.notingfecha >= ? \n"
          + "	and	c.notingfecha <= ?\n"
          + "	group	by 1\n"
          + " )  \n"
          + " UNION\n"
          + " ( 	select	d.productoscodigo as productoscodigo,count(*) as total\n"
          + "	from	notegrdetnte d,notegr c \n"
          + "	where	c.notegrnumero = d.notegrnumero \n"
          + "	and	c.notegrcreateuser = d.notegrcreateuser \n"
          + "	and	c.notegrcodigodiv = d.notegrcodigodiv                                                                                                                           \n"
          + "	and	(c.notegrcontab = 'SI' or c.notegrcontab = 'PO') \n"
          + "	and	c.notegrfecha >= ? \n"
          + "	and	c.notegrfecha <= ? \n"
          + "	group	by 1\n "
          + " )\n"
          + " )as ss  group by 1 order by 1 ";

  private String sSaldoIniSaldosInv = " select	productoscodigo,saldosiinvbodegadefault,saldosiinvcantidad,saldosiinvcosto "
          + " from	saldosiinv "
          + " where	productoscodigo = ? "
          + " and	saldosiinvbodegadefault = ? "
          + " limit 1 ";

  private String sSaldoMovAnt = " select	productoscodigo,tbodcodigo,kardexstock,kardexcostopromedio,kardexcostototalstock "
          + " from	kardex where productoscodigo=? and kardexfecha < ? and tbodcodigo = ? "
          + " order	by kardexfecha desc, kardexcodigosec desc limit 1 ";

  private String sFactores = "select o.ordpronumero,p.productoscodigo,p.tscatcodigo,f.tscatmanodeobra,f.tscatggproduccion,f.tscatgas, "
          + "	o.ordpronumpersonas,o.ordprohorasproduccion,o.ordpronumperss,o.ordprohorasprods, "
          + "	o.ordpronumpersa,o.ordprohorasproda,o.ordpronumperst,o.ordprohorasprodt "
          + "          from productos p, tscat f, ordpro o, noting i "
          + "          where p.tscatcodigo = f.tscatcodigo "
          + "          and p.productoscodigo = o.productoscodigo "
          + "          and o.ordpronumero = i.notingcegnum\n"
          + "          and i.notingcodigodiv = '004' "
          + "          and trim(i.notingtipo) ='PRODUCCION' "
          + "          and i.notingfecha>= ? "
          + "          and i.notingfecha<= ? "
          + "          and o.productoscodigo = ? ";

  private String sMaterialesProduccion = " select o.ordpronumero,o.productoscodigo as productoc,d.productoscodigo as productod, "
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

  public String sMaxKardexCodigo = " select max(kardexcodigosec) as maxreg from kardex ";

  public String dKardex = " delete from kardex where kardexfecha>= ? and kardexfecha<= ? ";

  public String iKardex = " INSERT INTO public.kardex( "
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

  public String uNotIng = " update noting "
          + " set notingpreciototal = ? "
          + " where trim(notingcodigodiv) = ? "
          + " and  notingnumero = ? "
          + " and trim(notingcreateuser) = ? "
          + " and trim(notingtipo)= ? ";

  public String uNotIngDet = " update notingdetnti "
          + " set detntipreciounitario = ?, detntipreciototal = ? "
          + " where notingnumero= ? AND trim(notingcreateuser)= ? and trim(notingcodigodiv)= ? and trim(productoscodigo)  = ? ";

  public String uNotEgrDet = " update notegrdetnte "
          + " set detntepreciounitario = ? , detntepreciototal = ? , detntek2cost = ? "
          + " where notegrnumero= ? AND trim(notegrcreateuser)=? and trim(notegrcodigodiv)=? and trim(productoscodigo)  = ? ";

  public DatosDAO() {
    conP = new ConexionPostgres();
  }

  public ArrayList getDocumentos(String codProducto, Date fechaIni, Date fechaFin) throws ClassNotFoundException, SQLException {
    ArrayList<Kardex> aDoc = new ArrayList<>();
    Kardex k;
    ResultSet rs;
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sDocumentos);
    ps.setDate(1, fechaIni);
    ps.setDate(2, fechaFin);
    ps.setString(3, codProducto.trim().toUpperCase());
    ps.setDate(4, fechaIni);
    ps.setDate(5, fechaFin);
    ps.setString(6, codProducto.trim().toUpperCase());
    ps.setDate(7, fechaIni);
    ps.setDate(8, fechaFin);
    ps.setString(9, codProducto.trim().toUpperCase());

    rs = ps.executeQuery();

    while (rs.next()) {
      int orden = rs.getInt("kardexregorden");
      String productotodo = rs.getString("productotodo") == null ? "" : rs.getString("productotodo").trim() ;
      if (rs.getString("kardextipotrx").trim().equals("NTI") && rs.getString("kardextipo").trim().equals("PRODUCCION") && productotodo.equals(rs.getString("productoscodigo").trim())) {
        orden = 30;
      }
      if (rs.getString("kardextipotrx").trim().equals("NTE") && rs.getString("kardextipo").trim().equals("PRODUCCION") && productotodo.equals(rs.getString("productoscodigo").trim())) {
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
              rs.getDouble("kardexpreciocompra"),
              rs.getDouble("kardexprecioventa"),
              rs.getDouble("kardexvalordescuento"),
              rs.getDouble("kardexcantidad"),
              rs.getString("kardexusuario").trim(),
              rs.getInt("kardexnumref"),
              orden
      );
      aDoc.add(k);
      Collections.sort(aDoc);
    }
    rs.close();
    conP.cerrar();
    return aDoc;
  }

  public ArrayList getMovimientos(Date fechaIni, Date fechaFin) throws ClassNotFoundException, SQLException {
    ArrayList<Movimiento> aMov = new ArrayList<>();
    Movimiento m;
    ResultSet rs;
    PreparedStatement ps;
    conP.conectar();
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
    conP.cerrar();
    return aMov;
  }

  public Bodega getSaldoIniSaldosInv(String codigoProducto, String codigoBodega) throws ClassNotFoundException, SQLException {
    Bodega b = new Bodega();

    ResultSet rs;
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sSaldoIniSaldosInv);
    ps.setString(1, codigoProducto.trim().toUpperCase());
    ps.setString(2, codigoBodega.trim());
    rs = ps.executeQuery();
    while (rs.next()) {
      b.setCodigo(rs.getString("saldosiinvbodegadefault").trim());
      b.setCantidad(rs.getDouble("saldosiinvcantidad"));
      b.setCostoUnitario(rs.getDouble("saldosiinvcosto"));
    }

    rs.close();

    conP.cerrar();
    return b;
  }

  public Bodega getSaldoMovAnt(String codigoProducto, String codigoBodega, Date fecha) throws ClassNotFoundException, SQLException {
    Bodega b = new Bodega();

    ResultSet rs;
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sSaldoMovAnt);
    ps.setString(1, codigoProducto.trim().toUpperCase());
    ps.setDate(2, fecha);
    ps.setString(3, codigoBodega.trim());
    rs = ps.executeQuery();
    while (rs.next()) {
      b.setCodigo(rs.getString("tbodcodigo").trim());
      b.setCantidad(rs.getDouble("kardexstock"));
      b.setCostoUnitario(rs.getDouble("kardexcostopromedio"));
      b.setCostoTotal(rs.getDouble("kardexcostototalstock"));
    }
    rs.close();

    conP.cerrar();
    return b;
  }

  public ArrayList getFactores(String codigoProducto, Date fechaIni, Date fechaFin) throws ClassNotFoundException, SQLException {
    ArrayList<FactorCosto> aFac = new ArrayList<>();

    ResultSet rs;
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sFactores);
    ps.setDate(1, fechaIni);
    ps.setDate(2, fechaFin);
    ps.setString(3, codigoProducto.trim().toUpperCase());
    rs = ps.executeQuery();
    while (rs.next()) {
      FactorCosto ft = new FactorCosto();
      ft.setOrdenNumero(rs.getInt("ordpronumero"));
      ft.setCodigoProducto(rs.getString("productoscodigo").trim());
      ft.setCategoria(rs.getString("tscatcodigo").trim());
      ft.setFactorMod(rs.getDouble("tscatmanodeobra"));
      ft.setFactorCif(rs.getDouble("tscatggproduccion"));
      ft.setFactorGas(rs.getDouble("tscatgas"));
      ft.setPersonasRot(rs.getInt("ordpronumpersonas"));
      ft.setHorasRot(rs.getDouble("ordprohorasproduccion"));
      ft.setPersonasSol(rs.getInt("ordpronumperss"));
      ft.setHorasSol(rs.getDouble("ordprohorasprods"));
      ft.setPersonasAca(rs.getInt("ordpronumpersa"));
      ft.setHorasAca(rs.getDouble("ordprohorasproda"));
      ft.setPersonasTal(rs.getInt("ordpronumperst"));
      ft.setHorasTal(rs.getDouble("ordprohorasprodt"));

      aFac.add(ft);
    }
    rs.close();
    conP.cerrar();
    return aFac;
  }

  public ArrayList getMateriales(String codigoProducto, Date fechaIni, Date fechaFin) throws ClassNotFoundException, SQLException {

    ArrayList<Materiales> aMats = new ArrayList<>();

    ResultSet rs;
    PreparedStatement ps;
    conP.conectar();
    ps = conP.prepareStatement(sMaterialesProduccion);
    ps.setString(1, codigoProducto);
    rs = ps.executeQuery();
    while (rs.next()) {
      Materiales mt = new Materiales();
      mt.setOrdenNumero(rs.getInt("ordpronumero"));
      mt.setProductoC(rs.getString("productoc").trim());
      mt.setProductoscodigo(rs.getString("productod").trim());
      mt.setTcatcodigo(rs.getString("tcatcodigo").trim());
      mt.setDetntecantidad(rs.getDouble("detntecantidad"));
      mt.setDetntepreciounitario(rs.getDouble("detntepreciounitario"));
      mt.setNotegrnumero(rs.getInt("notegrnumero"));
      mt.setNotegrcreateuser(rs.getString("notegrcreateuser").trim());
      mt.setNotegrcodigodiv(rs.getString("notegrcodigodiv").trim());
      aMats.add(mt);
    }
    rs.close();

    conP.cerrar();
    return aMats;
  }

  public int saveChanges(ArrayList<Kardex> aKardex, Date fechaIni, Date fechaFin) throws SQLException, ClassNotFoundException {
    int rAfectados = 0;
    int rEliminados = 0;
    int maxKardexSec = 0;
    int cNoting = 0;
    int cNotIngdet = 0;
    int cNotEgr = 0;
    int cNotegrdet = 0;
    Date fechaCerrado = new Date((2015 - 1900), 8, 30);
    PreparedStatement ps;
    try {
      conP.conectar();
      conP.autoCommit(false);

      //Se eliminan datos de kardex
      ps = conP.prepareStatement(dKardex);
      ps.setDate(1, fechaIni);
      ps.setDate(2, fechaFin);
      rEliminados = ps.executeUpdate();

      //Se obtiene el registro maximo de kardexcodigo sec;
      ps = conP.prepareStatement(sMaxKardexCodigo);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        maxKardexSec = rs.getInt("maxreg");
      }

      //Se ingresan datos al kardex;
      maxKardexSec++;
      for (Kardex k : aKardex) {
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;
        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);

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
        ps.setDouble(17, k.getKardexpreciocompra());
        ps.setDouble(18, k.getKardexprecioventa());
        ps.setDouble(19, k.getKardexvalordescuento());
        ps.setDouble(20, k.getKardexcantidad());
        ps.setDouble(21, k.getKardexcostopromedio());
        ps.setDouble(22, k.getKardexcostototal());
        ps.setDouble(23, k.getKardexstock());
        ps.setDouble(24, k.getKardexcantidad_a());
        ps.setDouble(25, k.getKardexcostopromedio_a());
        ps.setDouble(26, k.getKardexcostototalstock());
        ps.setString(27, "JTARIRA");
        ps.setString(28, "TKardex_BC");
        ps.setString(29, "JTARIRA");
        ps.setString(30, (hora + ":" + minutos + ":" + segundos));
        ps.setString(31, "TKardex_BC");
        ps.setString(32, k.getKardexusuario().trim());
        maxKardexSec++;
        rAfectados += ps.executeUpdate();

        if (k.getKardextipotrx().trim().equals("NTI")) {
          if (k.getKardextipo().trim().equals("PRODUCCION")  && k.getKardexfecha().compareTo(fechaCerrado) > 0 ) {
            ps = conP.prepareStatement(uNotIng);
            ps.setDouble(1, k.getKardexcostototal());
            ps.setString(2, k.getKardexcodigodiv().trim());
            ps.setInt(3, k.getKardexnumero());
            ps.setString(4, k.getKardexusuario().trim());
            ps.setString(5, k.getKardextipo().trim());
            cNoting += ps.executeUpdate();

            ps = conP.prepareStatement(uNotIngDet);
            ps.setDouble(1, k.getKardexpreciocompra());
            ps.setDouble(2, k.getKardexcostototal());
            ps.setInt(3, k.getKardexnumero());
            ps.setString(4, k.getKardexusuario().trim());
            ps.setString(5, k.getKardexcodigodiv().trim());
            ps.setString(6, k.getProductoscodigo().trim());
            int con = ps.executeUpdate();
            if(con>1){
              System.out.println("Se actualizaron dos registros de "+ k.getKardexnumero()+" "+k.getKardexusuario());
            }
            cNotIngdet += con;
          }
        }
        if (k.getKardextipotrx().trim().equals("NTE")) {
          ps = conP.prepareStatement(uNotEgrDet);
          ps.setDouble(1, k.getKardexpreciocompra());
          ps.setDouble(2, k.getKardexcostototal());
          ps.setDouble(3, k.getKardexpreciocompra());
          ps.setInt(4, k.getKardexnumero());
          ps.setString(5, k.getKardexusuario());
          ps.setString(6, k.getKardexcodigodiv().trim());
          ps.setString(7, k.getProductoscodigo().trim());
          cNotegrdet += ps.executeUpdate();
        }
      }

      System.out.println("Se eliminaron " + rEliminados + " registros del kardex.\n"
              + "Se ingresaron " + rAfectados + " registros al Kardex.\n"
              + "Se actualizaron " + cNoting + " registros de notas de ingreso por produccion.\n"
              + "Se actualizaron " + cNotIngdet + " registros del detalle de notas de ingreso por produccion.\n"
              + "Se actualizaron " + cNotegrdet + " registros del detalle de notas de egreso .\n");

      conP.Commit();
      conP.cerrar();

    } catch (SQLException ex) {
      conP.Rollback();
      conP.cerrar();
      ex.printStackTrace();
      rAfectados = -1;
    }

    return rAfectados;

  }

}
