package xyz.up123.decorator.theory;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.decorator.theory
 * @ClassName: LaoWang
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 11:36
 * @Version: 1.0
 */
public class LaoWang implements Person{
    @Override
    public Double cost() {
        return 0.0; //赤果果的时候累计消费为0
    }

    @Override
    public void show() {
        System.out.println("我是赤果果的老王");
    }
}
