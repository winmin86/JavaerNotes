package callable;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * @ProjectName: JavaerNotes
 * @Package: callable
 * @ClassName: MyCallable
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/18 10:07
 * @Version: 1.0
 */
public  class MyCallable<T> implements Callable<T> {
    private T taskNum;

    public MyCallable(T taskNum) {
        this.taskNum = taskNum;
    }

    @Override
    public T call() throws Exception {
        System.out.println(">>>" + taskNum + "任务启动");
        Date dateTmp1 = new Date();
        Thread.sleep(1000);
        Date dateTmp2 = new Date();
        long time = dateTmp2.getTime() - dateTmp1.getTime();
        System.out.println(">>>" + taskNum + "任务终止");
        return (T) (taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】");
    }
}
