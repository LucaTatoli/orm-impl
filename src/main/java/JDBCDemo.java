import java.sql.*;

public class JDBCDemo
{

    public static void main(String... args)
    {
        String driver = "org.postgresql.Driver";
        String url = "jdbc:postgresql://localhost:5432/TEST";
        try
        {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, "luca", "1234");
            String query = "select * from test_schema.users where cognome = ?;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "Masiero");
            preparedStatement.execute();
            ResultSet result = preparedStatement.getResultSet();

            ResultSetMetaData rsmd = result.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            for(int i = 1; i <= columnsNumber; i++)
            {
                if(i > 1)
                    System.out.print("\t\t");
                System.out.print(rsmd.getColumnName(i).toUpperCase());
            }
            System.out.println("");

            while (result.next())
            {
                for (int i = 1; i <= columnsNumber; i++)
                {
                    if (i > 1) System.out.print("\t\t");
                    String columnValue = result.getString(i);
                    System.out.print(columnValue);
                }
                System.out.println("");
            }

            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
