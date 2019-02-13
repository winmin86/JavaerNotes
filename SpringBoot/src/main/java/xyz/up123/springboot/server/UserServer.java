package xyz.up123.springboot.server;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import xyz.up123.springboot.domain.User;
import xyz.up123.springboot.domain.UserRole;

import java.util.List;

public interface UserServer {

    int insert(User record);

    User selectByPrimaryKey(int uid);

    int updateByPrimaryKey(User record);

    void sayHello();
}
