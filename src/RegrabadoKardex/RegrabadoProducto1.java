package RegrabadoKardex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegrabadoProducto1 {

  private DatosDAO db;

  private ArrayList<FactorCosto> aFac = null;
  private ArrayList<Materiales> aMats = null;
  private Movimiento m;
  private Date fechaIni;
  private Date fechaFin;

  public RegrabadoProducto1(Movimiento mov, Date fechaI, Date fechaF) {
    db = new DatosDAO();
    this.m = mov;
    this.fechaIni = fechaI;
    this.fechaFin = fechaF;
  }

  public ArrayList<Kardex> regrabadoProducto() throws ClassNotFoundException, SQLException {
    ArrayList<Kardex> aDocs = null;

    ArrayList<Kardex> kRegrabado = new ArrayList<>();
    ArrayList<Bodega> aSaldosIni = new ArrayList<>();

    Date fechaCerrado = new Date((2015 - 1900), 8, 30);
    aFac = db.getFactores(m.getProductoscodigo(), fechaIni, fechaFin);
    aDocs = db.getDocumentos(m.getProductoscodigo(), fechaIni, fechaFin);
    aMats = db.getMateriales(m.getProductoscodigo(), fechaIni, fechaFin);

    int i = 0; //Conteo de documentos afectados
    for (Kardex k : aDocs) {

//    Comprueba saldo inicial
      int existe = 0;
      BigDecimal saldo = BigDecimal.ZERO;
      BigDecimal costoU = BigDecimal.ZERO;
      BigDecimal costoT = BigDecimal.ZERO;
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

      //************************************     Calcula Costos   ************************************ \\
      if (k.getKardextipotrx().trim().equals("NTE")) {

        k.setKardexstock(saldo.add(k.getKardexcantidad()));
        k.setKardexcostopromedio(costoU);

        if (k.getKardexstock().compareTo(BigDecimal.ZERO) == -1 || k.getKardexstock().compareTo(BigDecimal.ZERO) == 0) {
          k.setKardexcostototalstock(k.getKardexcostopromedio().multiply(BigDecimal.ZERO));
        } else {
          k.setKardexcostototalstock(k.getKardexstock().multiply(k.getKardexcostopromedio()));
        }
        k.setKardexpreciocompra(k.getKardexcostopromedio());
        k.setKardexcostototal(costoU.multiply(k.getKardexcantidad().negate()));
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
                  k.getKardexcantidad().negate(),
                  k.getKardexusuario(),
                  k.getKardexnumref(),
                  k.getKardexregorden()
          );

          int existeSaldoBod = 0;
          BigDecimal saldoT = BigDecimal.ZERO;
          BigDecimal costoUt = BigDecimal.ZERO;
          BigDecimal costoTt = BigDecimal.ZERO;
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

          kt.setKardexstock(saldoT.add(kt.getKardexcantidad()));

          if (kt.getKardexstock().compareTo(BigDecimal.ZERO) == 1) {
            kt.setKardexcostototalstock(costoTt.add(kt.getKardexpreciocompra().multiply(kt.getKardexcantidad())));
            kt.setKardexcostopromedio(kt.getKardexcostototalstock().divide(kt.getKardexstock(), 6, RoundingMode.HALF_UP));
          } else {
            kt.setKardexcostototalstock(BigDecimal.ZERO);
            kt.setKardexcostopromedio(kt.getKardexpreciocompra());
          }
          kt.setKardexcostototal(kt.getKardexcantidad().multiply(kt.getKardexpreciocompra()));
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
          BigDecimal CostoProduccion = getCostoProduccion(k.getProductoscodigo(), k.getKardexnumref());
          k.setKardexpreciocompra(CostoProduccion.divide(k.getKardexcantidad(), 6, RoundingMode.HALF_UP));
        }
        k.setKardexstock(saldo.add(k.getKardexcantidad()));

        if ((k.getKardexstock().compareTo(BigDecimal.ZERO)) == 1) {
          if (saldo.multiply(costoU).compareTo(BigDecimal.ZERO) == 1) {
            k.setKardexcostototalstock(costoT.add(k.getKardexpreciocompra().multiply(k.getKardexcantidad())));
            BigDecimal CostoPromedio = k.getKardexcostototalstock().divide(k.getKardexstock(), 6, RoundingMode.HALF_UP);
            k.setKardexcostopromedio(CostoPromedio);
          } else {
            k.setKardexcostopromedio(k.getKardexpreciocompra());
            k.setKardexcostototalstock(k.getKardexstock().multiply(k.getKardexcostopromedio()));
          }
        } else {
          k.setKardexcostototalstock(BigDecimal.ZERO);
          k.setKardexcostopromedio(k.getKardexpreciocompra());
        }
        k.setKardexcostototal(k.getKardexcantidad().multiply(k.getKardexpreciocompra()));
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
        k.setKardexstock(saldo.add(k.getKardexcantidad()));
        k.setKardexcostopromedio(costoU);
        if (k.getKardexstock().compareTo(BigDecimal.ZERO) == -1 || k.getKardexstock().compareTo(BigDecimal.ZERO) == 0) {
          k.setKardexcostototalstock(k.getKardexcostopromedio().multiply(BigDecimal.ZERO));
        } else {
          k.setKardexcostototalstock(k.getKardexstock().multiply(k.getKardexcostopromedio()));
        }
        k.setKardexpreciocompra(k.getKardexcostopromedio());
        k.setKardexcostototal(k.getKardexcantidad().negate().multiply(costoU));
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

    System.out.println("   || " + aDocs.size() + " Datos procesados de " + m.getProductoscodigo());
    return kRegrabado;
  }

  private Bodega getSaldoInicial(Bodega bInicial, String codigoProducto) throws ClassNotFoundException, SQLException {
    Bodega respuestaSaldo;
    Date fechaIni = new Date((2015 - 1900), 8, 1);
    if (bInicial.getFecha().compareTo(fechaIni) == 0) {
      respuestaSaldo = db.getSaldoIniSaldosInv(codigoProducto, bInicial.getCodigo());
      if (respuestaSaldo.getCantidad() == null) {
        respuestaSaldo.setCodigo(bInicial.getCodigo());
        respuestaSaldo.setCantidad(BigDecimal.ZERO);
        respuestaSaldo.setCostoUnitario(BigDecimal.ZERO);
        respuestaSaldo.setCostoTotal(BigDecimal.ZERO);
      } else {
        respuestaSaldo.setCostoTotal(respuestaSaldo.getCantidad().multiply(respuestaSaldo.getCostoUnitario()));
      }
    } else {
      respuestaSaldo = db.getSaldoMovAnt(codigoProducto, bInicial.getCodigo(), bInicial.getFecha());
      if (respuestaSaldo.getCantidad() == null) {
        respuestaSaldo = db.getSaldoIniSaldosInv(codigoProducto, bInicial.getCodigo());
        if (respuestaSaldo.getCantidad() == null) {
          respuestaSaldo.setCodigo(bInicial.getCodigo());
          respuestaSaldo.setCantidad(BigDecimal.ZERO);
          respuestaSaldo.setCostoUnitario(BigDecimal.ZERO);
          respuestaSaldo.setCostoTotal(BigDecimal.ZERO);
        } else {
          respuestaSaldo.setCostoTotal(respuestaSaldo.getCantidad().multiply(respuestaSaldo.getCostoUnitario()));
        }
      }
    }
    return respuestaSaldo;
  }

  private BigDecimal getCostoProduccion(String codProducto, int orden)
          throws ClassNotFoundException, SQLException {
    BigDecimal costo = BigDecimal.ZERO;
    BigDecimal costoMateriales = BigDecimal.ZERO;
    BigDecimal costoCif = BigDecimal.ZERO;
    BigDecimal costoMod = BigDecimal.ZERO;
    BigDecimal costoGas = BigDecimal.ZERO;
    FactorCosto ft = null;

    for (FactorCosto fp : aFac) {
      if (fp.getOrdenNumero() == orden) {
        ft = fp;
        //      Calculo de mano de obra directa
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO
                : ft.getFactorMod()).multiply(ft.getHorasRot().multiply(new BigDecimal(ft.getPersonasRot()))));
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO
                : ft.getFactorMod()).multiply(ft.getHorasSol().multiply(new BigDecimal(ft.getPersonasSol()))));
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO
                : ft.getFactorMod()).multiply(ft.getHorasAca().multiply(new BigDecimal(ft.getPersonasAca()))));
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO
                : ft.getFactorMod()).multiply(ft.getHorasTal().multiply(new BigDecimal(ft.getPersonasTal()))));
      }
    }

//    FactorCosto ft = db.getFactores(orden);
    for (Materiales m : aMats) {
      if (m.getOrdenNumero() == orden) {
        //      Calculo de materiales
        costoMateriales = costoMateriales.add(m.getDetntecantidad().multiply(m.getDetntepreciounitario()));

        if (ft.getCategoria() != null) {
          if (!ft.getCategoria().trim().equals("MPR")) {
            if (m.getTcatcodigo().trim().equals("MP")) {
              // Calculo de costo CIF
              costoCif = costoCif.add(m.getDetntecantidad().multiply(ft.getFactorCif()));
              // Calculo de costo GAS
              costoGas = costoGas.add(m.getDetntecantidad().multiply(ft.getFactorGas()));
            }
          }
        }
      }
    }
    costo = costoMateriales.add(costoCif.add(costoMod.add(costoGas)));
    return costo;
  }

  private BigDecimal getCostoProduccionM2(String codProducto, int orden)
          throws ClassNotFoundException, SQLException {
    BigDecimal costo = BigDecimal.ZERO;
    BigDecimal costoMateriales = BigDecimal.ZERO;
    BigDecimal costoCif = BigDecimal.ZERO;
    BigDecimal costoMod = BigDecimal.ZERO;
    BigDecimal costoGas = BigDecimal.ZERO;
    FactorCosto ft = null;

    for (FactorCosto fp : aFac) {
      if (fp.getOrdenNumero() == orden) {

        ft = fp;
        //      Calculo de mano de obra directa
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod()).multiply(ft.getHorasRot().multiply(new BigDecimal(ft.getPersonasRot()))));
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod()).multiply(ft.getHorasSol().multiply(new BigDecimal(ft.getPersonasSol()))));
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod()).multiply(ft.getHorasAca().multiply(new BigDecimal(ft.getPersonasAca()))));
        costoMod = costoMod.add((ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod()).multiply(ft.getHorasTal().multiply(new BigDecimal(ft.getPersonasTal()))));
      }
    }

    // Calculo de costo CIF
    costoCif = costoCif.add((ft.getFactorCif() == null ? BigDecimal.ZERO : ft.getFactorCif()).multiply(costoMod));

    for (Materiales m : aMats) {
      if (m.getOrdenNumero() == orden) {
        //      Calculo de materiales
        costoMateriales = costoMateriales.add(m.getDetntecantidad().multiply(m.getDetntepreciounitario()));

        if (ft.getCategoria() != null) {
          if (!ft.getCategoria().trim().equals("MPR")) {
            if (m.getTcatcodigo().trim().equals("MP")) {
              // Calculo de costo GAS
              costoGas = costoGas.add(m.getDetntecantidad().multiply(ft.getFactorGas()));
            }
          }
        }
      }
    }

    costo = costoMateriales.add(costoCif.add(costoMod.add(costoGas)));
    return costo;
  }

}
