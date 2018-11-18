package xyz.up123.test.collection;

/**
 * @ClassName: User
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/18 15:33
 **/
public class User implements Comparable<User>{
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


    public int compareTo(User o) {
        return this.id.compareTo(o.id);
    }
}
