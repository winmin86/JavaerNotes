package xyz.up123.springboot.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: User
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:05
 **/
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private int uid;
    //帐号
    private String userName;
    //名称（昵称或者真实姓名，不同系统不同定义）
    private String name;
    //密码
    private String password;
    //加密密码的盐
    private String salt;
    ////用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
    private String state;

    private List<Role> roles;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
