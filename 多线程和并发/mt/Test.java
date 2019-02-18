import java.io.IOException;
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
        ShareData1 shareData1 = new ShareData1();
        new Thread(shareData1).start();
        new Thread(shareData1).start();

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(shareData1.count);
    }

    static class ShareData1 implements Runnable {
        public int count = 100;
        public void run() {
            while (count > 0) {
                count--;
                System.out.println("run:"+count);
            }
        }
    }
}
