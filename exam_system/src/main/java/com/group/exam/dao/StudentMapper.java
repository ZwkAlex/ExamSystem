package com.group.exam.dao;

import com.group.exam.model.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentMapper {
    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select("SELECT * FROM student WHERE sid = #{id}")
    Student findByID(@Param("id") String ID);

    @Select("SELECT * FROM student WHERE smajorid = #{SMajorID}")
    List<Student> findBySmajorID(@Param("SMajorID") String SMajorID);

    @Select("SELECT count(*)=1 FROM student WHERE sid = #{id}")
    boolean checkStudent(@Param("id") String ID);

    @Insert("INSERT INTO student(sid,sname,college,smajorid) VALUES(#{sID},#{sName},#{college},#{sMajorID})")
    int insertStudent(Student student);

    @Delete("DELETE FROM student WHERE sid = #{sid}")
    int deleteStudent(String sid);

    @Update("UPDATE  student SET sname = #{sName}, college =#{college}, smajorid =#{sMajorID} WHERE sid = #{sID}")
    int updateStudent(Student student);
}
