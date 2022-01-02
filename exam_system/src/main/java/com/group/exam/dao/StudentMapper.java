package com.group.exam.dao;

import com.group.exam.model.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {
    @Select("SELECT * FROM student WHERE sid = #{id}")
    Student findByID(@Param("id") String ID);

    @Select("SELECT * FROM student WHERE smajorid = #{smajorid}")
    List<Student> findBySmajorID(@Param("smajorid") String SMajorID);

    @Select("SELECT count(*)=1 FROM student WHERE sid = #{id}")
    boolean checkStudent(@Param("id") String ID);

    @Select("INSERT INTO student(sid,sname,college,smajor) VALUES(#{sid},#{sname},#{college},#{smajor})")
    int insertStudent(Student student);

    @Select("DELETE FROM student WHERE sid = #{sid}")
    int deleteStudent(String sid);

    @Select("UPDATE  student SET sname = #{sname}, college =#{college}, smajor =#{smajor} WHERE sid = #{sid}")
    int updateStudent(Student student);
}
