package xyz.up123.decorator.theory;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.decorator.theory
 * @ClassName: Jacket
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 11:43
 * @Version: 1.0
 */
public class Jacket extends ClothesDecorator{
    public Jacket(Person person) {
        super(person);
    }
    @Override
    public void show() {
        person.show();
        System.out.println("穿上夹克，累计消费" + this.cost());
    }

    @Override
    public Double cost() {
        return person.cost() + 100; //夹克100元
    }
}
