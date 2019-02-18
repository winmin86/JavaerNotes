package callable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ProjectName: JavaerNotes
 * @Package: callable
 * @ClassName: Demo
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/18 10:09
 * @Version: 1.0
 */
public class Demo {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 20, 100, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardPolicy());

        int taskSize = 5;
        ExecutorService executor = Executors.newFixedThreadPool(taskSize);
        List<Future> list = new ArrayList<>();
        for (int i = 0; i < taskSize; i++) {
            MyCallable<String> myCallable = new MyCallable<>(i + "");
            Future<String> submit = executor.submit(myCallable);
            list.add(submit);
        }
        executor.shutdown();

        try {
            for (Future f : list) {
                String s = f.get().toString();
                System.out.println(">>>>" + s);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

