package xyz.up123.strategy.actual;

/**
 * @ProjectName: JavaerNotes
 * @Package: xyz.up123.strategy.actual
 * @ClassName: ThemeManager
 * @Description: java类作用描述
 * @Author: zhuwenming
 * @CreateDate: 2019/3/5 10:05
 * @Version: 1.0
 */
public class ThemeManager {
    private Theme theme;

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public void showTheme() {
        this.theme.showTheme();
    }
}
