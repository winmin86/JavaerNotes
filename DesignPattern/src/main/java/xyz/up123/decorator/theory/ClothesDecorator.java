package xyz.up123.decorator.theory;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.decorator.theory
 * @ClassName: ClothesDecorator
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 11:41
 * @Version: 1.0
 */
public abstract class ClothesDecorator implements Person{
    //装饰器中要使用被装饰器的对象，构造方法中传入
    protected Person person;

    public ClothesDecorator(Person person){
        this.person = person;
    }
}
