import java.sql.Date;

/**
 * Created by 13987 on 2016/12/27.
 */
public class Time_Price {
    private java.sql.Date time;
    private float price;

    public Time_Price() {
    }

    public Time_Price(Date time, float price) {
        this.time = time;
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
