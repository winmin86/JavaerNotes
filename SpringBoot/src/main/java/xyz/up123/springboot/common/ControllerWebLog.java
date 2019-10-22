package xyz.up123.springboot.common;

import java.lang.annotation.*;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/10/22 11:30
 * @Description：日志注解
 * @Version: V1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ControllerWebLog {
    String name();

    String[] pamams() default {};

    boolean intoDb() default false;

}