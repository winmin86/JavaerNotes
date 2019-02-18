package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: countdownlatch.CountDownLatchDemo
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/13 18:06
 * @Version: 1.0
 */
public class CountDownLatchDemo implements Runnable {
    private CountDownLatch latch;

    public CountDownLatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            synchronized(this){
                for(int i = 0;i<50000;i++){  //找出50000以内的所有偶数
                    if(i % 2 == 0){
                        System.out.println(Thread.currentThread().getName() + "===" + i);
                    }
                }
            }
        } finally{
            latch.countDown();   //为了让这一句一定执行可以放在finally中
        }
    }
}
