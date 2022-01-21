package com.group.exam.service.impl;

import com.group.exam.dao.*;
import com.group.exam.model.cusEnum.ExamStatus;
import com.group.exam.model.daoModel.*;
import com.group.exam.model.entity.*;
import com.group.exam.model.requestModel.*;
import com.group.exam.model.responseModel.*;
import com.group.exam.service.intf.TeacherServiceInterface;
import com.group.exam.util.ExamUtil;
import com.group.exam.util.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherService implements  TeacherServiceInterface {

    @Resource
    private UserMapper userMapper;
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private StudentExamMapper studentExamMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private StudentAnswerMapper studentAnswerMapper;
    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private MajorMapper majorMapper;


    @Override
    public ResponseModel getTeacherInfo(String tID) {
        try {
            TeacherInfoResponse response = new TeacherInfoResponse(teacherMapper.findByID(tID));
            return ResponseUtil.success(response);
        }catch(Exception e){
            return ResponseUtil.error("未找到当前教师信息");
        }
    }

    @Override
    public ResponseModel getStudentList() {
        List<StudentListResponse> studentList= new ArrayList<StudentListResponse>();
        for(Student student:studentMapper.findAll()){
            User user = userMapper.findByID(student.getsID());
            StudentListResponse temp = new StudentListResponse(student, user);
            studentList.add(temp);
        }
        return ResponseUtil.success(studentList);
    }

    @Override
    public ResponseModel addStudent(AlterStudentRequest request) {
        if(studentMapper.findByID(request.getsID()) == null && teacherMapper.findByID(request.getsID()) == null){
            return studentMapper.insertStudent(request) == 1&&
                    userMapper.insertStudentUser(request.getsID(),request.getPassword()) == 1?
                    ResponseUtil.success("添加成功"):
                    ResponseUtil.error("添加失败");
        }else{
            return ResponseUtil.error("当前ID已存在");
        }
    }

    @Override
    public ResponseModel deleteStudent(String sID) {
        return studentMapper.deleteStudent(sID) == 1&&userMapper.deleteStudentUser(sID)==1?
                ResponseUtil.success("删除成功"):
                ResponseUtil.error("删除失败");
    }

    @Override
    public ResponseModel updateStudent(AlterStudentRequest request) {
        return studentMapper.updateStudent(request) == 1&&userMapper.changePassword(request.getsID(),request.getPassword()) == 1?
                ResponseUtil.success("编辑成功"):
                ResponseUtil.error("编辑失败");
    }

    @Override
    public ResponseModel addQuestion(QuestionRequest questionRequest) {
        if (questionMapper.findByQuestionID(questionRequest.getQuestionID()) != null)
            return ResponseUtil.error("已存在该题目标识号");
        Answer answer = new Answer(questionRequest);
        Question question = new Question(questionRequest);
        return questionMapper.addQuestion(new QuestionDao(question)) == 1 &&
                answerMapper.addAnswer(new AnswerDao(answer)) == 1?
                ResponseUtil.success("添加成功"):
                ResponseUtil.error("添加失败");
    }

    @Override
    public ResponseModel deleteQuestion(String questionID) {
        if (questionMapper.findByQuestionID(questionID) == null)
            return ResponseUtil.error("不存在该题目标识号");
        return questionMapper.deleteQuestion(questionID) == 1 && answerMapper.deleteAnswer(questionID) == 1?
                ResponseUtil.success("删除成功"):
                ResponseUtil.error("删除失败");
    }

    @Override
    public ResponseModel updateQuestion(QuestionRequest questionRequest) {
        if (questionMapper.findByQuestionID(questionRequest.getQuestionID()) == null)
            return ResponseUtil.error("不存在该题目标识号");
        Answer answer = new Answer(questionRequest);
        Question question = new Question(questionRequest);
        return questionMapper.updateStudent(new QuestionDao(question))== 1&&
                answerMapper.updateAnswer(new AnswerDao(answer)) == 1?
                ResponseUtil.success("编辑成功"):
                ResponseUtil.error("编辑失败");
    }

    @Override
    public ResponseModel getCourseList(String id) {
        List<Course> courses = courseMapper.findAllByTID(id);
        return courses != null?ResponseUtil.success(courses):ResponseUtil.error("暂无课程");
    }

    @Override
    public ResponseModel getCourseListLite(String id) {
        List<CourseLiteDao> courses = courseMapper.findLiteAllByTID(id);
        if(courses == null)
            courses = new ArrayList<>();
        return ResponseUtil.success(courses);
    }


    @Override
    public ResponseModel addCourse(Course course) {
        if (courseMapper.findByCourseID(course.getCourseID()) != null)
            return ResponseUtil.error("已存在该课程号");
        return courseMapper.insertCourse(course) == 1 ?
                ResponseUtil.success("添加成功") :
                ResponseUtil.error("添加失败");
    }

    @Override
    public ResponseModel deleteCourse(String courseID) {
        if(courseMapper.findByCourseID(courseID) == null)
            return ResponseUtil.error("该课程号不存在");
        return courseMapper.deleteCourse(courseID) == 1?
                ResponseUtil.success("删除成功"):
                ResponseUtil.error("删除失败");
    }

    @Override
    public ResponseModel updateCourse(Course course) {
        if(courseMapper.findByCourseID(course.getCourseID()) == null)
            return ResponseUtil.error("该课程号不存在");
        return courseMapper.updateCourse(course) == 1?
                ResponseUtil.success("编辑成功"):
                ResponseUtil.error("编辑失败");
    }

    @Override
    public ResponseModel addExam(AlterExamRequest request) {
        if(examMapper.findByExamID(request.getExamID()) != null)
            return ResponseUtil.error("该考卷号已存在");
        Exam exam = new Exam(request);
        return examMapper.insertExam(exam)==1?ResponseUtil.success():ResponseUtil.error("创建失败");
    }

    @Override
    public ResponseModel updateExam(AlterExamRequest request) {
        if(examMapper.findByExamID(request.getExamID()) == null)
            return ResponseUtil.error("该考卷号不存在");
        Exam exam = new Exam(request);
        return examMapper.updateExam(exam)==1?ResponseUtil.success():ResponseUtil.error("修改失败");
    }

    @Override
    public ResponseModel deleteExam(AlterExamRequest request) {
        if(examMapper.findByExamID(request.getExamID()) == null)
            return ResponseUtil.error("该考卷号不存在");
        examMapper.deleteExam(request.getExamID());
        studentExamMapper.deleteAllStudentExamByExamID(request.getExamID());
        return ResponseUtil.success();
    }


    @Override
    public ResponseModel assignExam(AssignMajorExamRequest assignExamRequest) {
        try {
            List<Student> studentList = studentMapper.findBySmajorID(assignExamRequest.getSmajorID());
            for (Student student : studentList) {
                StudentExam s_e = new StudentExam();
                s_e.setExamID(assignExamRequest.getExamID());
                s_e.setsID(student.getsID());
                if (!studentExamMapper.checkStudentExam(s_e))
                    studentExamMapper.insertStudentExam(s_e);
            }
            return ResponseUtil.success("分配考试成功");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtil.error("分配考试失败");
    }

    @Override
    public ResponseModel getTeacherExamList(String tID) {
        try {
            if(tID == null) return ResponseUtil.error("错误的请求");
            List<TeacherExamListResponse> examListResponseList = new ArrayList<>();
            List<Course> courseList = courseMapper.findAllByTID(tID);
            for (Course course : courseList) {
                TeacherExamListResponse examListResponse = new TeacherExamListResponse();
                examListResponse.setCourseID(course.getCourseID());
                examListResponse.setCourseName(course.getCourseName());
                List<TeacherExamResponse> examList = new ArrayList<>();
                for (Exam exam : examMapper.findAllByCourseID(course.getCourseID())) {
                    TeacherExamResponse temp = new TeacherExamResponse();
                    temp.setExamID(exam.getExamID());
                    temp.setStartDate(exam.getStartDate().toString());
                    temp.setEndDate(exam.getEndDate().toString());
                    temp.setDuration(ExamUtil.Sec2String(exam.getDuration()));
                    temp.setHours(ExamUtil.Sec2Hours(exam.getDuration()));
                    temp.setMinutes(ExamUtil.Sec2Minutes(exam.getDuration()));
                    temp.setSeconds(ExamUtil.Sec2Sec(exam.getDuration()));
                    temp.setTotalScore(String.format("%.2f 分", exam.getTotalScore()));
                    examList.add(temp);
                }
                examListResponse.setExamList(examList);
                examListResponseList.add(examListResponse);
            }
            return ResponseUtil.success(examListResponseList);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseUtil.error("获取教师考试信息时发生未知的错误。");
        }
    }

    @Override
    public ResponseModel getTeacherExamListLite(String courseID) {
        List<ExamLiteListResponse> examLiteList = new ArrayList<>();
        for(ExamLiteDao examLiteDao:examMapper.findLiteAllByCourseID(courseID)){
            ExamLiteListResponse temp = new ExamLiteListResponse();
            temp.setExamID(examLiteDao.getExamID());
            temp.setStartToStop(ExamUtil.StartToStopStringFormat(examLiteDao.getStartDate(),examLiteDao.getEndDate()));
            examLiteList.add(temp);
        }
        return ResponseUtil.success(examLiteList);
    }

    @Override
    public ResponseModel getMajorList() {
        List<Major> majorList = majorMapper.findAll();
        return ResponseUtil.success(majorList);
    }

    @Override
    public ResponseModel getTeacherExamInfo(TeacherExamInfoRequest teacherExamInfoRequest) {
        try {
            TeacherExamInfoResponse teacherExamInfoResponse = new TeacherExamInfoResponse();
            Exam exam = examMapper.findByExamID(teacherExamInfoRequest.getExamID());
            Course course = courseMapper.findByCourseID(exam.getCourseID());
            teacherExamInfoResponse.setCourseName(course.getCourseName());
            teacherExamInfoResponse.setExamID(exam.getExamID());
            teacherExamInfoResponse.setStartDate(exam.getStartDate().toString());
            teacherExamInfoResponse.setEndDate(exam.getEndDate().toString());
            teacherExamInfoResponse.setDuration(ExamUtil.Sec2String(exam.getDuration()));
            teacherExamInfoResponse.setTotalScore(ExamUtil.Score2String(exam.getTotalScore()));
            List<StudentExam> s_eList = studentExamMapper.findAllByExamID(teacherExamInfoRequest.getExamID());
            int marked = 0, unfinished = 0, finished = 0;
            for (StudentExam s_e : s_eList) {
                switch (ExamStatus.get(s_e.getStatus())) {
                    case MARKED:
                        marked += 1;
                        break;
                    case MARKING:
                    case FINISHED:
                        finished += 1;
                        break;
                    case UNFINISHED:
                        unfinished += 1;
                        break;
                }
            }
            teacherExamInfoResponse.setTotalNumber(marked + unfinished + finished);
            teacherExamInfoResponse.setFinished(finished);
            teacherExamInfoResponse.setUnFinished(unfinished);
            teacherExamInfoResponse.setMarked(marked);
            return ResponseUtil.success(teacherExamInfoResponse);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtil.error("获取考试详情信息的过程中发生未知错误");
        }
    }

    @Override
    public ResponseModel getStudentExamList(TeacherExamInfoRequest teacherExamInfoRequest) {
        try {
            List<StudentExam> s_eList = studentExamMapper.findAllByExamID(teacherExamInfoRequest.getExamID());
            List<StudentNeedExam> studentNeedExamList = new ArrayList<>();
            for (StudentExam s_e : s_eList) {
                StudentNeedExam temp = new StudentNeedExam();
                temp.set(s_e);
                temp.set(studentMapper.findByID(s_e.getsID()));
                studentNeedExamList.add(temp);
            }
            return ResponseUtil.success(studentNeedExamList);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtil.error("获取需要考试的学生列表过程中发生未知错误");
        }
    }

    @Override
    public ResponseModel getStudentExamNeedMark(StudentExamNeedMarkRequest studentExamNeedMarkRequest) {
        try {
            return ResponseUtil.success(getStudentExamNeedMarkResponse(studentExamNeedMarkRequest));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("在获取学生答卷时发生错误。");
        }
    }

    @Override
    public ResponseModel getNextStudentExamNeedMark(NextStudentExamNeedMarkRequest nextStudentExamNeedMarkRequest) {
        try {
            StudentExamNeedMarkRequest studentExamNeedMarkRequest = (StudentExamNeedMarkRequest) nextStudentExamNeedMarkRequest;
            List<StudentExam> s_eList= studentExamMapper.findAllFinishedWithExamID(studentExamNeedMarkRequest.getExamID());
            StudentExam s_e= s_eList.get(0);
            studentExamNeedMarkRequest.setsID(s_e.getsID());
            studentExamMapper.updateStudentExamStatus(s_e.getsID(),s_e.getExamID(), ExamStatus.MARKING.getValue());
            return ResponseUtil.success(getStudentExamNeedMarkResponse(studentExamNeedMarkRequest));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("在获取下一个学生答卷时发生错误。");
        }
    }

    private StudentExamNeedMarkResponse getStudentExamNeedMarkResponse (StudentExamNeedMarkRequest studentExamNeedMarkRequest){
        List<StudentAnswerDao> answers = studentAnswerMapper.findAllByExamIDAndSID(
                studentExamNeedMarkRequest.getsID(), studentExamNeedMarkRequest.getExamID());
        Student student = studentMapper.findByID(studentExamNeedMarkRequest.getsID());
        StudentExamNeedMarkResponse studentExamNeedMarkResponse = new StudentExamNeedMarkResponse();
        studentExamNeedMarkResponse.setsID(studentExamNeedMarkRequest.getsID());
        studentExamNeedMarkResponse.setsName(student.getsName());
        studentExamNeedMarkResponse.setExamID(studentExamNeedMarkRequest.getExamID());
        List<AnswerNeedMarkModel> answerNeedMarkList = new ArrayList<>();
        for (StudentAnswerDao a : answers) {
            StudentAnswer answer = new StudentAnswer(a);
            Question question = new Question(questionMapper.findByQuestionID(answer.getQuestionID()));
            Answer true_answer = new Answer(answerMapper.findByQuestionID(answer.getQuestionID()));
            AnswerNeedMarkModel temp = new AnswerNeedMarkModel();
            temp.setTitle(question.getTitle());
            temp.setType(question.getType().getValue());
            temp.setTypeString(question.getType().getMsg());
            temp.setStudentAnswer(ExamUtil.expandAnswer(question, answer.getAnswer()));
            temp.setTrueAnswer(ExamUtil.expandAnswer(question, true_answer.getAnswer()));
            temp.setAutoMark(ExamUtil.AutoMark(
                    question.getType(), answer.getAnswer(),
                    true_answer.getAnswer(), question.getScore()));
            answerNeedMarkList.add(temp);
        }
        studentExamNeedMarkResponse.setAnswers(answerNeedMarkList);
        return studentExamNeedMarkResponse;
    }

    @Override
    public ResponseModel submitMarkedExam(MarkedExamRequest markedExamRequest) {
        try {
            double totalScore = 0;
            for (MarkedAnswerModel markedAnswer : markedExamRequest.getMarkedAnswers()) {
                totalScore += markedAnswer.getScore();
                studentAnswerMapper.setScore(markedExamRequest.getsID(),
                        markedExamRequest.getExamID(),markedAnswer.getQuestionID(), markedAnswer.getScore());
            }
            studentExamMapper.updateStudentExamStatus(markedExamRequest.getsID(),
                    markedExamRequest.getExamID(), ExamStatus.MARKED.getValue());
            studentExamMapper.updateStudentExamScore(markedExamRequest.getsID(),
                    markedExamRequest.getExamID(), totalScore);
            return ResponseUtil.success();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("提交评分结果时出现错误");
        }
    }


}
