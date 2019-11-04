package xyz.up123.prototype.simple;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/4 10:05
 * @Description：TODO
 * @Version: V1.0.0
 */
public class Obj {
    private Integer id;

    private String name;

    public Obj(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
