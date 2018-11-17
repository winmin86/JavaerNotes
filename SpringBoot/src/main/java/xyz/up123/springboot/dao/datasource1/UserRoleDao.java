package xyz.up123.springboot.dao.datasource1;

import org.apache.ibatis.annotations.Mapper;
import xyz.up123.springboot.domain.UserRole;

import java.util.List;

public interface UserRoleDao {
    int insert(List<UserRole> userRoles);
}
