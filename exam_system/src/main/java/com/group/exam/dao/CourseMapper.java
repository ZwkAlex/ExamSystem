package com.group.exam.dao;

import com.group.exam.model.entity.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseMapper {
    @Insert("INSERT INTO " +
            "course(courseid,tid,coursename,description) " +
            "VALUES(#{courseid},#{tid},#{coursename},#{description})")
    int insertCourse(Course course);

    @Delete("DELETE FROM course WHERE courseid = {#courseID}")
    int deleteCourse(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE courseid = #{courseID}")
    Course findByCourseID(@Param("courseID") String courseID);

    @Select("SELECT * FROM course WHERE tid = #{tID}")
    List<Course> findAllByTID(@Param("tID") String tID);

    @Select("UPDATE  answer SET tid = #{tid}, coursename = {#coursename}, description = {#description} WHERE courseid = #{courseid}")
    int updateCourse(Course course);
}
