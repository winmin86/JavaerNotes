import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: CyclicBarrierTest3
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/14 10:06
 * @Version: 1.0
 */
public class CyclicBarrierTest3 {
    public static void main(String[] args) {

        for (int i = 0; i < 4; i++) {
            barrDemo(4);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("CyclicBarrier重用");
            System.out.println("===================================");
        }

    }

    static void barrDemo(int num) {
        CyclicBarrier barrier = new CyclicBarrier(num);
        for (int i = 0; i < num; i++) {
            new Writer(barrier).start();
        }
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }
        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                //以睡眠来模拟写入数据操作
                Thread.sleep(1000);
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "所有线程写入完毕，继续处理其他任务...");
        }
    }
}
