package xyz.up123.test.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: GCTest
 * @Description: -Xmn10M -Xms20M -Xmx20M -XX:+PrintGCDetails
 * @Author: Ershixiong
 * @Date: 2018/11/18 14:23
 **/
public class GCTest {
    public static void main(String[] args) {
        List<byte[]> bytes = new ArrayList<byte[]>();
        for (int i = 0; i < 1000; i++) {
            byte[] allocation = new byte[30900*1024];
            bytes.add(allocation);
        }

    }
}
