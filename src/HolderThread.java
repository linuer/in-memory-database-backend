import java.sql.SQLException;

/**
 * Created by 13987 on 2016/12/30.
 */
public class HolderThread extends Thread {
    private Long userID;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public HolderThread(Long userID) {
        this.userID = userID;
    }

    @Override
    public void run() {
        Holder holder = new Holder();
        for (long i = userID; i != userID + 39; i++) {
            for (long j = 3; j != 405; j++) {
                try {
                    holder.initHolderTable(i, j);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
