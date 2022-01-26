package com.group.exam.dao;

import com.group.exam.model.daoModel.AnswerDao;
import com.group.exam.model.entity.Answer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AnswerMapper {
    @Insert("INSERT INTO answer VALUES(#{examID}, #{questionID}, #{answer})")
    int addAnswer(AnswerDao answer);

    @Delete("DELETE FROM answer WHERE questionid = #{questionID}")
    int deleteAnswer(@Param("questionID") String questionID);

    @Delete("DELETE FROM answer WHERE examid = #{examID}")
    int deleteAllAnswerByExamID(@Param("examID") String examID);

    @Select("SELECT * FROM answer WHERE questionid = #{questionID}")
    AnswerDao findByQuestionID(@Param("questionID")String questionID);

    @Select("SELECT * FROM answer WHERE examid = #{examID}")
    List<AnswerDao> findAllByExamID(@Param("examID")String examID);

    @Update("UPDATE  answer SET answer = #{answer}, examid = #{examID} WHERE questionid = #{questionID}")
    int updateAnswer(AnswerDao answer);
}
