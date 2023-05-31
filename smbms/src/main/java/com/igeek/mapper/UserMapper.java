package com.igeek.mapper;

import com.igeek.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public User findById(@Param("id") long id);

    public User findByNameAndPassword(@Param("userName") String userName,@Param("password") String password);

    public int updatePasswordById(@Param("id") long id,@Param("password") String password);

    public int findUserCountByRoleIdAndUsername(@Param("id") long id,@Param("username") String username);

    public List<User> findUserByRoleIdAndUsernameAndPageLimit(@Param("id") long id,@Param("username") String username,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize);

    public int insertUser(User user);

    public List<User> findAll();

    public int deleteById(@Param("id") long id);

    public int updateUser(User user);
}
