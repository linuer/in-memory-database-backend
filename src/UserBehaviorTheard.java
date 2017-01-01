import java.sql.SQLException;
import java.util.Random;

/**
 * Created by 13987 on 2016/12/31.
 */
public class UserBehaviorTheard extends Thread {
    private Long userID;
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public UserBehaviorTheard(Long userID) {
        this.userID = userID;
    }

    @Override
    public void run() {
        UserBehavior userBehavior = new UserBehavior();
        for (long i = userID; i != userID + 40; i++) {
                try {
                    Random random = new Random();
                    int num = random.nextInt(3);
                    userBehavior.setMaxFutureNum(num);
                    userBehavior.trade(i);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
