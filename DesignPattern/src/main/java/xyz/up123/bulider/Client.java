package xyz.up123.bulider;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 17:52
 * @Description：TODO
 * @Version: V1.0.0
 */
public class Client {
    public static void main(String[] args) {
        Builder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.construct();
        product.show();
    }
}
