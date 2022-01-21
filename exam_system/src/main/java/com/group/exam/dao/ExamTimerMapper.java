package com.group.exam.dao;

import com.group.exam.model.entity.ExamTimer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ExamTimerMapper {
    @Select("SELECT * FROM timer")
    List<ExamTimer> findAll();

    @Delete("DELETE FROM timer WHERE NOW() > endtime")
    int deleteExpired();

    @Select("SELECT count(*)=1 FROM timer WHERE sid = #{id} and examid <> #{examID}")
    boolean checkStudentInOtherExam(@Param("id") String ID,@Param("examID") String examID);

    @Select("SELECT count(*)=1 FROM timer WHERE sid = #{id} and examid = #{examID}")
    boolean checkStudentInExam(@Param("id") String ID,@Param("examID") String examID);

    @Select("SELECT * FROM timer WHERE sid = #{id} and examid = #{examID}")
    ExamTimer findById(@Param("id") String ID,@Param("examID") String examID);

    @Insert("INSERT INTO timer VALUES(#{sid},#{examID},DATE_ADD(NOW(),INTERVAL #{time} SECOND))")
    int startExam(@Param("sid") String sID, @Param("examID") String testID, @Param("time") String time);

    @Delete("Delete FROM timer WHERE sid = #{sid} AND examid = #{examID}")
    int stopExam(@Param("sid") String sID, @Param("examID") String testID);

}
