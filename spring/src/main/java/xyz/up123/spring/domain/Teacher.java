package xyz.up123.spring.domain;

/**
 * @ClassName: Teacher
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/15 19:34
 **/
public class Teacher {
    private String name;

    public Teacher() {

    }

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                '}';
    }
}
