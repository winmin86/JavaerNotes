package xyz.up123.springboot.domain;

import java.io.Serializable;

/**
 * @ClassName: Role
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:06
 **/
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String available;

    private String description;

    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
