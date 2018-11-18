package xyz.up123.test;

import xyz.up123.test.collection.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ClassName: Test
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/18 15:36
 **/
public class Test {
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        users.add(new User(15, "u15"));
        users.add(new User(53, "u53"));
        users.add(new User(26, "u26"));
        users.add(new User(9, "u9"));
        users.add(new User(20, "u20"));


    }
}
