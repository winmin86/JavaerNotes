package xyz.up123.bulider;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 17:49
 * @Description：产品角色：包含多个组成部件的复杂对象。
 * @Version: V1.0.0
 */
public class Product {
    private String partA;
    private String partB;
    private String partC;

    public void setPartA(String partA) {
        this.partA = partA;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public void setPartC(String partC) {
        this.partC = partC;
    }

    public void show() {
        //显示产品的特性
    }
}
