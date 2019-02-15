package sync;

import java.util.Random;

/**
 * @ProjectName: JavaerNotes
 * @Package: sync
 * @ClassName: Sync3
 * @Description: 通过添加wait notify关键字去主动唤醒生产者或消费者线程的执行
 * @Author:
 * @CreateDate: 2019/2/15 13:47
 * @Version: 1.0
 */
public class Sync3 {
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
                    if (Product.value != null) {
                        try {
                            object.wait();//产品还未消费 进入等待状态
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    int bound = (int)(System.currentTimeMillis() / 1000000);
                    Product.value="No"+(new Random().nextInt(bound));
                    System.out.println("产品："+ Product.value);
                    object.notify();//产品已生产 唤醒消费者线程
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
                    if (Product.value == null) {
                        try {
                            object.wait();//产品为空 进入等待状态
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("产品已消费" + Product.value);
                    Product.value = null;
                    object.notify();//产品已经消费 唤醒生产者线程生产
                }
            }
        }
    }
}
