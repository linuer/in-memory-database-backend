import java.util.Date;

/**
 * Created by 13987 on 2016/12/27.
 */
public class TradeRecord {
    public static Long userId = (long) 0;
    public static Long nowContractID = (long) 0;
    private float price;
    private int tradeState;
    private int type;
    private int amount;
    private Date time;

    public TradeRecord(float price, int tradeState, int type, int amount, Date time) {
        this.price = price;
        this.tradeState = tradeState;
        this.type = type;
        this.amount = amount;
        this.time = time;
    }

    public TradeRecord() {
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        TradeRecord.userId = userId;
    }

    public static Long getNowContractID() {
        return nowContractID;
    }

    public static void setNowContractID(Long nowContractID) {
        TradeRecord.nowContractID = nowContractID;
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
