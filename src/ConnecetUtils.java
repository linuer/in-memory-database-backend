import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by 13987 on 2016/12/26.
 */
public class ConnecetUtils {
    public static Connection getConn() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@10.60.42.203:1521:orcl";// 设置连接字符串
        String username = "tthr";// 用户名
        String password = "tthr";// 密码
        Connection conn = null; // 创建数据库连接对象
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
