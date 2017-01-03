import org.apache.commons.lang3.time.DateUtils;

import java.sql.*;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 13987 on 2016/12/24.
 */
public class FuturePrice {
    private java.sql.Date tradeTime;
    private Long futureID = (long) 3;

    private java.util.Date sTime;
    private java.util.Date endTime;
    private java.util.Date nowTime;

    public java.util.Date getsTime() {
        return sTime;
    }

    public void setsTime(java.util.Date sTime) {
        this.sTime = sTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public java.util.Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(java.util.Date nowTime) {
        this.nowTime = nowTime;
    }

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

    public FuturePrice(Long futureID) {
        this.futureID = futureID;
        increase = 0;
        BuildFuturePrice();
    }

    public FuturePrice() {
        futureID += 1;
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

    //
//    public void randPrice() throws SQLException {
//        float price = getDefaultPrice();
//        float num = (float) (Math.random());
//        if (increase == 0) {
//            increaseTimes();
//        }
//        while (0 < increase) {
//            increase--;
//            price += (price * (num - 0.2) * 0.005);
//            if (price < 0) {
//                continue;
//            }
//            java.util.Date date1 = new java.util.Date();
//            tradeTime = new java.sql.Date(date1.getTime());
//            setFuturePrice(price);
//            queryFuturePrice(tradeTime, futureID, futurePrice);
//        }
//        increaseTimes();
//        while (0 < increase) {
//            increase--;
//            price += (price * (num - 0.8) * 0.005);
//            if (price < 0) {
//                continue;
//            }
//            java.util.Date date1 = new java.util.Date();
//            tradeTime = new java.sql.Date(date1.getTime());
//            System.out.println(price);
//            setFuturePrice(price);
//            queryFuturePrice(tradeTime, futureID, futurePrice);
//        }
//    }
    public void generateTime(java.util.Date startTime, java.util.Date endTime) throws SQLException, ParseException {
        while (startTime.getTime() <= endTime.getTime()) {
            nowTime = startTime;
            nowTime = DateUtils.addHours(nowTime, 10);
            java.util.Date nowDayEndTime = DateUtils.addHours(nowTime, 4);
            while (nowTime.getTime() <= nowDayEndTime.getTime()) {
                randPrice(nowTime);
                nowTime = DateUtils.addMinutes(nowTime, 1);
            }
            startTime = DateUtils.addDays(startTime, 1);
        }
    }

    public void randPrice(java.util.Date date) throws SQLException, ParseException {
        float num = (float) (Math.random());
        float price = getDefaultPrice();
        //价格曲线开始开始时间
        tradeTime = new java.sql.Date(date.getTime());
        setFuturePrice(price);
        futurePrice += (futurePrice * (num - 0.5) * 0.01);
        System.out.println(tradeTime);
        queryFuturePrice(tradeTime, futureID, futurePrice);
    }


    private int increaseTimes() {
        Random r = new Random();
        increase = r.nextInt(2);
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
    private void queryFuturePrice(java.sql.Date tradeTime, Long futureID, float futurePrice) throws SQLException {
        Connection conn = ConnecetUtils.getDBConnection();
//        Connection conn = ConnecetUtils.getConn();
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

    public void updatePer(Long f_id, java.util.Date time) throws SQLException {
        float num = (float) (Math.random());
        float price = getDefaultPrice();
        tradeTime = new java.sql.Date(time.getTime());
        setFuturePrice(price);
        futurePrice += (futurePrice * (num - 0.5) * 0.1);
        System.out.println(tradeTime);
        queryFuturePrice(tradeTime, f_id, futurePrice);
    }
}
