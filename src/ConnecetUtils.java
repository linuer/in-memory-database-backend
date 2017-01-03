import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by 13987 on 2016/12/26.
 */
public class ConnecetUtils {
    static Connection getDBConnection() throws SQLException {
        return JDBCStatementCreateExample.pds.getConnection();
    }
}
