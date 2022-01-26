package com.group.exam.dao;

import com.group.exam.model.daoModel.QuestionDao;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface QuestionMapper {
    @Select("SELECT * FROM question WHERE examID = #{id}")
    List<QuestionDao> findAllByExamID(@Param("id") String ID);

    @Select("SELECT * FROM question WHERE questionid = #{id}")
    QuestionDao findByQuestionID(@Param("id") String ID);

    @Select("SELECT COUNT(*)=1 FROM question WHERE questionid = #{id}")
    boolean checkQuestion(@Param("id") String ID);

    @Select("SELECT COUNT(*) FROM question WHERE examid = #{id}")
    int countByExamID(@Param("id") String ID);

    @Insert("INSERT INTO question VALUES(#{questionID},#{examID},#{type},#{title},#{options},#{score})")
    int addQuestion(QuestionDao question);

    @Delete("DELETE FROM question WHERE questionid=#{questionID}")
    int deleteQuestion(@Param("questionID") String questionID);

    @Update("UPDATE  question SET examID = #{examID}, type = #{type}, title = #{title},  " +
            "options = #{options}, score = #{score}  WHERE questionID = #{questionID}")
    int updateQuestion(QuestionDao question);

    @Delete("DELETE FROM question WHERE examid=#{examID}")
    int deleteAllQuestionsByExamID(@Param("examID") String examID);
}
