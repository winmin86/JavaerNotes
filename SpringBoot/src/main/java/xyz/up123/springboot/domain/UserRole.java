package xyz.up123.springboot.domain;

/**
 * @ClassName: UserRole
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:17
 **/
public class UserRole {

    private Integer roleId;

    private Integer uid;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "roleId=" + roleId +
                ", uid=" + uid +
                '}';
    }
}
