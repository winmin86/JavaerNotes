import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: ConditionTest1
 * @Description: A 打印10次，B打印20次 ，C打印5次依次递归5次
 * @Author: zhuwenming
 * @CreateDate: 2019/2/13 17:21
 * @Version: 1.0
 */
public class ConditionTest1 {
    public static void main(String[] args) {
        final Buiness buiness = new Buiness();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    buiness.loopA(i);
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    buiness.loopB(i);
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 20; i++) {
                    buiness.loopC(i);
                    System.out.println("-----------------");
                }
            }
        }, "C").start();

    }

}

class Buiness {

    private int number = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    void loopA(int outerLoop) {
        lock.lock();
        try {
            while (number != 1) {
                condition1.await();
            }
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + outerLoop);
            }
            number = 2;
            condition2.signal();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    void loopB(int outerLoop) {
        lock.lock();
        try {
            while (number != 2) {
                condition2.await();
            }
            for (int i = 1; i <= 20; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + outerLoop);
            }
            number = 3;
            condition3.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    void loopC(int outerLoop) {
        lock.lock();
        try {
            while (number != 3) {
                condition3.await();
            }
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i + "\t" + outerLoop);
            }
            number = 1;
            condition1.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

}
