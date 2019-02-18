package lock;

import java.util.*;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: lock.ReadWriteLockTest
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/13 15:57
 * @Version: 1.0
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        final Queues queue = new Queues();
        for(int i = 0;i<3;i++){

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        queue.get();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    queue.set(new Random().nextInt(100000));
                }
            }).start();
        }
    }
}
