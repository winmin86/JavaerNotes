package xyz.up123.abstractFactory;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 11:40
 * @Description：TODO
 * @Version: V1.0.0
 */
class ConcreteFactory1 implements AbstractFactory {
    public Product1 newProduct1() {
        System.out.println("具体工厂 1 生成-->具体产品 11...");
        return new ConcreteProduct11();
    }

    public Product2 newProduct2() {
        System.out.println("具体工厂 1 生成-->具体产品 21...");
        return new ConcreteProduct21();
    }
}
