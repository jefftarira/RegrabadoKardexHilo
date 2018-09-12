package RegrabadoKardex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexionMysql {
  
  // DESARROLLO
    private final String SERVER = "localhost";
    private final String DBNAME = "plastimet";
    private final String PORT = "3306";
    private final String USER = "user";
    private final String PASSW = "12345678";
  
//  private String server = "192.168.0.7";
//  private final String dbname = "plastimet";
//  private final String port = "3306";
//  private final String user = "user";
//  private final String passw = "PlastDbUser@1984";
  
//  PRODUCCION
//  private String server = "localhost";
//  private final String dbname = "plastimet";
//  private final String port = "3306";
//  private final String user = "user";
//  private final String passw = "PlastDbUser@1984";

  private Connection con;

  public ConexionMysql() {
    con = null;
  }

  public void conectar() throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    String url = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DBNAME;
    con = DriverManager.getConnection(url, USER, PASSW);
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
