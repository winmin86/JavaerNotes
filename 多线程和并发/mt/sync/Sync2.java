package sync;

import java.util.Random;

/**
 * @ProjectName: JavaerNotes
 * @Package: sync
 * @ClassName: Sync2
 * @Description:
 * 通过synchronized对Object object=new Object();加锁 也叫对象锁 实现锁的互斥，当生产线程生产产品的时候会对object加锁
 * 消费者线程会进入阻塞状态 直到生产线程完成产品的生产释放锁 反之也是同样的道理。
 * 但是这样只能被动的唤醒线程的执行 可以使用wait和notify来进行主动唤醒线程继续执行
 *
 * @Author:
 * @CreateDate: 2019/2/15 13:41
 * @Version: 1.0
 */
public class Sync2 {
    public static void main(String[] args) {
        Object object = new Object();
        //开启生产
        new Producer(object).start();
        //开启消费
        new Consumer(object).start();
    }

    static class Product{
        public static String value;
    }
    //生产者线程
    static class Producer extends Thread{
        Object object;

        public Producer(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            while (true){
                synchronized (object) {
                    if (Product.value==null){
                        int bound = (int)(System.currentTimeMillis() / 1000000);
                        Product.value="No"+(new Random().nextInt(bound));
                        System.out.println("产品："+ Product.value);
                    }
                }
            }
        }
    }
    //消费者线程
    static class Consumer extends Thread{

        Object object;

        public Consumer(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            while (true){
                synchronized (object) {
                    if (Product.value!=null){
                        System.out.println("产品已消费" + Product.value);
                        Product.value=null;
                    }
                }
            }
        }
    }
}
