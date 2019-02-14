/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: JoinTest
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/14 11:15
 * @Version: 1.0
 */
public class JoinTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            System.out.println("t1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{
            System.out.println("t2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(()->{
            System.out.println("t3");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            t2.join();
            t3.join();
            t1.start();

            t1.join();
            t3.join();
            t2.start();

            t1.join();
            t2.join();
            t3.start();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
