package xyz.up123.springboot.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import xyz.up123.springboot.dao.datasource1.UserRoleDao;
import xyz.up123.springboot.domain.UserRole;
import xyz.up123.springboot.server.UserRoleServer;

import java.util.List;

/**
 * @ClassName: UserRoleServerImpl
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:27
 **/
@Service
public class UserRoleServerImpl implements UserRoleServer {

    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=2000,rollbackFor=Exception.class)
    public int insert(List<UserRole> userRoles) {
        return userRoleDao.insert(userRoles);
    }
}
