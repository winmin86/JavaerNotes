package xyz.up123.factoryMethod;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 11:07
 * @Description：TODO
 * @Version: V1.0.0
 */
//具体工厂1：实现了厂品的生成方法
class ConcreteFactory1 implements AbstractFactory {
    public Product newProduct() {
        System.out.println("具体工厂1生成-->具体产品1...");
        return new ConcreteProduct1();
    }
}
