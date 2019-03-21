package RegrabadoKardex.DB;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class PoolPostgres {

  private static BasicDataSource basicDataSource;

  // DESARROLLO
//  private final String SERVER = "192.168.0.15";
//  private final String DBNAME = "PlastimetV10P";
//  private final String PORT = "5432";
//  private final String USER = "postgres";
//  private final String PASSW = "Csdla2008!";

  // PRODUCCION
  private final String SERVER = "192.168.0.4";
  private final String DBNAME = "PlastimetV10P";
  private final String PORT = "5432";
  private final String USER = "postgres";
  private final String PASSW = "Csdla2008!";
  
  private final String URL = "jdbc:postgresql://" + SERVER + ":" + PORT + "/" + DBNAME;

  public PoolPostgres() {
    inicializaDataSource();
  }

  private void inicializaDataSource() {
    if (basicDataSource == null) {
      basicDataSource = new BasicDataSource();
      basicDataSource.setDriverClassName("org.postgresql.Driver");
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
      System.out.println("Inicializando pool de conexiones POSTGRES");
    }
  }

  public BasicDataSource getDataSource() {
    if (basicDataSource == null) {
      inicializaDataSource();
    }
    return basicDataSource;
  }

}
