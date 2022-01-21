package com.group.exam.dao;

import com.group.exam.model.daoModel.CourseLiteDao;
import com.group.exam.model.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {
    @Insert("INSERT INTO " +
            "course(courseid,tid,coursename,description) " +
            "VALUES(#{courseID},#{tID},#{courseName},#{description})")
    int insertCourse(Course course);

    @Delete("DELETE FROM course WHERE courseid = {#courseID}")
    int deleteCourse(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE courseid = #{courseID}")
    Course findByCourseID(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE tid = #{tID}")
    List<Course> findAllByTID(@Param("tID") String tID);

    @Select("SELECT courseid,coursename FROM course WHERE tid = #{tID}")
    List<CourseLiteDao> findLiteAllByTID(@Param("tID") String tID);

    @Update("UPDATE  course SET coursename = #{courseName}, description = #{description} WHERE courseid = #{courseID}")
    int updateCourse(Course course);
}
