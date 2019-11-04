package xyz.up123.bulider;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 17:52
 * @Description：指挥者：调用建造者中的方法完成复杂对象的创建。
 * @Version: V1.0.0
 */
public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    //产品构建与组装方法
    public Product construct() {
        builder.buildPartA();
        builder.buildPartB();
        builder.buildPartC();
        return builder.getResult();
    }
}
