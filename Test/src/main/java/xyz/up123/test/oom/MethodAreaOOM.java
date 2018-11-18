package xyz.up123.test.oom;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName: MethodAreaOOM
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/17 22:45
 **/
public class MethodAreaOOM {
    static class OOMObject {

    }

    /**
     * -XX:MaxPermSize=10M
     * @param args
     */
    public static void main(String[] args) {

        for (int i = 0; i < 9999; ++i) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                public Object intercept(Object obj, Method method,
                                        Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            enhancer.create();
        }
    }
}
