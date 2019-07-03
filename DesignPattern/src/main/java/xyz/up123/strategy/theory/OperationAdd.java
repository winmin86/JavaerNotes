package xyz.up123.strategy.theory;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.strategy
 * @ClassName: OperationAdd
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 9:51
 * @Version: 1.0
 */
public class OperationAdd implements Operation{
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
