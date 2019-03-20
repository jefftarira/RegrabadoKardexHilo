package RegrabadoKardex.DB;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class ConexionPoolMysql {

  private static BasicDataSource basicDataSource;

  // DESARROLLO
//  private final String SERVER = "localhost";
//  private final String DBNAME = "plastimet";
//  private final String PORT = "3306";
//  private final String USER = "user";
//  private final String PASSW = "12345678";
//  private final String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DBNAME;
  
    // PRODUCCION
  private final String SERVER = "192.168.0.7";
  private final String DBNAME = "plastimet";
  private final String PORT = "3306";
  private final String USER = "user";
  private final String PASSW = "PlastDbUser@1984";
  private final String URL = "jdbc:mysql://" + SERVER + ":" + PORT + "/" + DBNAME;

  public ConexionPoolMysql() {
    inicializaDataSource();
  }

  private void inicializaDataSource() {
    if (basicDataSource == null) {
      basicDataSource = new BasicDataSource();
      basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
      basicDataSource.setUsername(USER);
      basicDataSource.setPassword(PASSW);
      basicDataSource.setUrl(URL);
      basicDataSource.setInitialSize(20);
      basicDataSource.setMaxTotal(50);
      basicDataSource.setMinIdle(20);
      basicDataSource.setMaxIdle(50);
      basicDataSource.setRemoveAbandonedOnBorrow(true);
      basicDataSource.setRemoveAbandonedTimeout(10000);
      basicDataSource.setMaxWaitMillis(5000);
      System.out.println("Inicializando pool de conexiones MYSQL");
    }
  }

  public BasicDataSource getDataSource() {
    if (basicDataSource == null) {
      inicializaDataSource();
    }
    return basicDataSource;
  }

}