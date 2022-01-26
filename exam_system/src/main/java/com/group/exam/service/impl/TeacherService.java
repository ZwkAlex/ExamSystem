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
                ResponseUtil.success("修改成功"):
                ResponseUtil.error("修改失败");
    }

    @Override
    public ResponseModel getQuestionList(TeacherExamInfoRequest request) {
        List<QuestionDao> questionDaoList = questionMapper.findAllByExamID(request.getExamID());
        List<QuestionWithAnswerModel> questionList = new ArrayList<>();
        for(QuestionDao questionDao : questionDaoList) {
            QuestionWithAnswerModel temp = new QuestionWithAnswerModel(questionDao);
            AnswerDao answerDao = answerMapper.findByQuestionID(questionDao.getQuestionID());
            Answer answer = new Answer(answerDao);
            temp.setAnswers(answer.getAnswer());
            questionList.add(temp);
        }
        return ResponseUtil.success(questionList);
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
        try{
            questionMapper.deleteQuestion(questionID);
            answerMapper.deleteAnswer(questionID);
            studentAnswerMapper.deleteAllStudentAnswerByQuestionID(questionID);
            return ResponseUtil.success("删除成功");
        }catch (Exception e) {
            return ResponseUtil.error("删除失败");
        }
    }

    @Override
    public ResponseModel updateQuestion(QuestionRequest questionRequest) {
        if (questionMapper.findByQuestionID(questionRequest.getQuestionID()) == null)
            return ResponseUtil.error("不存在该题目标识号");
        Answer answer = new Answer(questionRequest);
        Question question = new Question(questionRequest);
        return questionMapper.updateQuestion(new QuestionDao(question))== 1&&
                answerMapper.updateAnswer(new AnswerDao(answer)) == 1?
                ResponseUtil.success("修改成功"):
                ResponseUtil.error("修改失败");
    }

    @Override
    public ResponseModel getCourseList(String id) {
        List<CourseDao> courses = courseMapper.findAllValidByTID(id);
        return courses != null?ResponseUtil.success(courses):ResponseUtil.error("暂无课程");
    }

    @Override
    public ResponseModel getCourseListLite(String id) {
        List<CourseLiteDao> courses = courseMapper.findLiteAllValidByTID(id);
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
        try{
            List<ExamDao> examList = examMapper.findAllValidByCourseID(courseID);
            if(examList != null && examList.size() > 0){
                courseMapper.updateCourseToInvalid(courseID);
            }else{
                courseMapper.deleteCourse(courseID);
            }
            return ResponseUtil.success("删除成功");
        }catch(Exception e){
            e.printStackTrace();
            return ResponseUtil.error("删除失败:"+e.getMessage());
        }
    }

    @Override
    public ResponseModel updateCourse(Course course) {
        if(courseMapper.findByCourseID(course.getCourseID()) == null)
            return ResponseUtil.error("该课程号不存在");
        return courseMapper.updateCourse(course) == 1?
                ResponseUtil.success("修改成功"):
                ResponseUtil.error("修改失败");
    }

    @Override
    public ResponseModel updateStudentExamScore(StudentExam studentExam) {
        try {
            studentExamMapper.updateStudentExamScore(studentExam.getsID(), studentExam.getExamID(), studentExam.getScore());
            return ResponseUtil.success("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("修改失败");
        }

    }

    @Override
    public ResponseModel updateStudentExamStatus(StudentExam studentExam) {
        try {
            if(ExamStatus.get(studentExam.getStatus()) != null) {
                studentExamMapper.updateStudentExamStatus(studentExam.getsID(), studentExam.getExamID(), studentExam.getStatus());
                return ResponseUtil.success("修改成功");
            }else{
                return ResponseUtil.error("修改失败，无权修改");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("修改失败");
        }
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
        if(studentExamMapper.findAllByExamID(request.getExamID()).size() > 0){
            examMapper.updateExamToInvalid(request.getExamID());
        }else{
            examMapper.deleteExam(request.getExamID());
        }
        studentExamMapper.updateAllStudentExamStatus(request.getExamID(),ExamStatus.DELETED.getValue());
        studentAnswerMapper.deleteAllStudentAnswerByExamID(request.getExamID());
        questionMapper.deleteAllQuestionsByExamID(request.getExamID());
        answerMapper.deleteAllAnswerByExamID(request.getExamID());
        return ResponseUtil.success();
    }


    @Override
    public ResponseModel assignExam(AssignMajorExamRequest assignExamRequest) {
        try {
            List<Student> studentList = studentMapper.findBySmajorID(assignExamRequest.getsMajorID());
            for (Student student : studentList) {
                StudentExam s_e = new StudentExam();
                s_e.setExamID(assignExamRequest.getExamID());
                s_e.setsID(student.getsID());
                if (!studentExamMapper.checkStudentExam(s_e))
                    studentExamMapper.insertStudentExam(s_e);
            }
            return ResponseUtil.success("发布考试成功");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.error("发布考试失败");
        }
    }

    @Override
    public ResponseModel getTeacherExamList(String tID) {
        try {
            if(tID == null) return ResponseUtil.error("错误的请求");
            List<TeacherExamListResponse> examListResponseList = new ArrayList<>();
            List<CourseDao> courseList = courseMapper.findAllValidByTID(tID);
            for (CourseDao course : courseList) {
                TeacherExamListResponse examListResponse = new TeacherExamListResponse();
                examListResponse.setCourseID(course.getCourseID());
                examListResponse.setCourseName(course.getCourseName());
                List<TeacherExamResponse> examList = new ArrayList<>();
                for (ExamDao exam : examMapper.findAllValidByCourseID(course.getCourseID())) {
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
            return ResponseUtil.error("获取教师考卷信息时发生未知的错误。");
        }
    }

    @Override
    public ResponseModel getTeacherExamListLite(String tID) {
        try {
            List<ExamLiteListResponse> examLiteList = new ArrayList<>();
            List<CourseLiteDao> courseList = courseMapper.findLiteAllValidByTID(tID);
            for (CourseLiteDao course : courseList) {
                ExamLiteListResponse temp = new ExamLiteListResponse();
                temp.setCourseID(course.getCourseID());
                temp.setCourseName(course.getCourseName());
                List<ExamLiteModel> examList = new ArrayList<>();
                for (ExamLiteDao examLiteDao : examMapper.findLiteAllValidByCourseID(course.getCourseID())) {
                    ExamLiteModel lite = new ExamLiteModel();
                    lite.setExamID(examLiteDao.getExamID());
                    lite.setStartToStop(ExamUtil.StartToStopStringFormat(examLiteDao.getStartDate(), examLiteDao.getEndDate()));
                    examList.add(lite);
                }
                temp.setExamList(examList);
                examLiteList.add(temp);
            }
            return ResponseUtil.success(examLiteList);
        }catch (Exception e){
            return ResponseUtil.error("获取教师考卷信息时发生未知的错误。");
        }
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
            ExamDao exam = examMapper.findByExamID(teacherExamInfoRequest.getExamID());
            CourseDao course = courseMapper.findValidByCourseID(exam.getCourseID());
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
                    case DELETED:
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
            teacherExamInfoResponse.setTotalFinished(marked + finished);
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
                Student student = studentMapper.findByID(s_e.getsID());
                temp.set(student);
                temp.setmName(majorMapper.findByID(student.getsMajorID()).getmName());
                studentNeedExamList.add(temp);
            }
            return ResponseUtil.success(studentNeedExamList);
        }catch (Exception e){
            e.printStackTrace();
            return  ResponseUtil.error("获取学生考试信息列表过程中发生未知错误");
        }
    }

    @Override
    public ResponseModel getStudentExamNeedMarkList(TeacherExamInfoRequest teacherExamInfoRequest) {
        try {
            List<StudentExam> s_eList = studentExamMapper.findAllFinishedWithExamID(teacherExamInfoRequest.getExamID());
            List<StudentExamNeedMark> studentNeedExamList = new ArrayList<>();
            for (StudentExam s_e : s_eList) {
                StudentExamNeedMark temp = new StudentExamNeedMark();
                temp.setsID(s_e.getsID());
                Student student = studentMapper.findByID(s_e.getsID());
                temp.setsName(student.getsName());
                temp.setCollege(student.getCollege());
                temp.setmName(majorMapper.findByID(student.getsMajorID()).getmName());
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
            StudentExamNeedMarkRequest studentExamNeedMarkRequest =  new StudentExamNeedMarkRequest();
            studentExamNeedMarkRequest.setExamID(nextStudentExamNeedMarkRequest.getExamID());
            List<StudentExam> s_eList= studentExamMapper.findAllFinishedWithExamID(studentExamNeedMarkRequest.getExamID());
            if(s_eList.size() > 0){
                StudentExam s_e= s_eList.get(0);
                studentExamNeedMarkRequest.setsID(s_e.getsID());
                studentExamMapper.updateStudentExamStatus(s_e.getsID(),s_e.getExamID(), ExamStatus.MARKING.getValue());
                return ResponseUtil.success(getStudentExamNeedMarkResponse(studentExamNeedMarkRequest));
            }else{
                return ResponseUtil.success("已无待评卷试卷");
            }
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
            temp.setQuestionID(question.getQuestionID());
            temp.setTitle(question.getTitle());
            temp.setType(question.getType().getValue());
            temp.setTypeString(question.getType().getMsg());
            temp.setScore(question.getScore());
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
            for (MarkedScoreModel markedAnswer : markedExamRequest.getScores()) {
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
