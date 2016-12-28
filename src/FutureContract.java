import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * Created by 13987 on 2016/12/24.
 */
public class FutureContract {
    //PK主码
    private String contractName;
    private String endDay;
    //是否还能交易
    private int deliveryState;
    //期货截止期限,精确到天
    private java.sql.Date contract;

    public FutureContract() throws ParseException {
        randomName();
        endTime();
        deliveryState = 1;
    }


    private void contractState() {
        deliveryState = 1;
    }


    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }


    public int getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(int deliveryState) {
        this.deliveryState = deliveryState;
    }


    public String getEndDay() {
        return endDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public Date getContract() {
        return contract;
    }

    public void setContract(Date contract) {
        this.contract = contract;
    }

    public void randomName() throws ParseException {
        Random r1 = new Random();
        int num1 = (int) (r1.nextFloat() * 24);
        char keyBoard1 = 'A';
        char keyBoard2 = 'A';
        keyBoard1 += num1;
        Random r2 = new Random();
        int num2 = (int) (r2.nextFloat() * 24);
        keyBoard2 += num2;
        String str1 = String.valueOf(keyBoard1);
        String str2 = String.valueOf(keyBoard2);
        str1 += str2;
        str1 += endTime();
        str1 = str1.substring(0, 12);
        this.contractName = str1;
//        return str1;
    }


    private Date endTime() throws ParseException {
        Random r1 = new Random();
        String year;
        String month;
        String day;
        float num = r1.nextFloat();
        if (num < 0.5) {
            year = "2017";
        } else {
            year = "2018";
        }
        Random r2 = new Random();
        int num2 = 1 + (int) (r2.nextFloat() * 12);
        month = String.valueOf(num2);
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM");
        java.util.Date date1 = dateFormat1.parse(year + "-" + month + "-" + "30");
        java.sql.Date date2 = new java.sql.Date(date1.getTime());
        contract = date2;
        return date2;

    }

}
