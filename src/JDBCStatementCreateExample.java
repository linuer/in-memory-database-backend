
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JDBCStatementCreateExample {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//10.60.42.202:1521/pdborcl.gc.com";
    private static final String DB_USER = "C##APP";
    private static final String DB_PASSWORD = "gcers";
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");

    public static void main(String[] argv) throws ParseException,SQLException {
//        createDbUserTable();
        FuturePriceThread futurePriceThread = new FuturePriceThread();
        futurePriceThread.start();
//        queryFutureContract();
//        for (int i = 0; i < 10; i++){
//            User user = new User();
//            System.out.println(user.getFund());
//            System.out.println(user.getIdentity());
//            System.out.println(user.getGender());
//            System.out.println(user.getTelphone());
//            System.out.println(user.getUserId());
//            System.out.println(user.getUserName());
//        }
//        try {
//            queryFutureContract();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }



    private static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE FUTURE_PRICE("
                + "TRADE_TIME DATE, "
                + "FUTURE_ID NUMBER(20), "
                + "FUTURE_PRICE NUMBER(24)"
                + ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(createTableSQL);
            // execute the SQL stetement
            statement.execute(createTableSQL);
            System.out.println("Table \"Future price\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static void insertRecordIntoDbUserTable() throws SQLException, ParseException {

        Connection dbConnection = null;
        Statement statement = null;
        FutureContract futureContract = new FutureContract();
        java.sql.Date date = futureContract.getContract();
        String insertTableSQL = "INSERT INTO DBUSER"
                + "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
                + "(2,'mkyong','system', " + "to_date('"
                + getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println(insertTableSQL);

            // execute insert SQL stetement
            statement.executeUpdate(insertTableSQL);

            System.out.println("Record is inserted into DBUSER table!");

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(
                    DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    private static Connection getConn() {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@//10.60.42.202:1521/pdborcl.gc.com";
        String username = "C##APP";// 用户名
        String password = "gcers";// 密码
        Connection conn = null; // 创建数据库连接对象
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static void queryFutureContract() throws ParseException {
        Connection conn = getConn();
        try {
            String sql = "INSERT INTO FUTURECONTRACT (CONTRACT_ID, CONTRACT_NAME,FUTURE_DESCIRPTION,DELIVERY_STATE,CONTRACT) VALUES (?, ?, ?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            FutureContract futureContract = new FutureContract();
            ps.setInt(1, 6);
            ps.setString(2, futureContract.getContractName());
            ps.setString(3, futureContract.getContractName());
            ps.setInt(4, 1);
            ps.setDate(5, futureContract.getContract());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return dateFormat.format(today.getTime());
    }
}