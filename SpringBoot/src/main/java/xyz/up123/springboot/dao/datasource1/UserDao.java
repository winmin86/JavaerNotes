package xyz.up123.springboot.dao.datasource1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.up123.springboot.domain.User;

import java.util.List;

/**
 * @ClassName: UserDao
 * @Description:
 * @Author: Ershixiong
 * @Date: 2018/11/16 10:11
 **/

public interface UserDao {

    int insert(User record);

    User selectByPrimaryKey(@Param("uid") int uid);

    int updateByPrimaryKey(User record);

}
