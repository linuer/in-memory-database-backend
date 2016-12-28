/**
 * Created by 13987 on 2016/12/26.
 */
public class FuturePriceThread extends Thread {
    private Long futureId = (long)3;

    public FuturePriceThread(Long futureId) {
        this.futureId = futureId;
    }

    @Override
    public void run() {
        FuturePrice futurePrice = new FuturePrice(futureId);
        while (true) {
            futurePrice.randPrice();
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
