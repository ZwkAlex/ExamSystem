package com.group.exam.dao;

import com.group.exam.model.entity.ExamTimer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ExamTimerMapper {
    @Select("SELECT * FROM timer")
    List<ExamTimer> findAll();

    @Delete("DELETE FROM timer WHERE UNIX_TIMESTAMP(NOW()) > endtime")
    int deleteExpired();

    @Select("SELECT count(*)=1 FROM timer WHERE sid = #{id}")
    boolean checkStudentInExam(@Param("id") String ID);

    @Select("SELECT * FROM timer WHERE sid = #{id}")
    boolean findById(@Param("id") String ID);

    @Insert("INSERT INTO timer VALUES(#{sid},#{examID},DATE_ADD(NOW(),INTERVAL #{time} SECOND))")
    int startExam(@Param("sid") String sID, @Param("examID") String testID, @Param("time") String time);

    @Delete("Delete FROM timer WHERE sid = #{sid} AND examID = #{examID}")
    int stopExam(@Param("sid") String sID, @Param("examID") String testID);

}
