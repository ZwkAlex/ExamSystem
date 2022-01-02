package com.group.exam.dao;

import com.group.exam.model.entity.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface TeacherMapper {
    @Select("SELECT * FROM teacher WHERE tid = #{id}")
    Teacher findByID(@Param("id") String ID);

    @Select("SELECT count(*) = 1 FROM teacher WHERE tid = #{id}")
    boolean checkTeacher(@Param("id") String ID);
}
