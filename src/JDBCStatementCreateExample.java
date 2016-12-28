
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JDBCStatementCreateExample {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@//10.60.42.202:1521/timespdb";
    private static final String DB_USER = "C##TIMESTEN";
    private static final String DB_PASSWORD = "googlecamp";
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");

    public static void main(String[] argv) throws ParseException, SQLException {
//        dropTable();
//        createUserTable();
//        createFutureContractTable();
//        createTradeRecordTable();
//        createHolderTable();
//        createFUTURE_PRICETable();
            FuturePriceThread futurePriceThread = new FuturePriceThread();
            Long id = (long) 3;
            futurePriceThread.run(id);
        FuturePriceThread futurePriceThread2 = new FuturePriceThread();
        Long id2 = (long) 4;
        futurePriceThread2.run(id2);
    }


    private static void createFUTURE_PRICETable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE FUTURE_PRICE("
                + "TRADE_TIME DATE, "
                + "FUTURE_ID NUMBER(20),"
                + "FUTURE_PRICE NUMBER,"
                + "PRIMARY KEY (TRADE_TIME,FUTURE_ID), "
                + "FOREIGN KEY (FUTURE_ID) REFERENCES FUTURE_CONTRACT "
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


    private static void insertFutureContract(FutureContract futureContract) throws ParseException {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getConn();
        try {
            String sql = "INSERT INTO FUTURE_CONTRACT ( CONTRACT_NAME,DELIVERY_STATE,CONTRACT) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, futureContract.getContractName());
            ps.setInt(2, 1);
            ps.setDate(3, futureContract.getContract());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertUser(User user) throws ParseException {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getConn();
        try {
            String sql = "INSERT INTO USER_TABLE ( USER_NAME,USER_IDENTITY,GENDER,Telephone,User_password,FUND) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getIdentity());
            ps.setString(3, user.getGender());
            ps.setString(4, user.getTelphone());
            ps.setString(5, user.getPwd());
            ps.setFloat(6, user.getFund());
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
            String sql = "DROP TABLE FUTURE_CONTRACT ";
            stmt.executeUpdate(sql);
            System.out.println("Table  deleted in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void createTradeRecordTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE TRADE_RECORD("
                + "USER_ID  NUMBER(20),"
                + "FUTURE_ID NUMBER(20), "
                + "PRICE NUMBER(10),"
                + "TRADESTATE NUMBER(5),"
                + "TRADE_TYPE NUMBER(5),"
                + "AMOUNT NUMBER(10),"
                + "TRADE_TIME DATE,"
                + "PRIMARY KEY (USER_ID,FUTURE_ID), "
                + "FOREIGN KEY (USER_ID) REFERENCES USER_TABLE, "
                + "FOREIGN KEY (FUTURE_ID) REFERENCES FUTURE_CONTRACT "
                + ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(createTableSQL);
            // execute the SQL stetement
            statement.execute(createTableSQL);
            System.out.println("Table \"TradeRecord\" is created!");
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

    private static void createUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE USER_TABLE("
                + "USER_ID  NUMBER(20) GENERATED ALWAYS AS IDENTITY,"
                + "USER_NAME VARCHAR(20), "
                + "USER_IDENTITY CHAR(18),"
                + "GENDER CHAR(8),"
                + "Telephone CHAR(20),"
                + "User_password CHAR(20),"
                + "FUND NUMBER(12,2),"
                + "PRIMARY KEY (USER_ID) "
                + ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(createTableSQL);
            statement.execute(createTableSQL);
            System.out.println("Table \"User_Table\" is created!");
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

    private static void createFutureContractTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE FUTURE_CONTRACT("
                + "FUTURE_ID  NUMBER(20) GENERATED ALWAYS AS IDENTITY,"
                + "CONTRACT_NAME VARCHAR2(20), "
                + "DELIVERY_STATE NUMBER(2),"
                + "CONTRACT DATE,"
                + "PRIMARY KEY (FUTURE_ID) "
                + ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(createTableSQL);
            // execute the SQL stetement
            statement.execute(createTableSQL);
            System.out.println("Table \"FutureContract\" is created!");
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

    private static void createHolderTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String createTableSQL = "CREATE TABLE HOLDER("
                + "USER_ID  NUMBER(20),"
                + "FUTURE_ID NUMBER(20), "
                + "AMOUNT NUMBER(10),"
                + "PRIMARY KEY (USER_ID,FUTURE_ID),"
                + "FOREIGN KEY (USER_ID) REFERENCES USER_TABLE, "
                + "FOREIGN KEY (FUTURE_ID) REFERENCES FUTURE_CONTRACT "
                + ")";
        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();
            System.out.println(createTableSQL);
            // execute the SQL stetement
            statement.execute(createTableSQL);
            System.out.println("Table \"Holder\" is created!");
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
}