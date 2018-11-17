package xyz.up123.springboot.common;

/**
 * @ClassName: RequestHolder
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 13:29
 **/
public class RequestHolder {
    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id) {
        requestHolder.set(id);
    }

    public static Long getId() {
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }

}
