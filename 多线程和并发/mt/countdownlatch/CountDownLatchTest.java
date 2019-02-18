package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: countdownlatch.CountDownLatchTest
 * @Description:
 *
 * CountDownLatch : 闭锁，在完成某些运算时，只有其他所有线程的运算全部完成，当前运算才继续执行
 *
 * CountDownLatch应用1 : 比如要统计5个线程并发的运行时间，即线程的开始时间与最后一个线程的运行结束时间的间隔时间。
 *
 * @Author:
 * @CreateDate: 2019/2/13 18:03
 * @Version: 1.0
 */
public class CountDownLatchTest {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(5);
        CountDownLatchDemo ld = new CountDownLatchDemo(latch);

        long start = System.currentTimeMillis();
        for(int i = 0;i<5;i++){
            new Thread(ld).start();
        }
        try {
            latch.await();   //先执行完成的线程需要等待还没有执行完的线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end  = System.currentTimeMillis();
        System.out.println("cost: "+ (end - start));
    }


}
