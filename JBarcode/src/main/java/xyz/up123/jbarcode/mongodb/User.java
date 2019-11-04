package xyz.up123.jbarcode.mongodb;

/**
 * @Author：ZhuWenming
 * @CreateTime：2019/11/1 16:40
 * @Description：TODO
 * @Version: V1.0.0
 */
public class User {
    private Integer id;

    private String name;

    public User(Integer id, String name) {
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
