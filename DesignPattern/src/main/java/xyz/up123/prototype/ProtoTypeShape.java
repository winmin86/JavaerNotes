package xyz.up123.prototype;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 9:54
 * @Description：TODO
 * @Version: V1.0.0
 */
public class ProtoTypeShape {
    public static void main(String[] args) {
        ProtoTypeManager pm = new ProtoTypeManager();
        Shape obj1 = (Circle) pm.getShape("Circle");
        obj1.countArea();
        Shape obj2 = (Shape) pm.getShape("Square");
        obj2.countArea();
    }
}
