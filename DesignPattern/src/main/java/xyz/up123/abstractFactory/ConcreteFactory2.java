package xyz.up123.abstractFactory;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 11:45
 * @Description：TODO
 * @Version: V1.0.0
 */
class ConcreteFactory2 implements AbstractFactory {
    public Product1 newProduct1() {
        System.out.println("具体工厂 2 生成-->具体产品 12...");
        return new ConcreteProduct12();
    }

    public Product2 newProduct2() {
        System.out.println("具体工厂 2 生成-->具体产品 22...");
        return new ConcreteProduct22();
    }
}
