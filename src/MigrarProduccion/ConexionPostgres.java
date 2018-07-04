package MigrarProduccion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionPostgres {

//  private String server = "localhost";
//  private String dbname = "PlastimetV10P";
//  private String port = "5432";
//  private String user = "postgres";
//  private String passw = "reload";
//  
  private final String server = "192.168.0.4";
  private final String dbname = "PlastimetV10P";
  private final String port = "5432";
  private final String user = "postgres";
  private final String passw = "Csdla2008!";

  private Connection con;

  public ConexionPostgres() {
    con = null;
  }

  public void conectar() throws ClassNotFoundException, SQLException {    
    Class.forName("org.postgresql.Driver");
    String url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname;
    con = DriverManager.getConnection(url, user, passw);    
  }

  public void cerrar() throws SQLException {
    if (con != null) {
      con.close();
    }
  }

  public PreparedStatement prepareStatement(String sql) throws SQLException {
    return con.prepareStatement(sql);
  }

  public void autoCommit(boolean commit) throws SQLException {
    if (con != null) {
      con.setAutoCommit(commit);
    }
  }

  public void Commit() throws SQLException {
    if (con != null) {
      con.commit();
    }
  }

  public void Rollback() throws SQLException {
    if (con != null) {
      con.rollback();
    }
  }

}
