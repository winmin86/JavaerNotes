package xyz.up123.bulider;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 17:51
 * @Description：具体建造者：实现了抽象建造者接口。
 * @Version: V1.0.0
 */
public class ConcreteBuilder extends Builder {
    public void buildPartA() {
        product.setPartA("建造 PartA");
    }

    public void buildPartB() {
        product.setPartA("建造 PartB");
    }

    public void buildPartC() {
        product.setPartA("建造 PartC");
    }
}
