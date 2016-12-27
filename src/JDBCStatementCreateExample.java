
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JDBCStatementCreateExample {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//10.60.42.202:1521/pdborcl.gc.com";
    private static final String DB_USER = "C##APP";
    private static final String DB_PASSWORD = "gcers";
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");

    public static void main(String[] argv) throws ParseException, SQLException {
//        createFUTURE_PRICETable();
//        FuturePriceThread futurePriceThread = new FuturePriceThread();
//        futurePriceThread.start();
        UserBehavior userBehavior =new UserBehavior();
        userBehavior.setMaxFutureNum(1);
        userBehavior.trade();
//        FuturePriceThread futurePriceThread2 = new FuturePriceThread();
//        futurePriceThread2.start();
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


    private static void createFUTURE_PRICETable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE FUTURE_PRICE("
                + "TRADE_TIME DATE, "
                + "FUTURE_ID NUMBER(20), "
                + "FUTURE_PRICE NUMBER(24),"
                + "PRIMARY KEY (TRADE_TIME,FUTURE_ID) "
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


    private static void queryFutureContract() throws ParseException {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getConn();
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

    //删除表
    public static void dropTable() {
        Connection conn = null;
        Statement stmt = null;
        try {
            ConnecetUtils connecetUtils = new ConnecetUtils();
            conn = ConnecetUtils.getConn();
            stmt = conn.createStatement();
            String sql = "DROP TABLE FUTURE_PRICE ";
            stmt.executeUpdate(sql);
            System.out.println("Table  deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}