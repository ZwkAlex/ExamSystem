package com.group.exam.dao;

import com.group.exam.model.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByID(@Param("id") String ID);

    @Select("SELECT count(*)=1 FROM user WHERE id = #{id} AND password = #{password}")
    boolean checkUser(@Param("id") String ID, @Param("password") String password);
}
