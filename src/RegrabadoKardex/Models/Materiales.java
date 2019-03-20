package RegrabadoKardex.Models;

import java.math.BigDecimal;

public class Materiales {

  private int ordenNumero;
  private String productoC;
  private String productoscodigo;
  private String tcatcodigo;
  private BigDecimal detntecantidad;
  private BigDecimal detntepreciounitario;
  private int notegrnumero;
  private String notegrcreateuser;
  private String notegrcodigodiv;

  public int getOrdenNumero() {
    return ordenNumero;
  }

  public void setOrdenNumero(int ordenNumero) {
    this.ordenNumero = ordenNumero;
  }

  public String getProductoC() {
    return productoC;
  }

  public void setProductoC(String productoC) {
    this.productoC = productoC;
  }

  public String getProductoscodigo() {
    return productoscodigo;
  }

  public void setProductoscodigo(String productoscodigo) {
    this.productoscodigo = productoscodigo;
  }

  public String getTcatcodigo() {
    return tcatcodigo;
  }

  public void setTcatcodigo(String tcatcodigo) {
    this.tcatcodigo = tcatcodigo;
  }

  public BigDecimal getDetntecantidad() {
    return detntecantidad;
  }

  public void setDetntecantidad(BigDecimal detntecantidad) {
    this.detntecantidad = detntecantidad;
  }

  public BigDecimal getDetntepreciounitario() {
    return detntepreciounitario;
  }

  public void setDetntepreciounitario(BigDecimal detntepreciounitario) {
    this.detntepreciounitario = detntepreciounitario;
  }

  public int getNotegrnumero() {
    return notegrnumero;
  }

  public void setNotegrnumero(int notegrnumero) {
    this.notegrnumero = notegrnumero;
  }

  public String getNotegrcreateuser() {
    return notegrcreateuser;
  }

  public void setNotegrcreateuser(String notegrcreateuser) {
    this.notegrcreateuser = notegrcreateuser;
  }

  public String getNotegrcodigodiv() {
    return notegrcodigodiv;
  }

  public void setNotegrcodigodiv(String notegrcodigodiv) {
    this.notegrcodigodiv = notegrcodigodiv;
  }

}
