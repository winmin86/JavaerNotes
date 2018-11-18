package xyz.up123.test.oom;

import java.util.Map;
import java.util.Random;

/**
 * @ClassName: GCOverhead
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/18 09:18
 **/
public class GCOverhead {
    public static void main(String args[]) throws Exception {
        Map map = System.getProperties();
        Random r = new Random();
        long i = 0;
        while (true) {
            map.put(r.nextInt(), "value");
            System.out.println(++i);
        }
    }
}
