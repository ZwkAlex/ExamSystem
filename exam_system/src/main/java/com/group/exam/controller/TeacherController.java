package com.group.exam.controller;

import com.group.exam.model.entity.Course;
import com.group.exam.model.entity.Question;
import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.Teacher;
import com.group.exam.model.requestModel.*;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.TeacherService;
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

    @RequestMapping(value = "/teacher/info", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherInfo(@RequestBody Teacher teacher){
        log.info(String.format(" 查看教师 -%s- 的个人信息", teacher.gettID()));
        return ResponseEntity.ok().body(teacherService.getTeacherInfo(teacher.gettID()));
    }

    @RequestMapping(value = "/teacher/student/add",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addStudent(@RequestBody AddStudentRequest request) {
        log.info(String.format(" 添加学生： -%s-", request.getsID()));
        return ResponseEntity.ok(teacherService.addStudent(request));
    }

    @RequestMapping(value = "/teacher/student/delete",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> deleteStudent(@RequestBody Student student) {
        log.info(String.format(" 删除学生： -%s-", student.getsID()));
        return ResponseEntity.ok(teacherService.deleteStudent(student.getsID()));
    }

    @RequestMapping(value = "/teacher/student/update",method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateStudent(@RequestBody Student student) {
        log.info(String.format(" 更新学生： -%s-", student.getsID()));
        return ResponseEntity.ok(teacherService.updateStudent(student));
    }

    @RequestMapping(value = "/teacher/question/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addQuestion(@RequestBody QuestionRequest question) {
        log.info(String.format(" 添加试题： -%s- /n 答案：-%s- ", question.getTitle(), question.getAnswer()));
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

    @RequestMapping(value = "/teacher/course/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> addCourse(@RequestBody Course course) {
        log.info(String.format("教师 -%s-  发布课程考试 -%s- ", course.gettID(), course.getCourseID()));
        return ResponseEntity.ok().body(teacherService.addCourse(course));
    }

    @RequestMapping(value = "/teacher/course/delete", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> deleteCourse(@RequestBody Course course) {
        log.info(String.format("删除课程考试 -%s- ", course.getCourseID()));
        return ResponseEntity.ok().body(teacherService.deleteCourse(course.getCourseID()));
    }

    @RequestMapping(value = "/teacher/course/update", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> updateCourse(@RequestBody Course course){
        log.info(String.format("更新课程考试： -%s- ", course.getCourseID()));
        return ResponseEntity.ok().body(teacherService.updateCourse(course));
    }

    @RequestMapping(value = "/teacher/exam/assign", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> assignExam(@RequestBody AssignMajorExamRequest request) {
        log.info(String.format("教师 -%s- 指定 专业-%s- 进行考试 -%s- ", request.gettID(), request.getExamID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.assignExam(request));
    }

    @RequestMapping(value = "/teacher/exam/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherExamList(@RequestBody Teacher teacher){
        log.info(String.format("教师 -%s- 查询考试列表", teacher.gettID()));
        return ResponseEntity.ok().body(teacherService.getTeacherExamList(teacher.gettID()));
    }

    @RequestMapping(value = "/teacher/exam/info", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getTeacherExamInfo(@RequestBody TeacherExamInfoRequest request){
        log.info(String.format("教师 -%s- 查询考试 -%s- 详情", request.gettID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.getTeacherExamInfo(request));
    }

    @RequestMapping(value = "/teacher/exam/student/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentExamList(@RequestBody TeacherExamInfoRequest request){
        log.info(String.format("教师 -%s- 查询考试 -%s- 的待评卷列表", request.gettID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.getStudentExamList(request));
    }

    @RequestMapping(value = "/teacher/exam/student/mark/one", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentExamNeedMark(@RequestBody StudentExamNeedMarkRequest request){
        log.info(String.format("教师 -%s- 查询考试 -%s- 学生 -%s- 的试卷", request.gettID(), request.getExamID(), request.getsID()));
        return ResponseEntity.ok().body(teacherService.getStudentExamNeedMark(request));
    }

    @RequestMapping(value = "/teacher/exam/student/mark/submit", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> submitMarkedExam(@RequestBody MarkedExamRequest request){
        log.info(String.format("教师 -%s-   批改学生 -%s- 科目 -%s- 的试卷", request.gettID(), request.getsID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.submitMarkedExam(request));
    }

    @RequestMapping(value = "/teacher/exam/student/mark/next", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getNextStudentExamNeedMark(@RequestBody NextStudentExamNeedMarkRequest request){
        log.info(String.format("教师 -%s- 查询考试 -%s- 下一个学生的试卷", request.gettID(), request.getExamID()));
        return ResponseEntity.ok().body(teacherService.getNextStudentExamNeedMark(request));
    }
}
