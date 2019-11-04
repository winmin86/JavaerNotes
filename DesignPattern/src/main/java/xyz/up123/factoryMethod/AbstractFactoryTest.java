package xyz.up123.factoryMethod;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 11:08
 * @Description：TODO
 * @Version: V1.0.0
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        try {
            Product a;
            AbstractFactory af;
            af = (AbstractFactory) ReadXML1.getObject();
            a = af.newProduct();
            a.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
