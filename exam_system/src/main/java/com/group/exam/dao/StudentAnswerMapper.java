package com.group.exam.dao;

import com.group.exam.model.daoModel.StudentAnswerDao;
import com.group.exam.model.entity.StudentAnswer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StudentAnswerMapper {
    @Insert("INSERT INTO studentanswer(sid,examid,questionid,answer) VALUES(#{sID},#{examID},#{questionID},#{answer}) " +
            "ON DUPLICATE KEY UPDATE answer = #{answer}")
    int saveStudentAnswer(StudentAnswerDao studentAnswer);

    @Select("SELECT * FROM studentanswer WHERE examid = #{examID} AND sid = #{sID}")
    List<StudentAnswerDao> findAllByExamIDAndSID(@Param("sID")String sID, @Param("examID")String examID);

    @Update("UPDATE studentanswer SET score = #{score}, ismarked = TRUE WHERE sID = #{sID} AND examID = #{examID} " +
            "AND questionid = #{questionID}")
    int setScore(@Param("sID")String sID, @Param("examID")String examID, @Param("questionID") String questionID,
                 @Param("score") double score);

    @Delete("DELETE FROM studentanswer WHERE examid = #{examID}")
    int deleteAllStudentAnswerByExamID(@Param("examID")String examID);

    @Delete("DELETE FROM studentanswer WHERE Questionid = #{QuestionID}")
    int deleteAllStudentAnswerByQuestionID(@Param("QuestionID")String QuestionID);
}
