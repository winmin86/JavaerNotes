package xyz.up123.springboot.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.up123.springboot.common.RequestHolder;
import xyz.up123.springboot.domain.Role;
import xyz.up123.springboot.domain.User;
import xyz.up123.springboot.domain.UserRole;
import xyz.up123.springboot.server.UserRoleServer;
import xyz.up123.springboot.server.UserServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserController
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:33
 **/

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServer userServer;
    @Autowired
    private UserRoleServer userRoleServer;

    @RequestMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") int id) {
        return  userServer.selectByPrimaryKey(id);
    }

    @PostMapping("")
    public int addUser(@RequestBody User user) {
        userServer.insert(user);
        /*
        添加记录能够返回主键的关键点在于需要在<insert>标签中添加以下三个属性
        <insert useGeneratedKeys="true" keyProperty="id" keyColumn="id"></insert>。
        useGeneratedKeys：必须设置为true，否则无法获取到主键id。
        keyProperty：设置为POJO对象的主键id属性名称。
        keyColumn：设置为数据库记录的主键id字段名称
        插入后MyBatis会将返回的主键id填充到对象里
        */
        /*List<Role> roles = user.getRoles();
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUid(user.getUid());
            userRoles.add(userRole);
        }

        userRoleServer.insert(userRoles);*/

        return user.getUid();
    }

    @GetMapping("/count")
    public long getCount() {
        RequestHolder.add(1L);

        return RequestHolder.getId();
    }

    @GetMapping("/user")
    public User getUser() {
        User user = new User();
        //user.setUid(1);
        user.setName("二师兄");
        user.setUserName("尼古拉斯·二师兄");
        return user;
    }

}
