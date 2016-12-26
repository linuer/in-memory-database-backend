/**
 * Created by 13987 on 2016/12/26.
 */
public class FuturePriceThread extends Thread {
    public void run(){
        FuturePrice futurePrice = new FuturePrice();
        while (true){
            futurePrice.randPrice();
        }
    }
}
