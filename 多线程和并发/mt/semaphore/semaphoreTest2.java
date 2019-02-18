package semaphore;

import java.util.concurrent.Semaphore;

/**
 * @ProjectName: JavaerNotes
 * @Package: semaphore
 * @ClassName: semaphoreTest2
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/16 17:39
 * @Version: 1.0
 */
public class semaphoreTest2 {

    public static void main(String[] args) {
        //new Semaphore(permits)：初始化许可证数量的构造函数
        Semaphore semaphore = new Semaphore(5);
        //new Semaphore(permits,fair):初始化许可证数量和是否公平模式的构造函数
        semaphore = new Semaphore(5, true);
        //isFair():是否公平模式FIFO
        System.out.println("是否公平模式FIFO: " + semaphore.isFair());
        //availablePermits():获取当前可用的许可证数量
        System.out.println("获取当前可用的许可证数量: 开始---" + semaphore.availablePermits());
        //acquire():获取1个许可证
        //此线程会一直阻塞，直到获取这个许可证，或者被中断(抛出InterruptedException异常)
        try {
            semaphore.acquire();
            System.out.println("获取当前可用的许可证数量: acquire 1个---" + semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //release():释放1个许可证
        semaphore.release();
        System.out.println("获取当前可用的许可证数量: release 1个---" + semaphore.availablePermits());

        //acquire(permits):获取n个许可证
        //此线程会一直阻塞，直到获取全部n个许可证，或者被中断(抛出InterruptedException异常)
        try {
            semaphore.acquire(2);
            System.out.println("获取当前可用的许可证数量: acquire 2个---" + semaphore.availablePermits());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //release(permits):释放n个许可证
        semaphore.release(2);
        System.out.println("获取当前可用的许可证数量: release 2个---" + semaphore.availablePermits());

        //hasQueuedThreads():是否有正在等待许可证的线程
        System.out.println("是否有正在等待许可证的线程: " + semaphore.hasQueuedThreads());

        //getQueueLength():正在等待许可证的队列长度(线程数量)
        System.out.println("正在等待许可证的队列长度(线程数量):" + semaphore.getQueueLength());

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("===================");
        //定义final的信号量
        Semaphore finalSemaphore = semaphore;

        new Thread(() -> {
            //drainPermits():获取剩余的所有的许可证
            int permits = finalSemaphore.drainPermits();
            System.out.println(Thread.currentThread().getName() + "获取了剩余的全部" + permits + "个许可证");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //释放所有的许可证
            finalSemaphore.release(permits);
            System.out.println(Thread.currentThread().getName() + "释放了" + permits + "个许可证");
        }).start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            try {
                //有一个线程正在等待获取1个许可证
                finalSemaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });



    }
}
