package com.group.exam.controller;

import com.group.exam.model.entity.*;
import com.group.exam.model.requestModel.*;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.TeacherService;
import com.group.exam.util.AuthUtil;
import com.group.exam.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 *  date:2022/1/1
 *  author:
 */
@Controller
public class TeacherController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Resource
    private TeacherService teacherService;
    @Resource
    private AuthUtil authUtil;

    @RequestMapping(value = "/teacher/info", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherInfo(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format(" 查看教师 -%s- 的个人信息", id));
            return ResponseEntity.ok().body(teacherService.getTeacherInfo(id));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/student/list",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentList() {
        log.info(" 获取学生列表");
        return ResponseEntity.ok(teacherService.getStudentList());
    }

    @RequestMapping(value = "/teacher/student/add",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addStudent(@RequestBody AlterStudentRequest request) {
        log.info(String.format(" 添加学生： -%s-", request.getsID()));
        return ResponseEntity.ok(teacherService.addStudent(request));
    }

    @RequestMapping(value = "/teacher/student/delete",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> deleteStudent(@RequestBody Student student) {
        log.info(String.format(" 删除学生： -%s-", student.getsID()));
        return ResponseEntity.ok(teacherService.deleteStudent(student.getsID()));
    }

    @RequestMapping(value = "/teacher/student/update",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateStudent(@RequestBody AlterStudentRequest request) {
        log.info(String.format(" 更新学生： -%s-", request.getsID()));
        return ResponseEntity.ok(teacherService.updateStudent(request));
    }


    @RequestMapping(value = "/teacher/question/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getQuestionList(@RequestBody TeacherExamInfoRequest request){
        log.info(String.format(" 查询试卷： -%s- 的试题列表", request.getExamID()));
        return ResponseEntity.ok().body(teacherService.getQuestionList(request));
    }


    @RequestMapping(value = "/teacher/question/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addQuestion(@RequestBody QuestionRequest question) {
        log.info(String.format(" 添加试题： -%s- /n 答案：-%s- ", question.getTitle(), question.getAnswers()));
        return ResponseEntity.ok().body(teacherService.addQuestion(question));
    }

    @RequestMapping(value = "/teacher/question/delete", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> deleteQuestion(@RequestBody Question question){
        log.info(String.format(" 移除试题： -%s- ", question.getQuestionID()));
        return ResponseEntity.ok().body(teacherService.deleteQuestion(question.getQuestionID()));
    }

    @RequestMapping(value = "/teacher/question/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateQuestion(@RequestBody QuestionRequest question){
        log.info(String.format("更新试题： -%s- ", question.getQuestionID()));
        return ResponseEntity.ok().body(teacherService.updateQuestion(question));
    }

    @RequestMapping(value = "/teacher/course/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getCourseList(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format(" 获取课程列表： -%s- ", id));
            return ResponseEntity.ok().body(teacherService.getCourseList(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/course/list/lite",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getCourseListLite(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format(" 获取课程lite列表： -%s- ", id));
            return ResponseEntity.ok().body(teacherService.getCourseListLite(id));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }


    @RequestMapping(value = "/teacher/course/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addCourse(@RequestBody Course course) {
        log.info(String.format("教师 -%s-  发布课程 -%s- ", course.gettID(), course.getCourseID()));
        return ResponseEntity.ok().body(teacherService.addCourse(course));
    }

    @RequestMapping(value = "/teacher/course/delete", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> deleteCourse(@RequestBody Course course) {
        log.info(String.format("删除课程 -%s- ", course.getCourseID()));
        return ResponseEntity.ok().body(teacherService.deleteCourse(course.getCourseID()));
    }

    @RequestMapping(value = "/teacher/course/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateCourse(@RequestBody Course course){
        log.info(String.format("更新课程： -%s- ", course.getCourseID()));
        return ResponseEntity.ok().body(teacherService.updateCourse(course));
    }

    @RequestMapping(value = "/teacher/student/exam/score/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateStudentExamScore(@RequestBody StudentExam studentExam){
        log.info(String.format("更新学生-%s- -%s- 的成绩", studentExam.getsID(),studentExam.getExamID()));
        return ResponseEntity.ok().body(teacherService.updateStudentExamScore(studentExam));
    }

    @RequestMapping(value = "/teacher/student/exam/status/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateStudentExamStatus(@RequestBody StudentExam studentExam){
        log.info(String.format("更新学生-%s- -%s- 的状态", studentExam.getsID(),studentExam.getExamID()));
        return ResponseEntity.ok().body(teacherService.updateStudentExamStatus(studentExam));
    }


    @RequestMapping(value = "/teacher/exam/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addExam(@RequestBody AlterExamRequest request){
        log.info(String.format("在课程 -%s- 下添加考试 -%s-", request.getCourseID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.addExam(request));
    }

    @RequestMapping(value = "/teacher/exam/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateExam(@RequestBody AlterExamRequest request){
        log.info(String.format("在课程 -%s- 下更新考试 -%s-", request.getCourseID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.updateExam(request));
    }

    @RequestMapping(value = "/teacher/exam/delete", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> deleteExam(@RequestBody AlterExamRequest request){
        log.info(String.format("在课程 -%s- 下删除考试 -%s-", request.getCourseID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.deleteExam(request));
    }

    @RequestMapping(value = "/teacher/exam/assign", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> assignExam(@RequestBody AssignMajorExamRequest request) {
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 指定 专业-%s- 进行考试 -%s- ", id, request.getExamID(), request.getExamID()));
            return ResponseEntity.ok().body(teacherService.assignExam(request));
        }catch (Exception e) {
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherExamList(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试列表", id));
            return ResponseEntity.ok().body(teacherService.getTeacherExamList(id));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/list/lite",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherExamListLite(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试lite列表", id));
            return ResponseEntity.ok().body(teacherService.getTeacherExamListLite(id));
        }catch (Exception e) {
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/major/list",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getMajorList(){
        log.info(" 获取专业列表 ");
        return ResponseEntity.ok().body(teacherService.getMajorList());
    }

    @RequestMapping(value = "/teacher/exam/info", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherExamInfo(@RequestBody TeacherExamInfoRequest request){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试 -%s- 详情",id, request.getExamID()));
            return ResponseEntity.ok().body(teacherService.getTeacherExamInfo(request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/student/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentExamList(@RequestBody TeacherExamInfoRequest request){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试 -%s- 的学生列表", id, request.getExamID()));
            return ResponseEntity.ok().body(teacherService.getStudentExamList(request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/student/mark/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentExamNeedMarkList(@RequestBody TeacherExamInfoRequest request){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试 -%s- 的待评卷列表", id, request.getExamID()));
            return ResponseEntity.ok().body(teacherService.getStudentExamNeedMarkList(request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/student/mark/one", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentExamNeedMark(@RequestBody StudentExamNeedMarkRequest request){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试 -%s- 学生 -%s- 的试卷",id, request.getExamID(), request.getsID()));
            return ResponseEntity.ok().body(teacherService.getStudentExamNeedMark(request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/student/mark/submit", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> submitMarkedExam(@RequestBody MarkedExamRequest request){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s-   批改学生 -%s- 科目 -%s- 的试卷", id, request.getsID(), request.getExamID()));
            return ResponseEntity.ok().body(teacherService.submitMarkedExam(request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/teacher/exam/student/mark/next", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getNextStudentExamNeedMark(@RequestBody NextStudentExamNeedMarkRequest request){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("教师 -%s- 查询考试 -%s- 下一个学生的试卷", id, request.getExamID()));
            return ResponseEntity.ok().body(teacherService.getNextStudentExamNeedMark(request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }
}
