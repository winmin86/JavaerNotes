package xyz.up123.strategy.actual;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.strategy.actual
 * @ClassName: DefaultTheme
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 10:04
 * @Version: 1.0
 */
public class DefaultTheme implements Theme{
    @Override
    public void showTheme() {
        System.out.println("显示默认主题");
    }
}
