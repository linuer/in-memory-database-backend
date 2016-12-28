/**
 * Created by 13987 on 2016/12/26.
 */
public class FuturePriceThread extends Thread {
    public void run(Long i) {
        FuturePrice futurePrice = new FuturePrice(i);
        while (true) {
            futurePrice.randPrice();
        }
    }
}
