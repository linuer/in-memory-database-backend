import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 13987 on 2016/12/27.
 */
//模拟用户行为
public class UserBehavior {
    //数据库中最大期货数量
    private int maxFutureNum;
    private java.sql.Date tradeTime;

    //买卖期货
    public void trade() throws SQLException {
        //创建一出一个用户
        //这个用户的ID，已经递增过了
        User user = new User();
        Random r1 = new Random();
        //不同期货尝试交易的次数
        int tradeTryTimes = 1 + (int) (r1.nextFloat() * 12);
        for (int i = 0; i != tradeTryTimes; i++) {
            //从数据库中随机获取几个FuturePrice内容
            Random r2 = new Random();
            //id从1到maxFutureNum
            int id = 1 + (int) (r2.nextFloat() * maxFutureNum);
            //交易时间
            Random r3 = new Random();
            int times = 1 + (int) (r3.nextFloat() * 40);
            ArrayList<Time_Price> time_prices = selectRecordsFromTable(id, times);
            //对于查询到的某一期货的list
            for (int j = 0; j != time_prices.size(); j++) {
                Time_Price time_price = time_prices.get(j);
                //是买还是卖
                Random r4 = new Random();
                int type = r4.nextInt(2);
                Date date = time_price.getTime();
                float price = time_price.getPrice();
                TradeRecord tradeRecord = new TradeRecord();
                tradeRecord.setPrice(price);
                tradeRecord.setTime(date);
                tradeRecord.setType(type);
                tradeRecord.setUserId(user.getUserId());
                tradeRecord.setNowContractID(time_price.getContractID());
                Random r5 = new Random();
                //之前的数量
                Holder holder = new Holder(user.getUserId(), time_price.getContractID());
                //只有当买/卖成功的时候，才更新holder里面的数值
                //用户原来持有的amount
                int oldAmount = holder.getAmount();
                int amount = r5.nextInt(200);
                tradeRecord.setAmount(amount);
                float fee = amount * price;
                //买
                if (type == 0) {
                    float nowFund = user.getFund();
                    int nowAmount = oldAmount + amount;
                    nowFund -= fee;
                    if (nowFund > 0) {
                        tradeRecord.setTradeState(1);
                        holder.setAmount(nowAmount);
                        user.setFund(nowFund);
                    } else {
                        tradeRecord.setTradeState(0);
                    }
                }
                //卖出
                else {
                    float nowFund = user.getFund();
                    int nowAmount = oldAmount - amount;
                    if (nowAmount > 0) {
                        nowFund += fee;
                        tradeRecord.setTradeState(1);
                        holder.setAmount(nowAmount);
                        user.setFund(nowFund);
                    } else {
                        tradeRecord.setTradeState(0);
                    }
                }
                //写回数据库
                saveTradeRecord(tradeRecord);
                updateHolderTabel(holder);
                updateUserTabel(user);
            }
        }
    }

    //Holder表更新
    private void updateHolderTabel(Holder holder) {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getConn();
        try {
            String updateTableSQL = "UPDATE HOLDER SET AMOUNT = ? "
                    + " WHERE USER_ID = ? AND FUTURE_ID = ?";
            PreparedStatement ps = conn.prepareStatement(updateTableSQL);
            ps.setInt(1, holder.getAmount());
            ps.setLong(2, holder.getUserId());
            ps.setLong(3, holder.getNowContractID());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //User表更新
    private void updateUserTabel(User user) {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getConn();
        try {
            String updateTableSQL = "UPDATE USER_TABLE SET FUND = ? "
                    + " WHERE USER_ID = ?";
            PreparedStatement ps = conn.prepareStatement(updateTableSQL);
            ps.setFloat(1, user.getFund());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTradeRecord(TradeRecord tradeRecord) {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = ConnecetUtils.getConn();
        try {
            String sql = "INSERT INTO TRADE_RECORD(USER_ID, FUTURE_ID,PRICE,TRADESTATE,TRADE_TYPE,AMOUNT,TRADE_TIME) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, tradeRecord.getUserId());
            ps.setLong(2, tradeRecord.getNowContractID());
            ps.setFloat(3, tradeRecord.getPrice());
            ps.setInt(4, tradeRecord.getTradeState());
            ps.setInt(5, tradeRecord.getType());
            ps.setInt(6, tradeRecord.getAmount());
            Date date = new java.sql.Date(tradeRecord.getTime().getTime());
            ps.setDate(7, date);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getMaxFutureNum() {
        return maxFutureNum;
    }

    public void setMaxFutureNum(int maxFutureNum) {
        this.maxFutureNum = maxFutureNum;
    }

    //更新用户资金
    //更新fund数值

    //查询期货价格表
    public static ArrayList<Time_Price> selectRecordsFromTable(int id, int times) throws SQLException {
        ArrayList<Time_Price> time_prices = new ArrayList<>();
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        int counter = 0;
        String selectSQL = "SELECT * FROM FUTURE_PRICE WHERE FUTURE_ID = ?";
        try {
            ConnecetUtils connecetUtils = new ConnecetUtils();
            dbConnection = ConnecetUtils.getConn();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, id);
            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Date time = rs.getDate("TRADE_TIME");
                Long futureID = rs.getLong("FUTURE_ID");
                float futurePrice = rs.getFloat("FUTURE_PRICE");
                System.out.println("TRADE_TIME : " + time.getTime());
                System.out.println("FUTURE_ID : " + futureID);
                System.out.println("FUTURE_PRICE : " + futurePrice);
                Time_Price time_price = new Time_Price();
                time_price.setPrice(futurePrice);
                time_price.setTime(time);
                time_price.setContractID(futureID);
                time_prices.add(time_price);
                counter += 1;
                if (counter == times) {
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return time_prices;
    }

    //从数据库中根据Id，查询出一个User
    private User getUser(Long id) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT * FROM USER_TABLE WHERE USER_ID = ?";
        User user = new User();
        try {
            ConnecetUtils connecetUtils = new ConnecetUtils();
            dbConnection = ConnecetUtils.getConn();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setUserId(id);
                user.setUserName(rs.getString("USER_NAME"));
                user.setGender(rs.getString("GENDER"));
                user.setFund(rs.getFloat("FUND"));
                user.setIdentity(rs.getString("USER_IDENTITY"));
                user.setPwd(rs.getString("User_password"));
                user.setTelphone(rs.getString("Telephone"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return user;
    }
}
