package xyz.up123.test;

import xyz.up123.test.collection.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: Test
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/18 15:36
 **/
public class Test {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(34);
        list.add(55);
        list.add(56);
        list.add(89);
        list.add(12);
        list.add(23);
        list.add(126);
        System.out.println(list);

        ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>();

        chm.put("a", "b");

    }
}
