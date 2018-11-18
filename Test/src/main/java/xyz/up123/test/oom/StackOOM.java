package xyz.up123.test.oom;

/**
 * @ClassName: StackOOM
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/17 22:22
 **/
public class StackOOM {
    private static void fun(){
        fun();
    }

    /**
     * VM arg  -Xss128K
     */
    public static void main(String[] args) {
        fun();
    }
}
