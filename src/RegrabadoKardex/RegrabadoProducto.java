package RegrabadoKardex;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegrabadoProducto implements Runnable {

  private DatosDAO db;

  private ArrayList<FactorCosto> aFac = null;
  private ArrayList<Materiales> aMats = null;
  private Movimiento m;
  private Date fechaIni;
  private Date fechaFin;
  private ArrayList<Kardex> listaKardex;

  public RegrabadoProducto(Movimiento mov, Date fechaI, Date fechaF) {
    db = new DatosDAO();
    this.m = mov;
    this.fechaIni = fechaI;
    this.fechaFin = fechaF;
  }

  public ArrayList regrabadoProducto() throws ClassNotFoundException, SQLException {
    ArrayList<Kardex> aDocs = null;

    ArrayList<Kardex> kRegrabado = new ArrayList<>();
    ArrayList<Bodega> aSaldosIni = new ArrayList<>();

    Date fechaCerrado = new Date((2015 - 1900), 8, 30);
    aFac = db.getFactores(m.getProductoscodigo(), fechaIni, fechaFin);
    aDocs = db.getDocumentos(m.getProductoscodigo(), fechaIni, fechaFin);
    aMats = db.getMateriales(m.getProductoscodigo(), fechaIni, fechaFin);

    int i = 0;
    for (Kardex k : aDocs) {

//    Comprueba saldo inicial
      int existe = 0;
      Double saldo = 0.00;
      Double costoU = 0.00;
      Double costoT = 0.00;
      for (Bodega b : aSaldosIni) {
        if (b.getCodigo().equals(k.getTbodcodigo().trim())) {
          existe = 1;
          saldo = b.getCantidad();
          costoU = b.getCostoUnitario();
          costoT = b.getCostoTotal();
        }
      }
      if (existe == 0) {
        Bodega saldoIniSearch = new Bodega(k.getTbodcodigo(), fechaIni);
        Bodega saldoIniBod = getSaldoInicial(saldoIniSearch, m.getProductoscodigo());
        aSaldosIni.add(saldoIniBod);
        saldo = saldoIniBod.getCantidad();
        costoU = saldoIniBod.getCostoUnitario();
        costoT = saldoIniBod.getCostoTotal();
      }

//*******************
//       Calcula Costos
      if (k.getKardextipotrx().trim().equals("NTE")) {

        k.setKardexstock(saldo + k.getKardexcantidad());
        k.setKardexcostopromedio(costoU);
        if (k.getKardexstock() <= 0) {
          k.setKardexcostototalstock(0 * k.getKardexcostopromedio());
        } else {
          k.setKardexcostototalstock(k.getKardexstock() * k.getKardexcostopromedio());
        }
        k.setKardexpreciocompra(k.getKardexcostopromedio());
        k.setKardexcostototal((k.getKardexcantidad() * -1) * costoU);
        k.setKardexcantidad_a(saldo);
        k.setKardexcostopromedio_a(costoU);
        saldo = k.getKardexstock();
        costoU = k.getKardexcostopromedio();
        costoT = k.getKardexcostototalstock();
        kRegrabado.add(k);

        for (Bodega b : aSaldosIni) {
          if (b.getCodigo().equals(k.getTbodcodigo().trim())) {
            b.setCantidad(saldo);
            b.setCostoUnitario(costoU);
            b.setCostoTotal(costoT);
          }
        }

        if (k.getKardextipo().trim().equals("TRANSFERENCIA")) {
          Kardex kt = new Kardex(
                  k.getKardexcodigodiv(),
                  k.getKardexanno(),
                  "NTI",
                  k.getKardexnumero(),
                  k.getKardexlinea(),
                  k.getKardexorden(),
                  k.getKardexfecha(),
                  k.getProductotodo(),
                  k.getProductoscodigo(),
                  k.getTbodcodigo2(),
                  k.getTbodcodigo2(),
                  k.getKardexlote(),
                  k.getKardexcaducidad(),
                  k.getKardexdescripcion(),
                  k.getKardextipo(),
                  k.getKardexcodigoven1(),
                  k.getKardexcostopromedio(),
                  k.getKardexprecioventa(),
                  k.getKardexvalordescuento(),
                  k.getKardexcantidad() * -1,
                  k.getKardexusuario(),
                  k.getKardexnumref(),
                  k.getKardexregorden()
          );

          int existeSaldoBod = 0;
          Double saldoT = 0.00;
          Double costoUt = 0.00;
          Double costoTt = 0.00;
          for (Bodega b : aSaldosIni) {
            if (b.getCodigo().equals(kt.getTbodcodigo().trim())) {
              existeSaldoBod = 1;
              saldoT = b.getCantidad();
              costoUt = b.getCostoUnitario();
              costoTt = b.getCostoTotal();
            }
          }
          if (existeSaldoBod == 0) {
            Bodega saldoIniSearch = new Bodega(kt.getTbodcodigo(), fechaIni);
            Bodega saldoIniBod = getSaldoInicial(saldoIniSearch, m.getProductoscodigo());
            aSaldosIni.add(saldoIniBod);
            saldoT = saldoIniBod.getCantidad();
            costoUt = saldoIniBod.getCostoUnitario();
            costoTt = saldoIniBod.getCostoTotal();
          }

          kt.setKardexstock(saldoT + kt.getKardexcantidad());
          if (kt.getKardexstock() > 0) {
            kt.setKardexcostototalstock(costoTt + (kt.getKardexpreciocompra() * kt.getKardexcantidad()));
            kt.setKardexcostopromedio(kt.getKardexcostototalstock() / kt.getKardexstock());
          } else {
            kt.setKardexcostototalstock(0.00);
            kt.setKardexcostopromedio(kt.getKardexpreciocompra());
          }
          kt.setKardexcostototal(kt.getKardexcantidad() * kt.getKardexpreciocompra());
          kt.setKardexcantidad_a(saldoT);
          kt.setKardexcostopromedio_a(costoUt);
          saldoT = kt.getKardexstock();
          costoUt = kt.getKardexcostopromedio();
          costoTt = kt.getKardexcostototalstock();
          kRegrabado.add(kt);
          for (Bodega b : aSaldosIni) {
            if (b.getCodigo().equals(kt.getTbodcodigo().trim())) {
              b.setCantidad(saldoT);
              b.setCostoUnitario(costoUt);
              b.setCostoTotal(costoTt);
            }
          }

        }
      }

      if (k.getKardextipotrx().trim().equals("NTI")) {

        if (k.getKardextipo().trim().equals("PRODUCCION") && k.getKardexfecha().compareTo(fechaCerrado) > 0 && k.getKardexnumref() != 0) {
          k.setKardexpreciocompra(
                  getCostoProduccion(k.getProductoscodigo(), k.getKardexnumref()) / k.getKardexcantidad()
          );
        }
        k.setKardexstock(saldo + k.getKardexcantidad());

        if (k.getKardexstock() > 0) {
          if ((saldo * costoU) > 0) {
            k.setKardexcostototalstock(costoT + (k.getKardexpreciocompra() * k.getKardexcantidad()));
            k.setKardexcostopromedio(k.getKardexcostototalstock() / k.getKardexstock());
          } else {
            k.setKardexcostopromedio(k.getKardexpreciocompra());
            k.setKardexcostototalstock(k.getKardexstock() * k.getKardexcostopromedio());
          }
        } else {
          k.setKardexcostototalstock(0.00);
          k.setKardexcostopromedio(k.getKardexpreciocompra());
        }
        k.setKardexcostototal(k.getKardexcantidad() * k.getKardexpreciocompra());
        k.setKardexcantidad_a(saldo);
        k.setKardexcostopromedio_a(costoU);
        saldo = k.getKardexstock();
        costoU = k.getKardexcostopromedio();
        costoT = k.getKardexcostototalstock();
        kRegrabado.add(k);
        for (Bodega b : aSaldosIni) {
          if (b.getCodigo().equals(k.getTbodcodigo().trim())) {
            b.setCantidad(saldo);
            b.setCostoUnitario(costoU);
            b.setCostoTotal(costoT);
          }
        }
      }

      if (k.getKardextipotrx().trim().equals("FAC")) {
        k.setKardexstock(saldo + k.getKardexcantidad());
        k.setKardexcostopromedio(costoU);
        if (k.getKardexstock() <= 0) {
          k.setKardexcostototalstock(0 * k.getKardexcostopromedio());
        } else {
          k.setKardexcostototalstock(k.getKardexstock() * k.getKardexcostopromedio());
        }
        k.setKardexpreciocompra(k.getKardexcostopromedio());
        k.setKardexcostototal((k.getKardexcantidad() * -1) * costoU);
        k.setKardexcantidad_a(saldo);
        k.setKardexcostopromedio_a(costoU);
        saldo = k.getKardexstock();
        costoU = k.getKardexcostopromedio();
        costoT = k.getKardexcostototalstock();
        kRegrabado.add(k);
        for (Bodega b : aSaldosIni) {
          if (b.getCodigo().equals(k.getTbodcodigo().trim())) {
            b.setCantidad(saldo);
            b.setCostoUnitario(costoU);
            b.setCostoTotal(costoT);
          }
        }
      }
      i++;
    }

    System.out.println(" || " + aDocs.size() + " Datos procesados de " + m.getProductoscodigo());
    return kRegrabado;
  }

  private Bodega getSaldoInicial(Bodega bInicial, String codigoProducto) throws ClassNotFoundException, SQLException {
    Bodega respuestaSaldo;
    Date fechaIni = new Date((2015 - 1900), 8, 1);
    if (bInicial.getFecha().compareTo(fechaIni) == 0) {
      respuestaSaldo = db.getSaldoIniSaldosInv(codigoProducto, bInicial.getCodigo());
      if (respuestaSaldo.getCantidad() == null) {
        respuestaSaldo.setCodigo(bInicial.getCodigo());
        respuestaSaldo.setCantidad(0.00);
        respuestaSaldo.setCostoUnitario(0.00);
        respuestaSaldo.setCostoTotal(0.00);
      } else {
        respuestaSaldo.setCostoTotal(respuestaSaldo.getCantidad() * respuestaSaldo.getCostoUnitario());
      }
    } else {
      respuestaSaldo = db.getSaldoMovAnt(codigoProducto, bInicial.getCodigo(), bInicial.getFecha());
      if (respuestaSaldo.getCantidad() == null) {
        respuestaSaldo = db.getSaldoIniSaldosInv(codigoProducto, bInicial.getCodigo());
        if (respuestaSaldo.getCantidad() == null) {
          respuestaSaldo.setCodigo(bInicial.getCodigo());
          respuestaSaldo.setCantidad(0.00);
          respuestaSaldo.setCostoUnitario(0.00);
          respuestaSaldo.setCostoTotal(0.00);
        } else {
          respuestaSaldo.setCostoTotal(respuestaSaldo.getCantidad() * respuestaSaldo.getCostoUnitario());
        }
      }
    }
    return respuestaSaldo;
  }

  private Double getCostoProduccion(String codProducto, int orden) throws ClassNotFoundException, SQLException {
    Double costo = 0.00;
    Double costoMateriales = 0.00;
    Double costoCif = 0.00;
    Double costoMod = 0.00;
    Double costoGas = 0.00;
    FactorCosto ft = null;

    for (FactorCosto fp : aFac) {
      if (fp.getOrdenNumero() == orden) {

        ft = fp;
        //      Calculo de mano de obra directa
        costoMod += (ft.getPersonasRot() * ft.getHorasRot()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
        costoMod += (ft.getPersonasSol() * ft.getHorasSol()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
        costoMod += (ft.getPersonasAca() * ft.getHorasAca()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
        costoMod += (ft.getPersonasTal() * ft.getHorasTal()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
      }
    }

//    FactorCosto ft = db.getFactores(orden);
    for (Materiales m : aMats) {
      if (m.getOrdenNumero() == orden) {
        //      Calculo de materiales
        costoMateriales += m.getDetntecantidad() * m.getDetntepreciounitario();

        if (ft.getCategoria() != null) {
          if (!ft.getCategoria().trim().equals("MPR")) {
            if (m.getTcatcodigo().trim().equals("MP")) {
              // Calculo de costo CIF
              costoCif += m.getDetntecantidad() * ft.getFactorCif();
              // Calculo de costo GAS
              costoGas += m.getDetntecantidad() * ft.getFactorGas();
            }
          }
        }
      }
    }

    costo = costoMateriales + costoCif + costoMod + costoGas;

    return costo;
  }

  private Double getCostoProduccionM2(String codProducto, int orden) throws ClassNotFoundException, SQLException {
    Double costo = 0.00;
    Double costoMateriales = 0.00;
    Double costoCif = 0.00;
    Double costoMod = 0.00;
    Double costoGas = 0.00;
    FactorCosto ft = null;

    for (FactorCosto fp : aFac) {
      if (fp.getOrdenNumero() == orden) {

        ft = fp;
        //      Calculo de mano de obra directa
        costoMod += (ft.getPersonasRot() * ft.getHorasRot()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
        costoMod += (ft.getPersonasSol() * ft.getHorasSol()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
        costoMod += (ft.getPersonasAca() * ft.getHorasAca()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());
        costoMod += (ft.getPersonasTal() * ft.getHorasTal()) * (ft.getFactorMod() == null ? 0.00 : ft.getFactorMod());

        // Calculo de costo CIF
        costoCif += costoMod * (ft.getFactorCif() == null ? 0.00 : ft.getFactorCif());
      }
    }

    for (Materiales m : aMats) {
      if (m.getOrdenNumero() == orden) {
        //      Calculo de materiales
        costoMateriales += m.getDetntecantidad() * m.getDetntepreciounitario();

        if (ft.getCategoria() != null) {
          if (!ft.getCategoria().trim().equals("MPR")) {
            if (m.getTcatcodigo().trim().equals("MP")) {
              // Calculo de costo GAS
              costoGas += m.getDetntecantidad() * ft.getFactorGas();
            }
          }
        }
      }
    }

    costo = costoMateriales + costoCif + costoMod + costoGas;
    return costo;
  }

  @Override
  public void run() {
    try {
      this.listaKardex = regrabadoProducto();
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(RegrabadoProducto.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
      Logger.getLogger(RegrabadoProducto.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public ArrayList getResultado() {
    return this.listaKardex;
  }
}
