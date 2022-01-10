package com.group.exam.controller;



import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.StudentExam;
import com.group.exam.model.requestModel.StartExamRequest;
import com.group.exam.model.requestModel.StopExamRequest;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.ExamService;
import com.group.exam.model.cusEnum.ResponseCode;
import com.group.exam.service.impl.StudentService;
import com.group.exam.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 *  date:2021/12/30
 *  author:郑梧堃
 */
@RestController
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Resource
    private StudentService studentService;
    @Resource
    private ExamService examService;

    @RequestMapping(value = "/student/info", method= RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentInfo(@RequestBody Student student) {
        log.info(String.format("查询学生 -%s- 的个人信息。", student.getsID()));
        ResponseModel info = studentService.getStudentInfo(student.getsID());
        return ResponseEntity.ok().body(info);
    }

    @RequestMapping(value = "/student/exam/list", method= RequestMethod.POST)
    public ResponseEntity<ResponseModel> getExamList(@RequestBody Student student){
        log.info(String.format(" -%s- 查询考试列表信息。", student.getsID()));
        ResponseModel exam_list = examService.getStudentExamList(student);
        return ResponseEntity.ok().body(exam_list);
    }

    @RequestMapping(value = "/student/exam/info", method= RequestMethod.POST)
    public ResponseEntity<ResponseModel> getExamList(@RequestBody StudentExam studentTest){
        log.info(String.format(" -%s- 查看考试ID -%s- 信息。", studentTest.getsID(), studentTest.getExamID()));
        ResponseModel exam = examService.getExamInfo(studentTest);
        return ResponseEntity.ok().body(exam);
    }

    @RequestMapping(value = "/student/exam/start", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> startExam(@RequestBody StartExamRequest startExamRequest){
        log.info(String.format(" -%s- 开始考试 -%s- ", startExamRequest.getsID(), startExamRequest.getExamID()));
        ResponseModel questions = examService.getQuestionList(startExamRequest.getExamID());
        ResponseModel startExamResponse = examService.startExam(startExamRequest);
        if(startExamResponse.getStatus() != ResponseCode.SUCCESS.getCode())
            return ResponseEntity.ok().body(startExamResponse);
        return ResponseEntity.ok().body(questions);
    }

    @RequestMapping(value = "/student/exam/end", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> endExam(@RequestBody StopExamRequest stopExamRequest){
        log.info(String.format(" -%s- 结束考试 -%s- ", stopExamRequest.getsID(), stopExamRequest.getExamID()));
        ResponseModel stopExamResponse = examService.stopExam(stopExamRequest);
        if(ResponseCode.get(stopExamResponse.getStatus()) != ResponseCode.SUCCESS)
            return ResponseEntity.ok().body(stopExamResponse);
        return ResponseEntity.ok().body(ResponseUtil.success());
    }

    @RequestMapping(value = "/student/exam/score", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getScore(@RequestBody Student student){
        log.info(String.format("查看 -%s- 的成绩", student.getsID()));
        return ResponseEntity.ok().body(examService.getScoreList(student));
    }

}
