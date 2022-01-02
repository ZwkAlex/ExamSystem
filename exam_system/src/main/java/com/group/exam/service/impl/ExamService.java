package com.group.exam.service.impl;

import com.group.exam.dao.*;
import com.group.exam.model.daoModel.QuestionDao;
import com.group.exam.model.daoModel.StudentAnswerDao;
import com.group.exam.model.entity.*;
import com.group.exam.model.requestModel.StartExamRequest;
import com.group.exam.model.requestModel.StopExamRequest;
import com.group.exam.model.responseModel.StudentExamInfoResponse;
import com.group.exam.model.responseModel.StudentExamResponse;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.model.responseModel.ScoreResponse;
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
    public ResponseModel getStudentExamList(Student student) {
        if(!studentMapper.checkStudent(student.getsID())){
            return ResponseUtil.error("错误的学生ID");
        }
        try {
            List<StudentExamResponse> examList = new ArrayList<>();
            List<StudentExam> studentExamList = studentExamMapper.findAllBySID(student.getsID());
            for (StudentExam s_e : studentExamList) {
                examList.add(buildExamResponse(s_e,false));
            }
            String msg = String.format("当前有 %d 条考试信息。", examList.size());
            return examList.size() == 0 ?
                    ResponseUtil.empty("当前无考试信息。") :
                    ResponseUtil.success(ResponseUtil.success(examList, msg));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseUtil.error("获取学生考试表中遇见不可预期的错误。");
        }
    }


    @Override
    public ResponseModel getExamInfo(StudentExam studentExam) {
        if(!studentMapper.checkStudent(studentExam.getsID())){
            return ResponseUtil.error("错误的学生ID");
        }
        try{
            return ResponseUtil.success(buildExamResponse(studentExam,true));
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
    public ResponseModel getScoreList(Student student) {
        try {
            if(!studentMapper.checkStudent(student.getsID())){
                return ResponseUtil.error("错误的学生ID");
            }
            List<ScoreResponse> scoreList = new ArrayList<>();
            List<StudentExam> studentExamList = studentExamMapper.findAllBySID(student.getsID());
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
            String msg = String.format("当前有{%d}条成绩信息。", scoreList.size());
            return scoreList.size() == 0 ?
                    ResponseUtil.empty("当前无考试信息。") :
                    ResponseUtil.success(scoreList, msg);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseUtil.error("获取学生成绩过程中遇见不可预期的错误");
        }
    }

    @Override
    public ResponseModel startExam(StartExamRequest startExamRequest) {
        if(!studentMapper.checkStudent(startExamRequest.getsID())){
            return ResponseUtil.error("错误的学生ID");
        }
        Exam exam = examMapper.findByExamID(startExamRequest.getExamID());
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if(now.before(exam.getStartDate()) || now.after(exam.getEndDate())){
            return ResponseUtil.error("所选考试不在时间范围内。");
        }
        if(examTimerMapper.checkStudentInExam(startExamRequest.getsID())){
            return ResponseUtil.error("你有其他考试正在进行。");
        }
        StudentExam s_e=studentExamMapper.findBySIDAndExamID(startExamRequest.getsID(),startExamRequest.getExamID());
        if(s_e.getStatus() != ExamStatus.UNFINISHED.getValue()){
            return ResponseUtil.error("考试已完成或已被取消");
        }
        int i = examTimerMapper.startExam(startExamRequest.getsID(), startExamRequest.getExamID() , Integer.toString(exam.getDuration()));
        return i == 1?ResponseUtil.success():ResponseUtil.error();
    }

    @Override
    public ResponseModel stopExam(StopExamRequest stopExamRequest) {
        if(!studentMapper.checkStudent(stopExamRequest.getsID())){
            return ResponseUtil.error("错误的学生ID");
        }
        try {
            examTimerMapper.stopExam(stopExamRequest.getsID(), stopExamRequest.getExamID());
        }catch(Exception e){
            log.info("timer 已被删除。");
//            e.printStackTrace();
        }
        try{
            for(Answer answer : stopExamRequest.getAnswers()) {
                if(!questionMapper.checkQuestion(answer.getQuestionID()))
                    throw new Exception("没有找到对应试题，疑似错误的请求。");
                StudentAnswer studentAnswer = new StudentAnswer();
                studentAnswer.setQuestionID(answer.getQuestionID());
                studentAnswer.setAnswer(answer.getAnswer());
                studentAnswer.setsID(stopExamRequest.getsID());
                studentAnswer.setExamID(stopExamRequest.getExamID());
                studentAnswerMapper.saveStudentAnswer(new StudentAnswerDao(studentAnswer));
            }
            studentExamMapper.updateStudentExamStatus(stopExamRequest.getsID(),
                    stopExamRequest.getExamID(),
                    ExamStatus.FINISHED.getValue());
            return ResponseUtil.success();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ResponseUtil.error("提交试题答案时发生错误。");
    }

    private StudentExamInfoResponse buildExamResponse(StudentExam s_e, boolean getDetails) {
        Exam exam = examMapper.findByExamID(s_e.getExamID());
        Course course = courseMapper.findByCourseID(exam.getCourseID());
        Teacher teacher = teacherMapper.findByID(course.gettID());
        StudentExamInfoResponse temp = new StudentExamInfoResponse();
        temp.setCourseName(course.getCourseName());
        temp.settName(teacher.gettName());
        temp.setDescription(course.getDescription());
        temp.setStartDate(exam.getStartDate().toString());
        temp.setEndDate(exam.getEndDate().toString());
        if(getDetails)
            temp.setNumberOfQuestions(String.format("%d 题",questionMapper.countByExamID(s_e.getExamID())));
            temp.setDuration(ExamUtil.Sec2String(exam.getDuration()));
            temp.setTotalScore(String.format("%.2f 分",exam.getTotalScore()));
        return temp;
    }


}
