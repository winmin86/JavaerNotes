package xyz.up123.strategy.theory;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.strategy
 * @ClassName: Calculator
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 9:55
 * @Version: 1.0
 */
public class Calculator {
    private Operation operation;

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int doOperation(int num1, int num2) {
        return this.operation.doOperation(num1, num2);
    }
}
