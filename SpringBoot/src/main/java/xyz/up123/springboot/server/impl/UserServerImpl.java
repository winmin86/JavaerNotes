package xyz.up123.springboot.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.up123.springboot.dao.datasource1.UserDao;
import xyz.up123.springboot.dao.datasource1.UserRoleDao;
import xyz.up123.springboot.domain.Role;
import xyz.up123.springboot.domain.User;
import xyz.up123.springboot.domain.UserRole;
import xyz.up123.springboot.server.UserRoleServer;
import xyz.up123.springboot.server.UserServer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserServerImpl
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:24
 **/
@Service
public class UserServerImpl implements UserServer {


    @Autowired
    private UserRoleServer userRoleServer;

    @Autowired
    private UserDao userDao;

    //@WriteDataSource还是@ReadDataSource
    @Override
    @Transactional(transactionManager = "primaryTransactionManager", propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=2000,rollbackFor=Exception.class)
    public int insert(User user) {
        int rs = userDao.insert(user);
        List<Role> roles = user.getRoles();
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : roles) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUid(user.getUid());
            userRoles.add(userRole);
        }
        userRoleServer.insert(userRoles);
        return rs;
    }

    @Override
    public User selectByPrimaryKey(int uid) {
        return userDao.selectByPrimaryKey(uid);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }

    @Override
    public void sayHello() {
        System.out.println(Thread.currentThread().getId() + "===============" + this.hashCode());
    }
}
