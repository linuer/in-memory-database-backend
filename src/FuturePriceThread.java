import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 13987 on 2016/12/26.
 */
public class FuturePriceThread extends Thread {
    private Long futureId = (long) 1;

    public FuturePriceThread(Long futureId) {
        this.futureId = futureId;
    }

    @Override
    public void run() {
//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Integer year = 2017;
        Integer month = 1;
        Integer day = 4;
        Integer day2 = 4;
        Integer hour1 = 10;
        Integer hour2 = 16;
        java.lang.String str = year.toString() + "-" + month.toString() + "-" + day.toString();
        java.lang.String str2 = year.toString() + "-" + month.toString() + "-" + day2.toString();
        FuturePrice futurePrice = new FuturePrice(futureId);
        try {
            Date myDate1 = dateFormat2.parse(str);
            Date myDate2 = dateFormat2.parse(str2);
            futurePrice.generateTime(myDate1, myDate2);
        } catch (ParseException | SQLException e) {
            e.printStackTrace();
        }
    }
}
