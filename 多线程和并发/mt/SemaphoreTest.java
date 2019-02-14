import java.util.concurrent.*;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: SemaphoreTest
 * @Description:
 *
 * Semaphore实现信号灯
 *
 * Semaphore可以维护当前访问自身的线程个数，并提供了同步机制。使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。
 *
 * 假设一个文件同时可以被3个人访问，来了5个人，同时只有3个访问。3个中任何一个出来后，等待的就可以进去了。
 *
 * 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得了 "锁"，再由另外一个线程释放"锁"，这可应用于死锁恢复的一些场合。
 *
 * @Author:
 * @CreateDate: 2019/2/13 17:34
 * @Version: 1.0
 */
public class SemaphoreTest {
    /**
     * 线程池的基本大小
     */
    static int CORE_POOL_SIZE = 10;
    /**
     * 线程池最大数量
     */
    static int MAX_POOL_SIZE = 100;
    /**
     * 线程活动保持时间
     */
    static long KEEP_ALIVE_TIME = 1;
    /**
     * 任务队列
     */
    static ArrayBlockingQueue workQueue = new ArrayBlockingQueue(10);

    public static void main(String[] args) {
        ThreadPoolExecutor service = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                workQueue);

        //ExecutorService service = Executors.newCachedThreadPool();
        final  Semaphore sp = new Semaphore(3);  //还有一个构造方法，Semaphore(int permits, boolean fair)  fair参数为true表示谁先来谁先进，一种公平的原则
        for(int i=0;i<10;i++){
            Runnable runnable = new Runnable(){
                @Override
                public void run(){
                    try {
                        sp.acquire();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "进入，当前已有" + (3-sp.availablePermits()) + "个并发");
                    try {
                        Thread.sleep((long)(Math.random()*10000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "即将离开");
                    sp.release();
                    //下面代码有时候执行不准确，因为其没有和上面的代码合成原子单元
                    System.out.println("线程" + Thread.currentThread().getName() +
                            "已离开，当前已有" + (3-sp.availablePermits()) + "个并发");
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
    }
}
