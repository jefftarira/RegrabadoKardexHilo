package BigData.Models;

import java.math.BigDecimal;
import java.sql.Date;

public class LogMarketsoft {

  private String usuario;
  private Date logdia;
  private String loghora;
  private int logsec;
  private String logpgm;
  private String logmode;
  private String logref;
  private int logdocnum;
  private BigDecimal logvalor;

  public LogMarketsoft() {
  }

  public LogMarketsoft(String usuario, Date logdia, String loghora, int logsec, String logpgm, String logmode, String logref, int logdocnum, BigDecimal logvalor) {
    this.usuario = usuario;
    this.logdia = logdia;
    this.loghora = loghora;
    this.logsec = logsec;
    this.logpgm = logpgm;
    this.logmode = logmode;
    this.logref = logref;
    this.logdocnum = logdocnum;
    this.logvalor = logvalor;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getLogdia() {
    return logdia;
  }

  public void setLogdia(Date logdia) {
    this.logdia = logdia;
  }

  public String getLoghora() {
    return loghora;
  }

  public void setLoghora(String loghora) {
    this.loghora = loghora;
  }

  public int getLogsec() {
    return logsec;
  }

  public void setLogsec(int logsec) {
    this.logsec = logsec;
  }

  public String getLogpgm() {
    return logpgm;
  }

  public void setLogpgm(String logpgm) {
    this.logpgm = logpgm;
  }

  public String getLogmode() {
    return logmode;
  }

  public void setLogmode(String logmode) {
    this.logmode = logmode;
  }

  public String getLogref() {
    return logref;
  }

  public void setLogref(String logref) {
    this.logref = logref;
  }

  public int getLogdocnum() {
    return logdocnum;
  }

  public void setLogdocnum(int logdocnum) {
    this.logdocnum = logdocnum;
  }

  public BigDecimal getLogvalor() {
    return logvalor;
  }

  public void setLogvalor(BigDecimal logvalor) {
    this.logvalor = logvalor;
  }
  
  
  
}
