package entitymanager;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestManager extends EntityManager {
    @Override
    protected Connection getConnection() {
        if(connection == null) {
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://localhost:5432/TEST";
            connection = null;
            try
            {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, "luca", "1234");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                return connection;
            }
        }
        else
        {
            connectionCount++;
            return connection;
        }
    }
}
