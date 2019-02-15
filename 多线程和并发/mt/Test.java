import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author:
 * @CreateDate: 2019/2/13 15:54
 * @Version: 1.0
 */
public class Test {
    public static void main(String[] args) {
        Object obj = new Object();
        try {
            obj.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //开启一个线程
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是新开副线程的执行");
                //这个方法会先让副线程沉睡1秒之后在执行，但是期间不会影响主线程的执行
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("===============");
            }
        });
        thread.start();
        try {
            Thread.sleep(1000);
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("我是主线程的执行");*/

        /*System.exit(0);

        Integer[] integerArr = {1,2,3,4,5};
        List<Integer> integers1 = new ArrayList(Arrays.asList(integerArr));
        integers1.add(6);
        System.out.println(integers1);

        System.exit(0);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println(numbers.size());
        System.exit(0);
        List<Integer> biggerThan5 = numbers.stream()
                .filter(number -> number > 5)
                .collect(Collectors.toList());

        biggerThan5.forEach(System.out::println);

        List<Integer> alsoLt = biggerThan5.stream()
                .map(number -> number + 10)
                .collect(Collectors.toList());

        biggerThan5.parallelStream().map(n -> n + 10).collect(Collectors.toList()).forEach(System.out::println);


        System.exit(0);
        //numbers.parallelStream().forEachOrdered(System.out::println);
        numbers.parallelStream()
                .filter(number -> {
                    //numbers.add(7);
                    return number > 5;
                })
                .forEachOrdered(System.out::println);

        System.exit(0);

        Stream.of("AAA","BBB","CCC").parallel().forEach(s->System.out.println("Output:"+s));

        System.out.println("=======================");
        Stream.of("AAA","BBB","CCC").parallel().forEachOrdered(s->System.out.println("Output:"+s));


        System.exit(0);



        //创建集合大小为100
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            integers.add(i);
        }
        System.out.println("============" + integers.size());
        //多管道遍历
        List<Integer> integerList = new ArrayList<>();
        integers.parallelStream().forEachOrdered(e -> {
            //添加list的方法
            setInteger(integerList, e);
            try {
                //休眠100ms，假装执行某些任务
                Thread.sleep(10);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
        System.out.println("============" + integerList.size());*/
    }


    private static void setInteger(List<Integer> integerList, Integer e) {

        integerList.add(e);

    }

}
