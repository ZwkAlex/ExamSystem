package com.group.exam.dao;

import com.group.exam.model.daoModel.ExamLiteDao;
import com.group.exam.model.entity.Exam;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ExamMapper {
    @Select("SELECT * FROM exam WHERE examid = #{examID}")
    Exam findByExamID(@Param("examID") String ID);

    @Select("SELECT * FROM exam WHERE courseid = #{courseID}")
    List<Exam> findAllByCourseID(@Param("courseID") String ID);

    @Select("SELECT examid,startdate,enddate FROM exam WHERE courseid = #{courseID}")
    List<ExamLiteDao> findLiteAllByCourseID(@Param("courseID") String ID);

    @Insert("INSERT INTO exam(examid,courseid,startdate,enddate,duration) VALUES(#{examID},#{courseID},#{startDate},#{endDate},#{duration})")
    int insertExam(Exam exam);

    @Update("UPDATE exam SET startdate = #{startDate} , enddate = #{endDate}, duration = #{duration} WHERE examid=#{examID} AND courseid=#{courseID}")
    int updateExam(Exam exam);

    @Delete("DELETE FROM exam WHERE examid=#{examID}")
    int deleteExam(@Param("examID") String ID);

}
