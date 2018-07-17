package MigrarEgresosBod.MODELS;

import java.math.BigDecimal;

public class OpDetalle {

  private int id;
  private int idOrdenProduccion;
  private String codigoProducto;
  private BigDecimal cantidad;

  public OpDetalle() {
  }

  public OpDetalle(int id, int idOrdenProduccion, String codigoProducto, BigDecimal cantidad) {
    this.id = id;
    this.idOrdenProduccion = idOrdenProduccion;
    this.codigoProducto = codigoProducto;
    this.cantidad = cantidad;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdOrdenProduccion() {
    return idOrdenProduccion;
  }

  public void setIdOrdenProduccion(int idOrdenProduccion) {
    this.idOrdenProduccion = idOrdenProduccion;
  }

  public String getCodigoProducto() {
    return codigoProducto;
  }

  public void setCodigoProducto(String codigoProducto) {
    this.codigoProducto = codigoProducto;
  }

  public BigDecimal getCantidad() {
    return cantidad;
  }

  public void setCantidad(BigDecimal cantidad) {
    this.cantidad = cantidad;
  }

}
