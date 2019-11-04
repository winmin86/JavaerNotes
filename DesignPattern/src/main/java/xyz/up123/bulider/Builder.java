package xyz.up123.bulider;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 17:49
 * @Description：抽象建造者：包含创建产品各个子部件的抽象方法。
 * @Version: V1.0.0
 */
public abstract class Builder {
    //创建产品对象
    protected Product product = new Product();

    public abstract void buildPartA();

    public abstract void buildPartB();

    public abstract void buildPartC();

    //返回产品对象
    public Product getResult() {
        return product;
    }
}
