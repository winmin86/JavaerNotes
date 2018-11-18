package xyz.up123.test.oom;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: HeapOOM
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/17 22:36
 **/
public class HeapOOM {
    static int MB = 1024*1024;

    /**
     * -Xmx16M
     * @param args
     */
    public static void main(String[] args) {

        List<Object> list = new ArrayList<Object>();

        for(int i=0; i<1000; ++i){
            ByteBuffer bb = java.nio.ByteBuffer.allocate(MB);
            list.add(bb);
            System.out.println("分派第"+(i+1)+" MB");
        }

    }
}
