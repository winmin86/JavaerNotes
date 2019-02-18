package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: lock.ConditionTest
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/13 17:07
 * @Version: 1.0
 */
public class ConditionTest {
    public static void main(String[] args) {
        final Buiness buiness = new Buiness();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 50; i++) {
                    buiness.sub(i);
                }
            }
        }).start();

        for (int i = 1; i <= 50; i++) {
            buiness.main(i);
        }
    }

    /**
     * 将程序改写为使用Lock&Condition的方式进行 同步和通信
     */
    static class Buiness {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        private boolean isShouldSub = false;  //主线程先打印

        public void main(int j) { //进行同步，防止在打印时被其他线程干扰
            lock.lock();
            try {
                while (isShouldSub) {  //这里使用while 防止假唤醒
                    try {
                        condition.await();
//                    this.wait();  //wait() 和 notify() 必须出现在同步监视器内部中
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 1; i <= 100; i++) {
                    System.out.println("main thread print " + i + " loop of " + j);
                }
                isShouldSub = true;
                condition.signal();
//            this.notify();
            } finally {
                lock.unlock();
            }
        }

        public void sub(int j) {
            lock.lock();
            try {
                while (!isShouldSub) {
                    try {
                        condition.await();
//                    this.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                for (int i = 1; i <= 10; i++) {
                    System.out.println("sub thread print " + i + " loop of " + j);
                }
                isShouldSub = false;
                condition.signal();
//            this.notify();
            } finally {
                lock.unlock();
            }
        }
    }
}
