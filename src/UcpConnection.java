///**
// * Created by 13987 on 2016/12/30.
// */
//
//import java.sql.*;
//import oracle.ucp.jdbc.PoolDataSourceFactory;
//import oracle.ucp.jdbc.PoolDataSource;
//
//public class UcpConnection {
//    public static void main(String args[]) throws SQLException {
//        try {
//            //Creating a pool-enabled data source
//            PoolDataSource pds = PoolDataSourceFactory.getPoolDataSource();
//            //Setting connection properties of the data source
//            pds.setConnectionFactoryClassName("oracle.jdbc.pool.OracleDataSource");
//            pds.setURL("jdbc:oracle:thin:@//10.60.42.202:1521/timespdb");
//            pds.setUser("C##TIMESTEN");
//            pds.setPassword("googlecamp");
//            //Setting pool properties
//            pds.setInitialPoolSize(5);
//            pds.setMinPoolSize(5);
//            pds.setMaxPoolSize(10);
//            //Borrowing a connection from the pool
//            Connection conn = pds.getConnection();
//            System.out.println("\nConnection borrowed from the pool");
//            //Checking the number of available and borrowed connections
//            int avlConnCount = pds.getAvailableConnectionsCount();
//            System.out.println("\nAvailable connections: " + avlConnCount);
//            int brwConnCount = pds.getBorrowedConnectionsCount();
//            System.out.println("\nBorrowed connections: " + brwConnCount);
//            //Working with the connection
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM FUTURE_CONTRACT");
//            while (rs.next())
//                System.out.println("\nConnected as: " + rs.getString(1));
//            rs.close();
//            //Returning the connection to the pool
//            conn.close();
//            conn = null;
//            System.out.println("\nConnection returned to the pool");
//            //Checking the number of available and borrowed connections again
//            avlConnCount = pds.getAvailableConnectionsCount();
//            System.out.println("\nAvailable connections: " + avlConnCount);
//            brwConnCount = pds.getBorrowedConnectionsCount();
//            System.out.println("\nBorrowed connections: " + brwConnCount);
//        } catch (SQLException e) {
//            System.out.println("\nAn SQL exception occurred : " + e.getMessage());
//        }
//    }
//}