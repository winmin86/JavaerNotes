package sync;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: JavaerNotes
 * @Package: sync
 * @ClassName: Sync4
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/15 14:41
 * @Version: 1.0
 */
public class Sync4 {
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

        ReentrantLock lock = new ReentrantLock();

        public Producer(Object object) {
            this.object = object;
        }
        @Override
        public void run() {

            while (true){
                lock.lock();
                try {
                    if (Product.value==null){
                        int bound = (int)(System.currentTimeMillis() / 1000000);
                        Product.value="No"+(new Random().nextInt(bound));
                        System.out.println("产品："+Product.value);
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
    //消费者线程
    static class Consumer extends Thread{

        Object object;

        ReentrantLock lock = new ReentrantLock();

        public Consumer(Object object) {
            this.object = object;
        }

        @Override
        public void run() {
            while (true){
                lock.lock();
                try {
                    if (Product.value!=null){
                        System.out.println("产品已消费" + Product.value);
                        Product.value=null;
                    }
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
