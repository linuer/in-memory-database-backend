import java.sql.SQLException;
import java.util.Date;

/**
 * Created by 13987 on 2017/1/3.
 */
public class PerSecond extends Thread {
    private Date date;

    public PerSecond(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PerSecond() {
    }

    @Override
    public void run() {
        FuturePrice futurePrice = new FuturePrice();
        int maxFutureNumber = 101;
        for (long i = 0; i != maxFutureNumber; i++) {
            try {
                futurePrice.updatePer(i + 1,this.date);
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
