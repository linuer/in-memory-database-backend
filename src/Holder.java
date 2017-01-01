import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 13987 on 2016/12/27.
 */
public class Holder {
    private Long userId = (long) 0;
    private Long nowContractID = (long) 0;
    //用户总共持有该期货的数量
    private int amount;

    public Long getUserId() {
        return userId;
    }

    public Holder(Long userId, Long nowContractID) throws SQLException {
        selectRecordsFromTable(userId, nowContractID);
        this.userId = userId;
        this.nowContractID = nowContractID;
    }

    public Holder(Long userId, Long nowContractID, int amount) {
        this.userId = userId;
        this.nowContractID = nowContractID;
        this.amount = amount;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getNowContractID() {
        return nowContractID;
    }

    public void setNowContractID(Long nowContractID) {
        this.nowContractID = nowContractID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Holder() {
    }


    public void selectRecordsFromTable(Long userId, Long nowContractID) throws SQLException {
        Connection dbConnection = null;
        PreparedStatement preparedStatement = null;
        String selectSQL = "SELECT * FROM HOLDER WHERE USER_ID = ? AND FUTURE_ID = ? ";
        try {
            dbConnection = JDBCStatementCreateExample.pds.getConnection();
            preparedStatement = dbConnection.prepareStatement(selectSQL);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, nowContractID);
            // execute select SQL stetement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int amount = rs.getInt("NUMBER");
                System.out.println("AMOUNT : " + amount);
                this.setAmount(amount);
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
    }
    public void initHolderTable(Long userId,Long futureId) throws SQLException {
        ConnecetUtils connecetUtils = new ConnecetUtils();
        Connection conn = JDBCStatementCreateExample.pds.getConnection();
        try {
            String selectUserSQL = "INSERT INTO HOLDER (USER_ID,FUTURE_ID,AMOUNT) VALUES (?,?,?)";
            PreparedStatement ps = conn.prepareStatement(selectUserSQL);
            ps.setLong(1, userId);
            ps.setLong(2, futureId);
            ps.setInt(3, 0);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
