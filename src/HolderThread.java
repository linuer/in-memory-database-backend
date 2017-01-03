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
        for (long j = 1; j != 101; j++) {
            try {
                holder.initHolderTable(userID, j);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (j % 9 == 0) {
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
