package com.group.exam.service.impl;

import com.group.exam.dao.*;
import com.group.exam.model.daoModel.QuestionDao;
import com.group.exam.model.daoModel.StudentAnswerDao;
import com.group.exam.model.entity.*;
import com.group.exam.model.requestModel.StartExamRequest;
import com.group.exam.model.requestModel.StopExamRequest;
import com.group.exam.model.requestModel.StudentExamInfoRequest;
import com.group.exam.model.requestModel.TimeCheckRequest;
import com.group.exam.model.responseModel.*;
import com.group.exam.service.intf.ExamServiceInterface;
import com.group.exam.model.cusEnum.ExamStatus;
import com.group.exam.util.ExamUtil;
import com.group.exam.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExamService implements ExamServiceInterface {
    private static final Logger log = LoggerFactory.getLogger(ExamService.class);
    @Resource
    StudentMapper studentMapper;
    @Resource
    ExamMapper examMapper;
    @Resource
    QuestionMapper questionMapper;
    @Resource
    ExamTimerMapper examTimerMapper;
    @Resource
    StudentAnswerMapper studentAnswerMapper;
    @Resource
    StudentExamMapper studentExamMapper;
    @Resource
    CourseMapper courseMapper;
    @Resource
    TeacherMapper teacherMapper;

    @Override
    public ResponseModel getStudentExamList(String sID) {
        if(!studentMapper.checkStudent(sID)){
            return ResponseUtil.error("错误的学生ID");
        }
        try {
            List<StudentExamResponse> examList = new ArrayList<>();
            List<StudentExam> studentExamList = studentExamMapper.findAllBySID(sID);
            for (StudentExam s_e : studentExamList) {
                Exam exam = examMapper.findByExamID(s_e.getExamID());
                Course course = courseMapper.findByCourseID(exam.getCourseID());
                StudentExamResponse temp = new StudentExamResponse(course,exam);
                examList.add(temp);
            }
            String msg = String.format("当前有 %d 条考试信息。", examList.size());
            return examList.size() == 0 ?
                    ResponseUtil.empty("当前无考试信息。") :
                    ResponseUtil.success(examList, msg);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseUtil.error("获取学生考试表中遇见不可预期的错误。");
        }
    }


    @Override
    public ResponseModel getExamInfo(String id,StudentExamInfoRequest request) {
        if(!studentMapper.checkStudent(id)){
            return ResponseUtil.error("错误的学生ID");
        }
        String examID = request.getExamID();
        try{
            StudentExam studentExam = studentExamMapper.findBySIDAndExamID(id,examID);
            Exam exam = examMapper.findByExamID(examID);
            Course course = courseMapper.findByCourseID(exam.getCourseID());
            Teacher teacher = teacherMapper.findByID(course.gettID());
            StudentExamInfoResponse response = new StudentExamInfoResponse(course, exam, teacher);
            response.setNumberOfQuestions(String.format("%d 题",questionMapper.countByExamID(examID)));
            response.setTotalScore(String.format("%.2f 分",exam.getTotalScore()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if(studentExam.getStatus() == ExamStatus.UNFINISHED.getValue()){
                if(now.after(exam.getStartDate())&& now.before(exam.getEndDate())) {
                    response.setIsValid(true);
                }else{
                    response.setIsValid(false);
                    response.setStatus("未在考试时间范围内");
                }
            }else{
                response.setIsValid(false);
                response.setStatus(ExamStatus.get(studentExam.getStatus()).getMsg());
            }
            return ResponseUtil.success(response);
        }catch(Exception e){
            return ResponseUtil.error("获取学生考试信息中遇见不可预期的错误。");
        }
    }

    @Override
    public ResponseModel getQuestionList(String examID) {
        List<QuestionDao> questionDaoList = questionMapper.findAllByExamID(examID);
        List<Question> questions = new ArrayList<>();
        for(QuestionDao questionDao : questionDaoList) {
            questions.add(new Question(questionDao));
        }
        return ResponseUtil.success(questions);
    }

    @Override
    public ResponseModel getScoreList(String id) {
        try {
            if(!studentMapper.checkStudent(id)){
                return ResponseUtil.error("错误的学生ID");
            }
            List<ScoreResponse> scoreList = new ArrayList<>();
            List<StudentExam> studentExamList = studentExamMapper.findAllBySID(id);
            for (StudentExam studentExam : studentExamList) {
                Exam exam = examMapper.findByExamID(studentExam.getExamID());
                Course course = courseMapper.findByCourseID(exam.getCourseID());
                Teacher teacher = teacherMapper.findByID(course.gettID());
                ScoreResponse temp = new ScoreResponse();
                temp.setCourseName(course.getCourseName());
                temp.setStatus(ExamUtil.Status2String(studentExam.getStatus()));
                temp.settName(teacher.gettName());
                temp.setScore(ExamUtil.Score2String(studentExam.getScore(),studentExam.getStatus()));
                scoreList.add(temp);
            }
            String msg = String.format("当前有 %d 条成绩信息。", scoreList.size());
            return scoreList.size() == 0 ?
                    ResponseUtil.empty("当前无考试信息。") :
                    ResponseUtil.success(scoreList, msg);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseUtil.error("获取学生成绩过程中遇见不可预期的错误");
        }
    }

    @Override
    public ResponseModel startExam(String id, StartExamRequest startExamRequest) {
        String examID = startExamRequest.getExamID();
        if(!studentMapper.checkStudent(id)){
            return ResponseUtil.error("错误的学生ID");
        }
        Exam exam = examMapper.findByExamID(examID);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(now.before(exam.getStartDate()) || now.after(exam.getEndDate())){
            return ResponseUtil.error("所选考试不在时间范围内。");
        }
        if(examTimerMapper.checkStudentInOtherExam(id, examID)){
            return ResponseUtil.error("你有其他考试正在进行。");
        }
        StudentExam s_e=studentExamMapper.findBySIDAndExamID(id,examID);
        if(s_e.getStatus() != ExamStatus.UNFINISHED.getValue()){
            return ResponseUtil.error("考试已完成或已被取消");
        }
        if(!examTimerMapper.checkStudentInExam(id,examID)){
            examTimerMapper.startExam(id, examID , Integer.toString(exam.getDuration()));
        }
        return ResponseUtil.success();
    }

    @Override
    public ResponseModel stopExam(String id, StopExamRequest request) {
        String examID = request.getExamID();
        if(!studentMapper.checkStudent(id)){
            return ResponseUtil.error("错误的学生ID");
        }
        try {
            examTimerMapper.stopExam(id, examID);
        }catch(Exception e){
            log.info("timer 已被删除。");
//            e.printStackTrace();
        }
        try{
            for(Answer answer : request.getAnswers()) {
                if(!questionMapper.checkQuestion(answer.getQuestionID()))
                    throw new Exception("没有找到对应试题，疑似错误的请求。");
                StudentAnswer studentAnswer = new StudentAnswer();
                studentAnswer.setQuestionID(answer.getQuestionID());
                studentAnswer.setAnswer(answer.getAnswer());
                studentAnswer.setsID(id);
                studentAnswer.setExamID(examID);
                studentAnswerMapper.saveStudentAnswer(new StudentAnswerDao(studentAnswer));
            }
            studentExamMapper.updateStudentExamStatus(id, examID,
                    ExamStatus.FINISHED.getValue());
            return ResponseUtil.success();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseUtil.error("提交试题答案时发生错误。");
    }

    @Override
    public ResponseModel checkTime(String id, TimeCheckRequest request) {
        TimeCheckResponse response = new TimeCheckResponse();
        response.setUnexpired(examTimerMapper.checkStudentInExam(id,request.getExamID()));
        return ResponseUtil.success(response);
    }


}
