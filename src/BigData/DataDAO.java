package BigData;

import BigData.Models.LogMarketsoft;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataDAO {

  private ConexionPostgres conP;
  private ConexionMysql conM;

  private String selectLog = "SELECT\n"
          + "  usuario,\n"
          + "  logdia,\n"
          + "  loghora,\n"
          + "  logsec,\n"
          + "  logpgm,\n"
          + "  logmode,\n"
          + "  logref,\n"
          + "  logdocnum,\n"
          + "  logvalor\n"
          + "FROM log ";

  private String insertLog = "INSERT INTO log2 (usuario, dia, hora, secuencia, pgm, mode, ref, docnum, valor)\n"
          + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";

  public DataDAO() {
    conP = new ConexionPostgres();
    conM = new ConexionMysql();
  }

  public ArrayList getLogMarketsoft() throws SQLException, ClassNotFoundException {
    ArrayList<LogMarketsoft> aData = new ArrayList<>();
    ResultSet rs;
    PreparedStatement ps;

    conP.conectar();
    ps = conP.prepareStatement(selectLog);
    rs = ps.executeQuery();
    while (rs.next()) {
      LogMarketsoft log = new LogMarketsoft(
              rs.getString("usuario"),
              rs.getDate("logdia"),
              rs.getString("loghora"),
              rs.getInt("logsec"),
              rs.getString("logpgm"),
              rs.getString("logmode"),
              rs.getString("logref"),
              rs.getInt("logdocnum"),
              rs.getBigDecimal("logvalor")
      );
      aData.add(log);
    }
    rs.close();
    conP.cerrar();
    return aData;
  }

  public int saveLogMysql(ArrayList<LogMarketsoft> data) throws ClassNotFoundException, SQLException {
    conM.conectar();
    conM.autoCommit(false);
    PreparedStatement ps;
    int count = 0;

    String insertLog2 = new String(insertLog);

    for (int x = 0; x < 99; x++) {
      insertLog += "\n ,(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    }

    int limitRows = 100;
    try {
      for (int i = 0; i < data.size();) {

        if ((data.size() - count) < 100) {
          limitRows = data.size() - count;
          for (int x = 0; x < (limitRows - 1); x++) {
            insertLog2 += "\n ,(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
          }
          insertLog = insertLog2;
        }
        ps = conM.prepareStatement(insertLog);
        int countParams = 0;
        for (int j = 1; j <= limitRows; j++) {
          LogMarketsoft l = data.get(i);
          System.out.println(i);

          ps.setString((++countParams), l.getUsuario().trim());
          ps.setDate(++countParams, l.getLogdia());
          ps.setString(++countParams, l.getLoghora().trim());
          ps.setInt(++countParams, l.getLogsec());
          ps.setString(++countParams, l.getLogpgm().trim());
          ps.setString(++countParams, l.getLogmode().trim());
          ps.setString(++countParams, l.getLogref().trim());
          ps.setInt(++countParams, l.getLogdocnum());
          ps.setBigDecimal(++countParams, l.getLogvalor());
          i++;
        }
        count += ps.executeUpdate();

      }
      conM.Commit();
      conM.cerrar();
      return count;
    } catch (Exception ex) {
      conM.Rollback();
      conM.cerrar();
      ex.printStackTrace();
      return count;
    }
  }

}
