package xyz.up123.prototype.simple;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 10:04
 * @Description：TODO
 * @Version: V1.0.0
 */
//具体原型类
class Realizetype implements Cloneable {

    private Obj obj;

    public Realizetype() {
        System.out.println("具体原型创建成功！");
    }

    public Realizetype(Obj obj) {
        this.obj = obj;
    }

    public Object clone() throws CloneNotSupportedException {
        System.out.println("具体原型复制成功！");
        return (Realizetype) super.clone();
    }
}
