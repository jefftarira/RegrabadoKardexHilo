package RegrabadoKardex;

import RegrabadoKardex.Models.Movimiento;
import RegrabadoKardex.Models.Materiales;
import RegrabadoKardex.Models.FactorCosto;
import RegrabadoKardex.Models.Kardex;
import RegrabadoKardex.Models.Bodega;
import RegrabadoKardex.Models.SaldoBodega;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RegrabadoProducto1 {

  private DatosDAO db;

  private ArrayList<Kardex> kRegrabado = new ArrayList<>();
  private ArrayList<Bodega> aSaldosIni = new ArrayList<>();
  private ArrayList<FactorCosto> aFac = null;
  private ArrayList<Materiales> aMats = null;
  private Movimiento m;
  private Date fechaIni;
  private Date fechaFin;

  RegrabadoProducto1(Movimiento mov, Date fechaI, Date fechaF) {
    db = new DatosDAO();
    this.m = mov;
    this.fechaIni = fechaI;
    this.fechaFin = fechaF;
  }

  ArrayList<Kardex> regrabadoProducto() throws SQLException, ParseException {

    String fechaClose = "30-09-2015";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Date fechaCerrado = new Date(sdf.parse(fechaClose).getTime());

    aFac = db.getFactores(m.getProductoscodigo(), fechaIni, fechaFin);
    ArrayList<Kardex> aDocs = db.getDocumentos(m.getProductoscodigo(), fechaIni, fechaFin);
    aMats = db.getMateriales(m.getProductoscodigo());

    for (Kardex k : aDocs) {

      //****************** COMPRUEBA SALDO INICIAL ***************************** \\
      SaldoBodega sb = comprobarSaldoInicial(k);

      //****************************     CALCULA COSTOS ***************************** \\
      if (k.getKardextipotrx().trim().equals("NTE")) {
        k.setKardexcantidad(k.getKardexcantidad().setScale(5, RoundingMode.HALF_UP));
        k.setKardexstock(sb.getSaldo().add(k.getKardexcantidad()).setScale(6, RoundingMode.HALF_UP));
        k.setKardexcostopromedio(sb.getCostoU().setScale(6, RoundingMode.HALF_UP));

        if (k.getKardexstock().compareTo(BigDecimal.ZERO) == -1 || k.getKardexstock().compareTo(BigDecimal.ZERO) == 0) {
          k.setKardexcostototalstock(k.getKardexcostopromedio().multiply(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP));
        } else {
          k.setKardexcostototalstock(k.getKardexstock().multiply(k.getKardexcostopromedio()).setScale(2, RoundingMode.HALF_UP));
        }
        k.setKardexpreciocompra(k.getKardexcostopromedio());
        k.setKardexcostototal(sb.getCostoU().multiply(k.getKardexcantidad().negate()).setScale(2, RoundingMode.HALF_UP));
        k.setKardexcantidad_a(sb.getSaldo().setScale(5, RoundingMode.HALF_UP));
        k.setKardexcostopromedio_a(sb.getCostoU().setScale(6, RoundingMode.HALF_UP));

        sb.setSaldo(k.getKardexstock());
        sb.setCostoU(k.getKardexcostopromedio().setScale(6, RoundingMode.HALF_UP));
        sb.setCostoT(k.getKardexcostototalstock().setScale(2, RoundingMode.HALF_UP));
        updateSaldoInicialBodega(k);

        // ************************** PROCESO ESPECIAL POR TRANSFERENCIA *********************************** \\
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

          SaldoBodega sbt = comprobarSaldoInicial(kt);
          kt.setKardexstock(sbt.getSaldo().add(kt.getKardexcantidad()));
          if (kt.getKardexstock().compareTo(BigDecimal.ZERO) == 1) {
            kt.setKardexcostototalstock(sbt.getCostoT().add(kt.getKardexpreciocompra().multiply(kt.getKardexcantidad())).setScale(2, RoundingMode.HALF_UP));
            kt.setKardexcostopromedio(kt.getKardexcostototalstock().divide(kt.getKardexstock(), 6, RoundingMode.HALF_UP));
          } else {
            kt.setKardexcostototalstock(BigDecimal.ZERO);
            kt.setKardexcostopromedio(kt.getKardexpreciocompra().setScale(6, RoundingMode.HALF_UP));
          }
          kt.setKardexcostototal(kt.getKardexcantidad().multiply(kt.getKardexpreciocompra()).setScale(2, RoundingMode.HALF_UP));
          kt.setKardexcantidad_a(sbt.getSaldo().setScale(5, RoundingMode.HALF_UP));
          kt.setKardexcostopromedio_a(sbt.getCostoU().setScale(6, RoundingMode.HALF_UP));
          updateSaldoInicialBodega(kt);
        }
      }

      if (k.getKardextipotrx().trim().equals("NTI")) {
        k.setKardexcantidad(k.getKardexcantidad().setScale(6, RoundingMode.HALF_UP));
        if (k.getKardextipo().trim().equals("PRODUCCION")
                && k.getKardexfecha().compareTo(fechaCerrado) > 0
                && k.getKardexnumref() != 0) {
          BigDecimal CostoProduccion = getCostoProduccion(k.getKardexnumref());
          k.setKardexpreciocompra(CostoProduccion.divide(k.getKardexcantidad(), 6, RoundingMode.HALF_UP));
        }

        k.setKardexstock(sb.getSaldo().add(k.getKardexcantidad()).setScale(6, RoundingMode.HALF_UP));
        if ((k.getKardexstock().compareTo(BigDecimal.ZERO)) == 1) {
          if (sb.getSaldo().multiply(sb.getCostoU()).compareTo(BigDecimal.ZERO) == 1) {
            k.setKardexcostototalstock(sb.getCostoT().add(k.getKardexpreciocompra().multiply(k.getKardexcantidad()))
                    .setScale(2, RoundingMode.HALF_UP));
            BigDecimal CostoPromedio = k.getKardexcostototalstock().divide(k.getKardexstock(), 6, RoundingMode.HALF_UP);
            k.setKardexcostopromedio(CostoPromedio);
          } else {
            k.setKardexcostopromedio(k.getKardexpreciocompra().setScale(6, RoundingMode.HALF_UP));
            k.setKardexcostototalstock(k.getKardexstock().multiply(k.getKardexcostopromedio()).setScale(2, RoundingMode.HALF_UP));
          }
        } else {
          k.setKardexcostototalstock(BigDecimal.ZERO);
          k.setKardexcostopromedio(k.getKardexpreciocompra().setScale(6, RoundingMode.HALF_UP));
        }
        k.setKardexcostototal(k.getKardexcantidad().multiply(k.getKardexpreciocompra()).setScale(2, RoundingMode.HALF_UP));
        k.setKardexcantidad_a(sb.getSaldo().setScale(4, RoundingMode.HALF_UP));
        k.setKardexcostopromedio_a(sb.getCostoU().setScale(6, RoundingMode.HALF_UP));
        k.setKardexstock(k.getKardexstock().setScale(6, RoundingMode.HALF_UP));
        updateSaldoInicialBodega(k);
      }

      if (k.getKardextipotrx().trim().equals("FAC")) {
        k.setKardexstock(sb.getSaldo().add(k.getKardexcantidad()).setScale(6, RoundingMode.HALF_UP));
        k.setKardexcostopromedio(sb.getCostoU().setScale(6, RoundingMode.HALF_UP));
        if (k.getKardexstock().compareTo(BigDecimal.ZERO) == -1
                || k.getKardexstock().compareTo(BigDecimal.ZERO) == 0) {
          k.setKardexcostototalstock(k.getKardexcostopromedio().multiply(BigDecimal.ZERO));
        } else {
          k.setKardexcostototalstock(
                  k.getKardexstock().multiply(k.getKardexcostopromedio())
                          .setScale(2, RoundingMode.HALF_UP));
        }
        k.setKardexpreciocompra(k.getKardexcostopromedio());
        k.setKardexcostototal(
                k.getKardexcantidad().negate().multiply(sb.getCostoU())
                        .setScale(2, RoundingMode.HALF_UP));
        k.setKardexcantidad_a(sb.getSaldo().setScale(6, RoundingMode.HALF_UP));
        k.setKardexcostopromedio_a(sb.getCostoU().setScale(6, RoundingMode.HALF_UP));
        updateSaldoInicialBodega(k);
      }
    }
    System.out.println("   || " + aDocs.size() + " Datos procesados de " + m.getProductoscodigo());
    return kRegrabado;
  }

  private void updateSaldoInicialBodega(Kardex k) {
    for (Bodega b : aSaldosIni) {
      if (b.getCodigo().equals(k.getTbodcodigo().trim())) {
        b.setCantidad(k.getKardexstock());
        b.setCostoUnitario(k.getKardexcostopromedio().setScale(6, RoundingMode.HALF_UP));
        b.setCostoTotal(k.getKardexcostototalstock().setScale(2, RoundingMode.HALF_UP));
      }
    }
    kRegrabado.add(k);
  }

  private SaldoBodega comprobarSaldoInicial(Kardex k) throws SQLException, ParseException {
    int existe = 0;
    SaldoBodega sb = new SaldoBodega(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    for (Bodega b : aSaldosIni) {
      if (b.getCodigo().equals(k.getTbodcodigo().trim())) {
        existe = 1;
        sb.setSaldo(b.getCantidad());
        sb.setCostoU(b.getCostoUnitario());
        sb.setCostoT(b.getCostoTotal());
      }
    }
    if (existe == 0) {
      Bodega saldoIniSearch = new Bodega(k.getTbodcodigo(), fechaIni);
      Bodega saldoIniBod = getSaldoInicial(saldoIniSearch, m.getProductoscodigo());
      aSaldosIni.add(saldoIniBod);
      sb.setSaldo(saldoIniBod.getCantidad());
      sb.setCostoU(saldoIniBod.getCostoUnitario());
      sb.setCostoT(saldoIniBod.getCostoTotal());
    }
    return sb;
  }

  private Bodega getSaldoInicial(Bodega bInicial, String codigoProducto) throws SQLException, ParseException {
    Bodega respuestaSaldo;
    String dateIni = "01-09-2015";
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Date fechaIni = new Date(sdf.parse(dateIni).getTime());

    if (bInicial.getFecha().compareTo(fechaIni) == 0) {
      respuestaSaldo = getRespuestaSaldo(bInicial, codigoProducto);
    } else {
      respuestaSaldo = db.getSaldoMovAnt(codigoProducto, bInicial.getCodigo(), bInicial.getFecha());
      if (respuestaSaldo.getCantidad() == null) {
        respuestaSaldo = getRespuestaSaldo(bInicial, codigoProducto);
      }
    }
    return respuestaSaldo;
  }

  private Bodega getRespuestaSaldo(Bodega bInicial, String codigoProducto) throws SQLException {
    Bodega respuestaSaldo;
    respuestaSaldo = db.getSaldoIniSaldosInv(codigoProducto, bInicial.getCodigo());
    if (respuestaSaldo.getCantidad() == null) {
      respuestaSaldo.setCodigo(bInicial.getCodigo());
      respuestaSaldo.setCantidad(BigDecimal.ZERO);
      respuestaSaldo.setCostoUnitario(BigDecimal.ZERO);
      respuestaSaldo.setCostoTotal(BigDecimal.ZERO);
    } else {
      respuestaSaldo.setCostoTotal(respuestaSaldo.getCantidad().multiply(respuestaSaldo.getCostoUnitario()));
    }
    return respuestaSaldo;
  }

  private BigDecimal getCostoProduccion(int orden) {
    BigDecimal costo;
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

        if (ft != null && ft.getCategoria() != null) {
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

//	private BigDecimal getCostoProduccionM2(String codProducto, int orden)
//					throws ClassNotFoundException, SQLException {
//		BigDecimal costo = BigDecimal.ZERO;
//		BigDecimal costoMateriales = BigDecimal.ZERO;
//		BigDecimal costoCif = BigDecimal.ZERO;
//		BigDecimal costoMod = BigDecimal.ZERO;
//		BigDecimal costoGas = BigDecimal.ZERO;
//		FactorCosto ft = null;
//
//		for (FactorCosto fp : aFac) {
//			if (fp.getOrdenNumero() == orden) {
//
//				ft = fp;
//				//      Calculo de mano de obra directa
//				costoMod = costoMod.add(
//								(ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod())
//												.multiply(ft.getHorasRot().multiply(new BigDecimal(ft.getPersonasRot()))));
//				costoMod = costoMod.add(
//								(ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod())
//												.multiply(ft.getHorasSol().multiply(new BigDecimal(ft.getPersonasSol()))));
//				costoMod = costoMod.add(
//								(ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod())
//												.multiply(ft.getHorasAca().multiply(new BigDecimal(ft.getPersonasAca()))));
//				costoMod = costoMod.add(
//								(ft.getFactorMod() == null ? BigDecimal.ZERO : ft.getFactorMod())
//												.multiply(ft.getHorasTal().multiply(new BigDecimal(ft.getPersonasTal()))));
//			}
//		}
//
//		// Calculo de costo CIF
//		costoCif = costoCif.add((ft.getFactorCif() == null ? BigDecimal.ZERO : ft.getFactorCif()).multiply(costoMod));
//		for (Materiales m : aMats) {
//			if (m.getOrdenNumero() == orden) {
//				//      Calculo de materiales
//				costoMateriales = costoMateriales.add(m.getDetntecantidad().multiply(m.getDetntepreciounitario()));
//
//				if (ft.getCategoria() != null) {
//					if (!ft.getCategoria().trim().equals("MPR")) {
//						if (m.getTcatcodigo().trim().equals("MP")) {
//							// Calculo de costo GAS
//							costoGas = costoGas.add(m.getDetntecantidad().multiply(ft.getFactorGas()));
//						}
//					}
//				}
//			}
//		}
//		costo = costoMateriales.add(costoCif.add(costoMod.add(costoGas)));
//		return costo;
//	}
}
