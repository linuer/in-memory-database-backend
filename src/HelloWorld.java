import java.sql.*;

public class HelloWorld
{
    public static void main (String[]args)
    {
        try {
            Class.forName ("com.timesten.jdbc.TimesTenClientDriver");
        } catch (ClassNotFoundException ex)
        {
            System.out.println ("Class Not Found!");
        }
        try {
            String URL = "jdbc:timesten:client:dsn=timesten;uid=test;pwd=tiger;TTC_SERVER=10.60.42.202;TTC_SERVER_DSN=MY_TTDB;TCP_PORT=53397";
            int intValues = 12345;
            Connection conn = DriverManager.getConnection (URL);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE a(a int)");
            PreparedStatement pstmt = conn.prepareStatement ( "INSERT INTO a VALUES (?)" );
            pstmt.setInt(1, intValues);
            pstmt.executeUpdate();
            ResultSet rs = stmt.executeQuery("SELECT * FROM a");

            int numCols = rs.getMetaData().getColumnCount();

            System.out.println("Fetching data");
            System.out.println("Data:");
            while (rs.next()) {
                for (int i = 1; i <= numCols; i++) {
                    System.out.print("\t"+rs.getObject(i));
                }
                System.out.println();
            }
            stmt.close();
            pstmt.close();
            conn.close ();

        } catch (SQLException ex) {
            reportSQLExceptions (ex);
        }

    }

    static int reportSQLExceptions (SQLException ex)
    {

        int errCount = 0;
        if (ex != null) {
            System.err.println ("\n--- SQLException caught ---");
            ex.printStackTrace ();

            while (ex != null) {
                System.err.println ("SQL State: " + ex.getSQLState ());
                System.err.println ("Message: " + ex.getMessage ());
                System.err.println ("Error Code: " + ex.getErrorCode ());
                errCount++;
                ex = ex.getNextException ();
                System.err.println ();
            }
        }

        return errCount;
    }
}