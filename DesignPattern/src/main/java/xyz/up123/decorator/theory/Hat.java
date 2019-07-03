package xyz.up123.decorator.theory;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.decorator.theory
 * @ClassName: Hat
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 11:43
 * @Version: 1.0
 */
public class Hat extends ClothesDecorator{
    public Hat(Person person) {
        super(person);
    }

    @Override
    public void show() {
        //执行已有功能
        person.show();
        //此处是附加的功能
        System.out.println("戴上帽子，累计消费" + this.cost());
    }

    @Override
    public Double cost() {
        return person.cost() + 50; //帽子50元
    }
}
