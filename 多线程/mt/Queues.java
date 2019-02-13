import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: Queues
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/13 15:56
 * @Version: 1.0
 */
public class Queues {
    private Object data = null; //共享数据 ，只能有一个线程写该数据，但可以有多个线程同时读
    ReadWriteLock rwl = new ReentrantReadWriteLock();  //读写锁

    public void get(){
        try {
            rwl.readLock().tryLock(1000, TimeUnit.MILLISECONDS);
            //rwl.readLock().lock();  //上读锁 可以有多个线程同时读
            System.out.println(Thread.currentThread().getName() + " be ready to read data!");
            Thread.sleep((long)Math.random() * 1000);
            System.out.println(Thread.currentThread().getName() + " have read data : "+ data);
        } catch (InterruptedException e) {
        }finally{
            rwl.readLock().unlock();  //释放读锁
        }
    }
    public void set(Object data){
        try {
            rwl.writeLock().tryLock(1000, TimeUnit.MILLISECONDS);
            //rwl.writeLock().lock();  //添加写锁，保证只能有一个线程进行写操作
            System.out.println(Thread.currentThread().getName() + " be read to write data: "+ data);
            Thread.sleep((long)Math.random() * 1000);
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "has write data");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwl.writeLock().unlock();  //释放写锁
        }
    }
}
