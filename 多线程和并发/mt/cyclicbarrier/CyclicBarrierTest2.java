package cyclicbarrier;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: cyclicbarrier.CyclicBarrierTest2
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/14 9:51
 * @Version: 1.0
 */
public class CyclicBarrierTest2 {
    public static void main(String[] args) {
        //数组大小
        int size = 50000000;
        //定义数组
        long[] numbers = new long[size];
        //随机初始化数组
        for (int i = 0; i < size; i++) {
            numbers[i] = new Random().nextInt(10000000);
        }

        //单线程计算结果
        System.out.println();
        Long sum = 0L;
        for (int i = 0; i < size; i++) {
            sum += numbers[i];
        }

        System.out.println("单线程计算结果：" + sum);

        //多线程计算结果
        //定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //定义五个Future去保存子数组计算结果
        final long[] results = new long[5];

        //定义一个循环屏障，在屏障线程中进行计算结果合并
        CyclicBarrier barrier = new CyclicBarrier(5, () -> {
            Long sums = 0L;
            for (int i = 0; i < 5; i++) {
                sums += results[i];
            }
            System.out.println("多线程计算结果：" + sums);
        });

        //子数组长度
        int length = 10000000;
        //定义五个线程去计算
        for (int i = 0; i < 5; i++) {
            //定义子数组
            long[] subNumbers = Arrays.copyOfRange(numbers, (i * length), ((i + 1) * length));
            //盛放计算结果
            int finalI = i;
            executorService.submit(() -> {
                for (int j = 0; j < subNumbers.length; j++) {
                    results[finalI] += subNumbers[j];
                }
                //等待其他线程进行计算
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }

        //关闭线程池
        executorService.shutdown();

    }
}
