package sync;

import java.util.Random;

/**
 * @ProjectName: JavaerNotes
 * @Package: sync
 * @ClassName: Sync1
 * @Description:
 * Volatile： 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 * volatile关键字会强制将修改的值立即写入主存，使线程的工作内存中缓存变量行无效。
 * 缺点：但是不具备原子特性。这就是说线程能够自动发现 volatile.md 变量的最新值。Volatile 变量可用于提供线程安全，
 * 但是只能应用于非常有限的一组用例：多个变量之间或者某个变量的当前值与修改后值之间没有约束。
 *
 * @Author:
 * @CreateDate: 2019/2/15 13:39
 * @Version: 1.0
 */
public class Sync1 {
    public static void main(String[] args) {
        new Producer().start();//开启生产
        new Consumer().start();//开启消费
    }

    static class Product{
        //加volatile关键字保证Producer、Consumer中value的彼此可见性
        public volatile static String value;
    }
    //生产者线程
    static class Producer extends Thread{
        @Override
        public void run() {
            while (true){
                if (Product.value==null){
                    int bound = (int)(System.currentTimeMillis() / 1000000);
                    Product.value="No"+(new Random().nextInt(bound));
                    System.out.println("产品："+Product.value);
                }
            }
        }
    }
    //消费者线程
    static class Consumer extends Thread{
        @Override
        public void run() {
            while (true){
                if (Product.value!=null){
                    System.out.println("产品已消费" + Product.value);
                    Product.value=null;
                }
            }
        }
    }
}
