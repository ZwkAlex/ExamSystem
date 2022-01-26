package com.group.exam.dao;

import com.group.exam.model.daoModel.CourseDao;
import com.group.exam.model.daoModel.CourseLiteDao;
import com.group.exam.model.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {
    @Insert("INSERT INTO " +
            "course(courseid,tid,coursename,description) " +
            "VALUES(#{courseID},#{tID},#{courseName},#{description})")
    int insertCourse(Course course);

    @Delete("DELETE FROM course WHERE courseid = #{courseID}")
    int deleteCourse(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE courseid = #{courseID}")
    CourseDao findByCourseID(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE courseid = #{courseID} AND isValid=true")
    CourseDao findValidByCourseID(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE tid = #{tID} AND isValid = true")
    List<CourseDao> findAllValidByTID(@Param("tID") String tID);

    @Select("SELECT courseid,coursename FROM course WHERE tid = #{tID} AND isValid = true")
    List<CourseLiteDao> findLiteAllValidByTID(@Param("tID") String tID);

    @Update("UPDATE  course SET coursename = #{courseName}, description = #{description} WHERE courseid = #{courseID}")
    int updateCourse(Course course);

    @Update("UPDATE course SET isValid = false  WHERE courseid=#{courseID}")
    int updateCourseToInvalid(@Param("courseID") String courseID);
}
