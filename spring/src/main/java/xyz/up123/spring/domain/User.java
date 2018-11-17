package xyz.up123.spring.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName: User
 * @Description: 方法二：基于注解的bean定义（不需要提供setter方法）
 * @Author: Ershixiong
 * @Date: 2018/11/15 19:50
 **/
@Component("user")
public class User {
    @Value("1")
    private Integer id;

    @Value("Ershixiong")
    private String name;

    @Value("18")
    private Integer age;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
