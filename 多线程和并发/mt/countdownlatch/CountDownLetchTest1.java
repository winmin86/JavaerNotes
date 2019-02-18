package countdownlatch; /**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: countdownlatch.CountDownLetchTest1
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/13 19:06
 * @Version: 1.0
 */

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 犹如倒计时计数器，调用CountDownLatch对象的countDown方法就将计数器减一，
 * 当计数器到达0时，则所有等待者或单个等待者开始执行。
 * 可以实现一个人（也可以是多个人）等待其他所有人都来通知他，可以实现一个人通知多个人的效果，
 * 类似裁判一声口令，运动员同时开始奔跑，或者所有运动员都跑到终点后裁判才可以公布结果。
 * 还可以实现一个计划需要多个领导都签字后才能继续向下实施的情况
 *
 */
public class CountDownLetchTest1 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1); //计数器初始值 1
        final CountDownLatch cdAnswer = new CountDownLatch(3);
        for(int i = 0;i<3;i++){
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        System.out.println("线程"+Thread.currentThread().getName()
                                +"正准备接受命令");
                        cdOrder.await();
                        System.out.println("线程"+Thread.currentThread().getName()
                                +"已接受命令");
                        Thread.sleep((long)(Math.random()*10000));
                        System.out.println("线程"+Thread.currentThread().getName()
                                +"回应命令处理结果");
                        cdAnswer.countDown();
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }
            };
            executorService.execute(runnable);
        }
        try {
            Thread.sleep((long)(Math.random()*10000));
            System.out.println("线程"+Thread.currentThread().getName()
                    +"即将发布命令");
            cdOrder.countDown();  //计数器数值减 1
            System.out.println("线程"+Thread.currentThread().getName()
                    +"已发送命令，正在等待结果");
            cdAnswer.await();
            System.out.println("线程"+Thread.currentThread().getName()
                    +"已收到所有响应结果");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}
