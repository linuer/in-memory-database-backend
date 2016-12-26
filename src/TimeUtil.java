import java.util.Random;

/**
 * Created by 13987 on 2016/12/24.
 */
public class TimeUtil {
    private String timeResult;
    public String randTime() {
        String year;
        String month;
        String day;
        String hour;
        String minute;
        String second;
        String sss;
        String result;
        Random r1 = new Random();
        float num = r1.nextFloat();
        if (num < 0.5) {
            year = "2017";
        } else {
            year = "2018";
        }
        Random r2 = new Random();
        int num2 = 1 + (int) (r2.nextFloat() * 12);
        month = String.valueOf(num2);
        Random r3 = new Random();
        int num3 = 1 + (int) (r3.nextFloat() * 30);
        day = String.valueOf(num3);
        int num4 = (int) (r3.nextFloat() * 60);
        hour = String.valueOf(num4);
        int num5 = (int) (r3.nextFloat() * 60);
        minute = String.valueOf(num5);
        int num6 = (int) (r3.nextFloat() * 60);
        second = String.valueOf(num6);
        int num7 = (int) (r3.nextFloat() * 200);
        sss=String.valueOf(num7);
        result = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second+" "+sss;
        return result;
    }

    public String getTimeResult() {
        return timeResult;
    }

    public void setTimeResult(String timeResult) {
        this.timeResult = timeResult;
    }
}
