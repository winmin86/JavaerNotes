/**
 * @ProjectName: JavaerNotes
 * @Package: PACKAGE_NAME
 * @ClassName: VolatileTest
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/2/18 13:39
 * @Version: 1.0
 */
public class VolatileTest extends Thread{
    //设置类静态变量,各线程访问这同一共享变量
    private static boolean flag = false;
    //无限循环,等待flag变为true时才跳出循环
    @Override
    public void run() {
        while (!flag){
        };
        System.out.println("停止了");
    }

    public static void main(String[] args) throws Exception {
        new VolatileTest().start();
        //sleep的目的是等待线程启动完毕,也就是说进入run的无限循环体了
        Thread.sleep(100);
        flag = true;
    }
}
