package xyz.up123.test.oom;

import java.util.Vector;

/**
 * @ClassName: MemoryLeak
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/17 22:55
 **/
public class MemoryLeak {

    static Vector v = new Vector();

    public static void main(String[] args) {

        for (int i = 1; i < 100; i++) {
            Object o = new Object();
            v.add(o);
            o = null;
            System.out.println(i);
        }
    }
}
