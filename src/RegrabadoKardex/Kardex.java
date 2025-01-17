package RegrabadoKardex;

import java.sql.Date;

public class Kardex implements Comparable<Kardex>{

  private String kardexcodigodiv;
  private String kardexanno;
  private String kardextipotrx;
  private int kardexnumero;
  private int kardexlinea;
  private int kardexcodigosec;
  private String kardexorden;
  private Date kardexfecha;
  private String kardexhora;
  private String productotodo;
  private String productoscodigo;
  private String tbodcodigo;
  private String tbodcodigo2;
  private String kardexlote;
  private Date kardexcaducidad;
  private String kardexdescripcion;
  private String kardextipo;
  private String kardexcodigoven1;
  private Double kardexpreciocompra;
  private Double kardexprecioventa;
  private Double kardexvalordescuento;
  private Double kardexcantidad;
  private Double kardexcostopromedio;
  private Double kardexcostototal;
  private Double kardexstock;
  private Double kardexcantidad_a;
  private Double kardexcostopromedio_a;
  private Double kardexcostototalstock;
  private String kardexcreateuser;
  private Date kardexcreatedate;
  private String kardexcreatepgm;
  private String kardexupdateuser;
  private Date kardexupdatedate;
  private String kardexupdatetime;
  private String kardexupdatepgm;
  private String kardexusuario;
  private int kardexnumref;
  private int kardexregorden;

  public Kardex(String kardexcodigodiv, String kardexanno, String kardextipotrx, int kardexnumero,
          int kardexlinea, int kardexcodigosec, String kardexorden, Date kardexfecha, String kardexhora,
          String productoscodigo, String tbodcodigo, String kardexlote, Date kardexcaducidad,
          String kardexdescripcion, String kardextipo, String kardexcodigoven1, Double kardexpreciocompra,
          Double kardexprecioventa, Double kardexvalordescuento, Double kardexcantidad,
          Double kardexcostopromedio, Double kardexcostototal, Double kardexstock, Double kardexcantidad_a,
          Double kardexcostopromedio_a, Double kardexcostototalstock, String kardexcreateuser,
          Date kardexcreatedate, String kardexcreatepgm, String kardexupdateuser, Date kardexupdatedate,
          String kardexupdatetime, String kardexupdatepgm, String kardexusuario) {

    this.setKardexcodigodiv(kardexcodigodiv);
    this.setKardexanno(kardexanno);
    this.setKardextipotrx(kardextipotrx);
    this.setKardexnumero(kardexnumero);
    this.setKardexlinea(kardexlinea);
    this.setKardexcodigosec(kardexcodigosec);
    this.setKardexorden(kardexorden);
    this.setKardexfecha(kardexfecha);
    this.setKardexhora(kardexhora);
    this.setProductoscodigo(productoscodigo);
    this.setTbodcodigo(tbodcodigo);
    this.setKardexlote(kardexlote);
    this.setKardexcaducidad(kardexcaducidad);
    this.setKardexdescripcion(kardexdescripcion);
    this.setKardextipo(kardextipo);
    this.setKardexcodigoven1(kardexcodigoven1);
    this.setKardexpreciocompra(kardexpreciocompra);
    this.setKardexprecioventa(kardexprecioventa);
    this.setKardexvalordescuento(kardexvalordescuento);
    this.setKardexcantidad(kardexcantidad);
    this.setKardexcostopromedio(kardexcostopromedio);
    this.setKardexcostototal(kardexcostototal);
    this.setKardexstock(kardexstock);
    this.setKardexcantidad_a(kardexcantidad_a);
    this.setKardexcostopromedio_a(kardexcostopromedio_a);
    this.setKardexcostototalstock(kardexcostototalstock);
    this.setKardexcreateuser(kardexcreateuser);
    this.setKardexcreatedate(kardexcreatedate);
    this.setKardexcreatepgm(kardexcreatepgm);
    this.setKardexupdateuser(kardexupdateuser);
    this.setKardexupdatedate(kardexupdatedate);
    this.setKardexupdatetime(kardexupdatetime);
    this.setKardexupdatepgm(kardexupdatepgm);
    this.setKardexusuario(kardexusuario);
  }
  
  
//  Seleccion de productos
  public Kardex(String kardexcodigodiv, String kardexanno, String kardextipotrx, int kardexnumero,
          int kardexlinea, String kardexorden, Date kardexfecha,String productotodo, String productoscodigo, String tbodcodigo,
          String tbodcodigo2, String kardexlote, Date kardexcaducidad, String kardexdescripcion, String kardextipo, 
          String kardexcodigoven1, Double kardexpreciocompra, Double kardexprecioventa, Double kardexvalordescuento,
          Double kardexcantidad, String kardexusuario, int kardexnumref, int kardexregorden) {

    this.setKardexcodigodiv(kardexcodigodiv);
    this.setKardexanno(kardexanno);
    this.setKardextipotrx(kardextipotrx);
    this.setKardexnumero(kardexnumero);
    this.setKardexlinea(kardexlinea);
    this.setKardexorden(kardexorden);
    this.setKardexfecha(kardexfecha);
    this.setProductotodo(productotodo);
    this.setProductoscodigo(productoscodigo);
    this.setTbodcodigo(tbodcodigo);
    this.setTbodcodigo2(tbodcodigo2);
    this.setKardexlote(kardexlote);
    this.setKardexcaducidad(kardexcaducidad);
    this.setKardexdescripcion(kardexdescripcion);
    this.setKardextipo(kardextipo);
    this.setKardexcodigoven1(kardexcodigoven1);
    this.setKardexpreciocompra(kardexpreciocompra);
    this.setKardexprecioventa(kardexprecioventa);
    this.setKardexvalordescuento(kardexvalordescuento);
    this.setKardexcantidad(kardexcantidad);
    this.setKardexusuario(kardexusuario);
    this.setKardexnumref(kardexnumref);
    this.setKardexregorden(kardexregorden);
  }

  public String getKardexcodigodiv() {
    return kardexcodigodiv;
  }

  public void setKardexcodigodiv(String kardexcodigodiv) {
    this.kardexcodigodiv = kardexcodigodiv;
  }

  public String getKardexanno() {
    return kardexanno;
  }

  public void setKardexanno(String kardexanno) {
    this.kardexanno = kardexanno;
  }

  public String getKardextipotrx() {
    return kardextipotrx;
  }

  public void setKardextipotrx(String kardextipotrx) {
    this.kardextipotrx = kardextipotrx;
  }

  public int getKardexnumero() {
    return kardexnumero;
  }

  public void setKardexnumero(int kardexnumero) {
    this.kardexnumero = kardexnumero;
  }

  public int getKardexlinea() {
    return kardexlinea;
  }

  public void setKardexlinea(int kardexlinea) {
    this.kardexlinea = kardexlinea;
  }

  public int getKardexcodigosec() {
    return kardexcodigosec;
  }

  public void setKardexcodigosec(int kardexcodigosec) {
    this.kardexcodigosec = kardexcodigosec;
  }

  public String getKardexorden() {
    return kardexorden;
  }

  public void setKardexorden(String kardexorden) {
    this.kardexorden = kardexorden;
  }

  public Date getKardexfecha() {
    return kardexfecha;
  }

  public void setKardexfecha(Date kardexfecha) {
    this.kardexfecha = kardexfecha;
  }

  public String getKardexhora() {
    return kardexhora;
  }

  public void setKardexhora(String kardexhora) {
    this.kardexhora = kardexhora;
  }

  public String getProductoscodigo() {
    return productoscodigo;
  }

  public void setProductoscodigo(String productoscodigo) {
    this.productoscodigo = productoscodigo;
  }

  public String getTbodcodigo() {
    return tbodcodigo;
  }

  public void setTbodcodigo(String tbodcodigo) {
    this.tbodcodigo = tbodcodigo;
  }

  public String getTbodcodigo2() {
    return tbodcodigo2;
  }

  public void setTbodcodigo2(String tbodcodigo2) {
    this.tbodcodigo2 = tbodcodigo2;
  }

  public String getKardexlote() {
    return kardexlote;
  }

  public void setKardexlote(String kardexlote) {
    this.kardexlote = kardexlote;
  }

  public Date getKardexcaducidad() {
    return kardexcaducidad;
  }

  public void setKardexcaducidad(Date kardexcaducidad) {
    this.kardexcaducidad = kardexcaducidad;
  }

  public String getKardexdescripcion() {
    return kardexdescripcion;
  }

  public void setKardexdescripcion(String kardexdescripcion) {
    this.kardexdescripcion = kardexdescripcion;
  }

  public String getKardextipo() {
    return kardextipo;
  }

  public void setKardextipo(String kardextipo) {
    this.kardextipo = kardextipo;
  }

  public String getKardexcodigoven1() {
    return kardexcodigoven1;
  }

  public void setKardexcodigoven1(String kardexcodigoven1) {
    this.kardexcodigoven1 = kardexcodigoven1;
  }

  public Double getKardexpreciocompra() {
    return kardexpreciocompra;
  }

  public void setKardexpreciocompra(Double kardexpreciocompra) {
    this.kardexpreciocompra = kardexpreciocompra;
  }

  public Double getKardexprecioventa() {
    return kardexprecioventa;
  }

  public void setKardexprecioventa(Double kardexprecioventa) {
    this.kardexprecioventa = kardexprecioventa;
  }

  public Double getKardexvalordescuento() {
    return kardexvalordescuento;
  }

  public void setKardexvalordescuento(Double kardexvalordescuento) {
    this.kardexvalordescuento = kardexvalordescuento;
  }

  public Double getKardexcantidad() {
    return kardexcantidad;
  }

  public void setKardexcantidad(Double kardexcantidad) {
    this.kardexcantidad = kardexcantidad;
  }

  public Double getKardexcostopromedio() {
    return kardexcostopromedio;
  }

  public void setKardexcostopromedio(Double kardexcostopromedio) {
    this.kardexcostopromedio = kardexcostopromedio;
  }

  public Double getKardexcostototal() {
    return kardexcostototal;
  }

  public void setKardexcostototal(Double kardexcostototal) {
    this.kardexcostototal = kardexcostototal;
  }

  public Double getKardexstock() {
    return kardexstock;
  }

  public void setKardexstock(Double kardexstock) {
    this.kardexstock = kardexstock;
  }

  public Double getKardexcantidad_a() {
    return kardexcantidad_a;
  }

  public void setKardexcantidad_a(Double kardexcantidad_a) {
    this.kardexcantidad_a = kardexcantidad_a;
  }

  public Double getKardexcostopromedio_a() {
    return kardexcostopromedio_a;
  }

  public void setKardexcostopromedio_a(Double kardexcostopromedio_a) {
    this.kardexcostopromedio_a = kardexcostopromedio_a;
  }

  public Double getKardexcostototalstock() {
    return kardexcostototalstock;
  }

  public void setKardexcostototalstock(Double kardexcostototalstock) {
    this.kardexcostototalstock = kardexcostototalstock;
  }

  public String getKardexcreateuser() {
    return kardexcreateuser;
  }

  public void setKardexcreateuser(String kardexcreateuser) {
    this.kardexcreateuser = kardexcreateuser;
  }

  public Date getKardexcreatedate() {
    return kardexcreatedate;
  }

  public void setKardexcreatedate(Date kardexcreatedate) {
    this.kardexcreatedate = kardexcreatedate;
  }

  public String getKardexcreatepgm() {
    return kardexcreatepgm;
  }

  public void setKardexcreatepgm(String kardexcreatepgm) {
    this.kardexcreatepgm = kardexcreatepgm;
  }

  public String getKardexupdateuser() {
    return kardexupdateuser;
  }

  public void setKardexupdateuser(String kardexupdateuser) {
    this.kardexupdateuser = kardexupdateuser;
  }

  public Date getKardexupdatedate() {
    return kardexupdatedate;
  }

  public void setKardexupdatedate(Date kardexupdatedate) {
    this.kardexupdatedate = kardexupdatedate;
  }

  public String getKardexupdatetime() {
    return kardexupdatetime;
  }

  public void setKardexupdatetime(String kardexupdatetime) {
    this.kardexupdatetime = kardexupdatetime;
  }

  public String getKardexupdatepgm() {
    return kardexupdatepgm;
  }

  public void setKardexupdatepgm(String kardexupdatepgm) {
    this.kardexupdatepgm = kardexupdatepgm;
  }

  public String getKardexusuario() {
    return kardexusuario;
  }

  public void setKardexusuario(String kardexusuario) {
    this.kardexusuario = kardexusuario;
  }
  
   public String getProductotodo() {
    return productotodo;
  }

  public void setProductotodo(String productotodo) {
    this.productotodo = productotodo;
  }

  public int getKardexregorden() {
    return kardexregorden;
  }

  public void setKardexregorden(int kardexregorden) {
    this.kardexregorden = kardexregorden;
  }

  public int getKardexnumref() {
    return kardexnumref;
  }

  public void setKardexnumref(int kardexnumref) {
    this.kardexnumref = kardexnumref;
  }

  @Override
  public int compareTo(Kardex o) {
    int resultado = kardexfecha.compareTo(o.getKardexfecha());
    if(resultado != 0){return resultado;}
    
    resultado = Integer.compare(kardexregorden, o.getKardexregorden());
    if(resultado != 0){return resultado;}
    
    resultado = Integer.compare(kardexnumero, o.getKardexnumero());
    return resultado;
  }

}
