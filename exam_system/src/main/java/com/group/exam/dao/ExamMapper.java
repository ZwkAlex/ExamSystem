package com.group.exam.dao;

import com.group.exam.model.entity.Exam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ExamMapper {
    @Select("SELECT * FROM exam WHERE examid = #{examID}")
    Exam findByExamID(@Param("examID") String ID);

    @Select("SELECT * FROM exam WHERE courseid = #{courseID}")
    List<Exam> findAllByCourseID(@Param("courseID") String ID);
}
