import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 13987 on 2017/1/3.
 */
public class UserThread extends Thread {
    @Override
    public void run() {
        Date date1 = new Date();
        for (int i = 0; i != 10; i++) {
            User user = new User();
            user.generateAll();
            try {
                JDBCStatementCreateExample.insertUser(user);
            } catch (ParseException | SQLException e) {
                e.printStackTrace();
            }
        }
        Date date2 = new Date();
        System.out.println("起始时间"+date1.getTime());
        System.out.println("结束时间"+date2.getTime());
        System.out.println("时间查"+ (date2.getTime()-date1.getTime()));
    }

}
