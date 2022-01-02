package com.group.exam.dao;

import com.group.exam.model.daoModel.StudentAnswerDao;
import com.group.exam.model.entity.StudentAnswer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentAnswerMapper {
    @Insert("INSERT INTO studentanswer(sid,examid,questionid,answer) VALUES(#{sid},#{examid},#{questionid},#{answer})")
    int saveStudentAnswer(StudentAnswerDao studentAnswer);

    @Select("SELECT * FROM studentanswer WHERE examID = #{examID} AND sID = #{sID}")
    List<StudentAnswerDao> findAllByExamIDAndSID(@Param("sID")String sID, @Param("examID")String examID);

    @Update("UPDATE studentanswer SET score = #{score}, ismarked = TRUE WHERE sID = #{sid} AND examID = #{examid}")
    int setScore(@Param("sID")String sID, @Param("examID")String examID, @Param("score") double score);
}
