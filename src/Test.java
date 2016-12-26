///**
// * Created by 13987 on 2016/12/24.
// */
//
//import java.sql.*;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Random;
//
//public class Test {
//    public Long TradeTimeID;
//
//    private static Connection getConn() {
//        String driver = "oracle.jdbc.driver.OracleDriver";
//        //String url = "jdbc:oracle:thin:@//10.60.42.202:1521/pdborcl.gc.com";// 设置连接字符串
//        String url = "jdbc:oracle:thin:@//10.60.42.202:1521/pdborcl.gc.com";
//        String username = "C##APP";// 用户名
//        String password = "gcers";// 密码
//        Connection conn = null; // 创建数据库连接对象
//        try {
//            Class.forName(driver);
//            conn = DriverManager.getConnection(url, username, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//
//        return conn;
//    }
//
//    private static void queryFutureContract() throws ParseException {
//        Connection conn = getConn();
//        try {
//            Statement statemenet = conn.createStatement();
//            for (int i = 0; i != 10; i++) {
//                FutureContract futureContract = new FutureContract();
//                String query = "insert into dataer (email, name) values('"
//                        + futureContract.getContractName() + "','" + futureContract.getContractName() + "')";
//                statemenet.addBatch(query);
//            }
//            statemenet.executeBatch();
//            statemenet.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//    /**
//     * 关闭数据库
//     * @param conn
//     */
//    private static void closeConnection(Connection conn){
//        try{
//            if(conn != null){
//                conn.close();
//            }
//        }
//        catch(Exception e){
//            System.out.println("数据库关闭失败");
//            e.printStackTrace();
//        }
//    }
//    public static int Qinsert(String insert){
//    Connection conn = getConn();
//    int re = 0;
//    try{
//        conn.setAutoCommit(false);//事物开始
//        Statement statemenet = conn.createStatement();
//        re = statemenet.executeUpdate(insert);
//        if(re < 0){               //插入失败
//            conn.rollback();      //回滚
//            statemenet.close();
//            closeConnection(conn);
//            return re;
//        }
//        conn.commit();            //插入正常
//        statemenet.close();
//        closeConnection(conn);
//        return re;
//    }
//    catch(Exception e){
//        e.printStackTrace();
//    }
//    closeConnection(conn);
//    return 0;
//
//}
//
////        final int batchSize = 1000;
////        int count = 0;
////        PreparedStatement pstmt;
////        try {
////            pstmt = conn.prepareStatement(sql);
////            while (++count % batchSize != 0) {
////                String query = "insert into employee (name, city) values('"
////                        + employee.getName() + "','" + employee.getCity + "')";
////                statemenet.addBatch(query);
////                FutureContract futureContract = new FutureContract();
////                pstmt.setString(1, futureContract.getContractName());
//////                pstmt.setInt(2, futureContract.getDeliveryState());
//////                pstmt.setDate(3, futureContract.getContract());
////            }
////            pstmt.addBatch();
////            pstmt.executeBatch();
////            pstmt.close();
////            conn.close();
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
//
//    private static void query() {
//        Connection conn = getConn();
//        String sql = "SELECT * FROM step";
//        PreparedStatement pstmt;
//        try {
//            pstmt = conn.prepareStatement(sql);
//            // 建立一个结果集,用来保存查询出来的结果
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                String username = rs.getString("stepid");
//                System.out.println(username);
//            }
//            rs.close();
//            pstmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws ParseException {
////        UnrepeatRandom unrepeatRandom = new UnrepeatRandom();
////        int[] result = UnrepeatRandom.randomCommon(1, 9999, 4);
////        int i = 0;
////        assert result != null;
////        for (i = 0; i != result.length; i++){
////            System.out.println(result[i]);
////        }
////        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
////       TimeUtil timeUtil =new TimeUtil();
////        String temp = timeUtil.randTime();
////        Date myDate2 = dateFormat2.parse(temp);
////        System.out.println(temp);
////        System.out.println(myDate2);
////        java.util.Date a = new java.util.Date();
////        java.sql.Timestamp b = new java.sql.Timestamp(myDate2.getTime());
////        System.out.println(b);
////        System.out.println(a);
////        System.out.println(b);
////        java.sql.Time c = new java.sql.Time(a.getTime());
////        System.out.println(c);
//
////        FuturePrice futurePrice =new FuturePrice();
////        futurePrice.BuildFuturePrice();
////        query();
////        queryFutureContract();
////        String insert = "insert into t_department values('D005','外交部')";
//        query();
////        FutureContract futureContract = new FutureContract();
//
////        System.out.println(futureContract.randomName());
//    }
//
//}
