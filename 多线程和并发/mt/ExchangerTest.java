import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于实现两个人之间的数据交换，每个人在完成一定的事物后想与对方交换数据，第一个先拿出数据的人将
 * 一直等待第二个人拿着数据到来时，才能彼此交换数据。
 * @author Administrator
 *
 */
public class ExchangerTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Exchanger exchanger = new Exchanger();
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    String data1 = "aaa";
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "正在把数据" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "换回的数据为 " + data2);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });
        executorService.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    String data1 = "bbb";
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "正在把数据" + data1 + "换出去");
                    Thread.sleep((long) (Math.random() * 10000));
                    String data2 = (String) exchanger.exchange(data1);
                    System.out.println("线程" + Thread.currentThread().getName()
                            + "换回的数据为 " + data2);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        });

        executorService.shutdown();
    }

}
