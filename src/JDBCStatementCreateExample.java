
import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.timesten.jdbc.TimesTenCallableStatement;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.ucp.jdbc.PoolDataSourceFactory;
import oracle.ucp.jdbc.PoolDataSource;
import org.apache.commons.lang3.time.DateUtils;

public class JDBCStatementCreateExample {

    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@10.60.42.203:1521:orcl";
    private static final String DB_USER = "tthr";
    private static final String DB_PASSWORD = "tthr";
    private static final DateFormat dateFormat = new SimpleDateFormat(
            "yyyy/MM/dd HH:mm:ss");
    public static PoolDataSource pds = null;

    public static String CREATE_USER_WITH_FUND = "{call CREATE_USER_WITH_FUND(?, ?,?,?,?,?,?)}";
    public static String CREATE_FUTURE = "{call CREATE_FUTURE(?, ?, ?)}";

    public static void main(String[] argv) throws ParseException, SQLException {
//        dropTable();
        pds = PoolDataSourceFactory.getPoolDataSource();
        pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
        pds.setURL(DB_CONNECTION);
        pds.setUser(DB_USER);
        pds.setPassword(DB_PASSWORD);
        //Setting pool properties
        pds.setInitialPoolSize(5);
        pds.setMinPoolSize(5);
        pds.setMaxPoolSize(300);


//        createUserTable();
//        createFutureContractTable();
//        createTradeRecordTable();
//        createFUTURE_PRICETable();
//        createHolderTable();
//        createFUTURE_PRICETable();

        //产生数据到1月4号中午2点
//        for (int i = 1; i != 102; i++) {
//            FuturePriceThread futurePriceThread = new FuturePriceThread((long) i);
//            futurePriceThread.start();
//        }

        //从当前时间开始每秒产生
        java.util.Date nowDate = new java.util.Date();
        PerSecond perSecond = new PerSecond(nowDate);
        while (true){
            perSecond.run();
        }

//        for (int i = 0; i != 600; i++) {
//            User user =new User();
//            user.generateAll();
//            insertUser(user);
//        }
//        for (long j = 1; j < 601; j += 40) {
//            UserBehaviorTheard userBehaviorTheard = new UserBehaviorTheard(j);
//            userBehaviorTheard.start();
//        }
//
//        for (long i = 1; i !=100; i++) {
//            HolderThread holderThread = new HolderThread(i);
//            holderThread.start();
//        }
//        for (long i = 1; i != 1203; i++) {
//            HolderThread holderThread = new HolderThread(i);
//            holderThread.start();
//        }
//        System.out.println("finish init!");
//            FutureContract futureContract = new FutureContract();
//            insertFutureContract(futureContract);
//        for (int i = 0; i != 200; i++) {
//            UserThread userThread =  new UserThread();
//            userThread.start();
//        }
    }


    static int reportSQLExceptions(SQLException ex) {

        int errCount = 0;
        if (ex != null) {
            System.err.println("\n--- SQLException caught ---");
            ex.printStackTrace();

            while (ex != null) {
                System.err.println("SQL State: " + ex.getSQLState());
                System.err.println("Message: " + ex.getMessage());
                System.err.println("Error Code: " + ex.getErrorCode());
                errCount++;
                ex = ex.getNextException();
                System.err.println();
            }
        }

        return errCount;
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
            dbConnection = ConnecetUtils.getDBConnection();
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
            dbConnection = ConnecetUtils.getDBConnection();
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


    private static void insertFutureContract(FutureContract futureContract) throws ParseException, SQLException {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getDBConnection();
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

    public static void insertUser(User user) throws ParseException, SQLException {
        Connection connection = ConnecetUtils.getDBConnection();
        try {
            OracleCallableStatement callableStatement = (OracleCallableStatement) connection.prepareCall(CREATE_USER_WITH_FUND);
            callableStatement.setString(1, user.getUserName());
            callableStatement.setString(2, user.getIdentity());
            callableStatement.setString(3, user.getGender());
            callableStatement.setString(4, user.getTelphone());
            callableStatement.setString(5, user.getPwd());
            callableStatement.setFloat(6, user.getFund());
            callableStatement.registerOutParameter(7, OracleTypes.NUMBER);
            callableStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    static void insertUser(User user) throws ParseException, SQLException {
//        ConnecetUtils connecetUtils = new ConnecetUtils();
//        Connection conn = ConnecetUtils.getDBConnection();
//        try {
//            String sql = "INSERT INTO USER_TABLE ( USER_NAME,USER_IDENTITY,GENDER,Telephone,User_password,FUND) VALUES (?,?,?,?,?,?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, user.getUserName());
//            ps.setString(2, user.getIdentity());
//            ps.setString(3, user.getGender());
//            ps.setString(4, user.getTelphone());
//            ps.setString(5, user.getPwd());
//            ps.setFloat(6, user.getFund());
////            ps.setLong(7,1);
//            ps.executeUpdate();
//            ps.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

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
            conn = ConnecetUtils.getDBConnection();
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
                + "record_ID  NUMBER(20),"
                + "USER_ID  NUMBER(20),"
                + "FUTURE_ID NUMBER(20), "
                + "PRICE NUMBER(10),"
                + "TRADESTATE NUMBER(5),"
                + "TRADE_TYPE NUMBER(5),"
                + "AMOUNT NUMBER(10),"
                + "TRADE_TIME DATE,"
                + "PRIMARY KEY (record_ID), "
                + "FOREIGN KEY (USER_ID) REFERENCES USER_TABLE, "
                + "FOREIGN KEY (FUTURE_ID) REFERENCES FUTURE_CONTRACT "
                + ")";
        try {
            dbConnection = ConnecetUtils.getDBConnection();
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
                + "USER_ID  NUMBER(20),"
                + "USER_NAME VARCHAR(20), "
                + "USER_IDENTITY CHAR(18),"
                + "GENDER CHAR(8),"
                + "Telephone CHAR(20),"
                + "User_password CHAR(20),"
                + "FUND NUMBER(12,2),"
                + "PRIMARY KEY (USER_ID) "
                + ")";
        try {
            dbConnection = ConnecetUtils.getDBConnection();
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
                + "FUTURE_ID  NUMBER(20),"
                + "CONTRACT_NAME VARCHAR2(20), "
                + "DELIVERY_STATE NUMBER(2),"
                + "CONTRACT DATE,"
                + "PRIMARY KEY (FUTURE_ID) "
                + ")";
        try {
            dbConnection = ConnecetUtils.getDBConnection();
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
            dbConnection = ConnecetUtils.getDBConnection();
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