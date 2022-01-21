package com.group.exam.dao;

import com.group.exam.model.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByID(@Param("id") String ID);

    @Select("SELECT count(*)=1 FROM user WHERE id = #{id} AND password = #{password}")
    boolean checkUser(@Param("id") String ID, @Param("password") String password);

    @Update("UPDATE user SET password = #{password}  WHERE id = #{id}")
    int changePassword(@Param("id") String ID, @Param("password") String password);

    @Update("UPDATE user SET token = #{token}  WHERE id = #{id}")
    int updateToken(@Param("id") String ID, @Param("token") String token);

    @Insert("INSERT INTO user(id,password,role) VALUES(#{id}, #{password}, 'ROLE_STUDENT')")
    int insertStudentUser(@Param("id") String ID, @Param("password") String password);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteStudentUser(@Param("id") String ID);
}
