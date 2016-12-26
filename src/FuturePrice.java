import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

/**
 * Created by 13987 on 2016/12/24.
 */
public class FuturePrice {
    private java.sql.Date tradeTime;
    private static Long futureID;

    public Long getFutureID() {
        return futureID;
    }

    public void setFutureID(Long futureID) {
        this.futureID = futureID;
    }

    private float futurePrice;
    private float defaultPrice;
    private int increase;

    public int getIncrease() {
        return increase;
    }

    public void setIncrease(int increase) {
        this.increase = increase;
    }

    public FuturePrice() {
        futureID = (long) 0;
        increase = 0;
        BuildFuturePrice();
    }

    public float getDefaultPrice() {
        return defaultPrice;
    }


    public void setDefaultPrice(float defaultPrice) {
        this.defaultPrice = defaultPrice;
    }


    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }


    public float getFuturePrice() {
        return futurePrice;
    }

    public void setFuturePrice(float futurePrice) {
        this.futurePrice = futurePrice;
    }

    public void randPrice()  {
        float price = getDefaultPrice();
        float num = (float) (Math.random());
        if (increase == 0) {
            increaseTimes();
        }
        while (0 < increase) {
            increase--;
            price += (price * (num - 0.2) * 0.005);
            if (price < 0) {
                continue;
            }
            java.util.Date date1 = new java.util.Date();
            tradeTime = new java.sql.Date(date1.getTime());
            System.out.println(price);
            setFuturePrice(price);
            futureID += 1;
            queryFuturePrice(tradeTime, futureID, futurePrice);
        }
        increaseTimes();
        while (0 < increase) {
            increase--;
            price += (price * (num - 0.8) * 0.005);
            if (price < 0) {
                continue;
            }
            java.util.Date date1 = new java.util.Date();
            tradeTime = new java.sql.Date(date1.getTime());
            System.out.println(price);
            setFuturePrice(price);
            futureID += 1;
            queryFuturePrice(tradeTime, futureID, futurePrice);
        }
    }

    private int increaseTimes() {
        Random r = new Random();
        increase = r.nextInt(25);
        return increase;
    }

    //价格曲线
    private void priceLine() {
        //5到60
        int times = (int) (Math.random() * 65) + 5;
        //70--840
        setDefaultPrice(times * 14);
        //在default的基础上 上下波动
    }

    public void BuildFuturePrice() {
        priceLine();
    }

    //访问数据库并且把价格写入进去
    private void writePrice() {

    }

    private void queryFuturePrice(java.sql.Date tradeTime, Long futureID, float futurePrice)  {
        Connection conn = getConn();
        try {
            String sql = "INSERT INTO FUTURE_PRICE(TRADE_TIME, FUTURE_ID,FUTURE_PRICE) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, tradeTime);
            ps.setLong(2, futureID);
            ps.setFloat(3, futurePrice);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
