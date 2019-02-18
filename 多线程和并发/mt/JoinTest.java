/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: JoinTest
 * @Description: java类作用描述
 * @Author: ershixiong
 * @CreateDate: 2019/2/14 11:15
 * @Version: 1.0
 */
public class JoinTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            System.out.println("t1");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            System.out.println("t2");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(()->{
            System.out.println("t3");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            t1.start();
            //join 该线程优先执行 其他线程进入等待
            t1.join();

            t2.start();
            t2.join();

            t3.start();
            t3.join();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
