/**
 * Created by 13987 on 2016/12/27.
 */
public class Holder {
    public static Long userId = (long) 0;
    public static Long nowContractID = (long) 0;
    //用户总共持有该期货的数量
    private int amount;

    public Holder(int amount) {
        this.amount = amount;
    }

    public static Long getUserId() {
        return userId;
    }

    public static void setUserId(Long userId) {
        Holder.userId = userId;
    }

    public static Long getNowContractID() {
        return nowContractID;
    }

    public static void setNowContractID(Long nowContractID) {
        Holder.nowContractID = nowContractID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
