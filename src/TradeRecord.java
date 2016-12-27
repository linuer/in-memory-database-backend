import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 13987 on 2016/12/27.
 */
public class TradeRecord {
    private Long userId = (long) 0;
    private Long nowContractID = (long) 0;
    private float price;
    private int tradeState;
    private int type;
    private int amount;
    private Date time;

    public Long getUserId() {
        return userId;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTradeState() {
        return tradeState;
    }

    public void setTradeState(int tradeState) {
        this.tradeState = tradeState;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    //需要从数据库中进行查询，根据2个ID，获得amount
    public void selcetAmount(Long userId, Long nowContractID) {

    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
