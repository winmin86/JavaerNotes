package xyz.up123.prototype.simple;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 10:04
 * @Description：TODO
 * @Version: V1.0.0
 */
public class PrototypeTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Obj obj = new Obj(1,"TEST");

        Realizetype obj1 = new Realizetype(obj);

        Realizetype obj2 = (Realizetype) obj1.clone();

        System.out.println("obj1==obj2?" + (obj1 == obj2));

    }
}
