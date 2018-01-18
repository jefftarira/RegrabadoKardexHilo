package BigData;

import BigData.Models.LogMarketsoft;
import java.sql.SQLException;
import java.util.ArrayList;

public class BigData {

  public static void main(String[] args) throws SQLException, ClassNotFoundException {

    DataDAO dao = new DataDAO();
    ArrayList<LogMarketsoft> data = dao.getLogMarketsoft();

    System.out.println("Total registos capturados " + data.size());

    int count = dao.saveLogMysql(data);

    System.out.println("Total registos ingresados " + count);
  }

}
