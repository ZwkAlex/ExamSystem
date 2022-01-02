package com.group.exam.dao;

import com.group.exam.model.entity.StudentExam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface StudentExamMapper {
    @Select("SELECT * FROM s_e WHERE sID = #{sID}")
    List<StudentExam> findAllBySID(@Param("sID") String ID);

    @Select("SELECT * FROM s_e WHERE examID = #{examID}")
    List<StudentExam> findAllByExamID(@Param("examID") String ID);

    @Select("SELECT * FROM s_e WHERE examID = #{examID} AND status = 1")
    List<StudentExam> findAllFinishedWithExamID(@Param("examID") String ID);

    @Select("SELECT * FROM s_e WHERE examID = #{examID} AND sID = #{sID}")
    StudentExam findBySIDAndExamID(@Param("sID") String sID, @Param("examID") String examID);

    @Select("SELECT COUNT(*)=1 FROM s_e WHERE sID = #{sid} AND examID = #{examid}")
    boolean checkStudentExam(StudentExam studentExam);

    @Insert("INSERT INTO s_e(sid,examid) VALUES(#{sid},#{examid}))")
    int insertStudentExam(StudentExam studentExam);

    @Update("UPDATE s_e SET status = #{status} WHERE sID = #{sid} AND examID = #{examid}")
    int updateStudentExamStatus(@Param("sid") String sID,@Param("examid") String examID,@Param("status") int status);

    @Update("UPDATE s_e SET score = #{score} WHERE sID = #{sid} AND examID = #{examid}")
    int updateStudentExamScore(@Param("sid") String sID,@Param("examid") String examID,@Param("score") double score);
}
