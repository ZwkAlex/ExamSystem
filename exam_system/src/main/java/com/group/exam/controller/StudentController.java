package com.group.exam.controller;



import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.StudentExam;
import com.group.exam.model.requestModel.StartExamRequest;
import com.group.exam.model.requestModel.StopExamRequest;
import com.group.exam.model.requestModel.StudentExamInfoRequest;
import com.group.exam.model.requestModel.TimeCheckRequest;
import com.group.exam.model.responseModel.ResponseModel;
import com.group.exam.service.impl.ExamService;
import com.group.exam.model.cusEnum.ResponseCode;
import com.group.exam.service.impl.StudentService;
import com.group.exam.util.AuthUtil;
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
    @Resource
    private AuthUtil authUtil;

    @RequestMapping(value = "/student/info", method= RequestMethod.POST)
    public ResponseEntity<ResponseModel> getStudentInfo() {
        try {
            String id = authUtil.getUserID();
            log.info(String.format("查询学生 -%s- 的个人信息。", id));
            ResponseModel info = studentService.getStudentInfo(id);
            return ResponseEntity.ok().body(info);
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }

    }

    @RequestMapping(value = "/student/exam/list", method= RequestMethod.POST)
    public ResponseEntity<ResponseModel> getExamList(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format(" -%s- 查询考试列表信息。", id));
            ResponseModel exam_list = examService.getStudentExamList(id);
            return ResponseEntity.ok().body(exam_list);
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/student/exam/info", method= RequestMethod.POST)
    public ResponseEntity<ResponseModel> getExamList(@RequestBody StudentExamInfoRequest request){
        try{
            String id = authUtil.getUserID();
            log.info(String.format(" -%s- 查看考试ID -%s- 信息。", id, request.getExamID()));
            ResponseModel exam = examService.getExamInfo(id, request);
            return ResponseEntity.ok().body(exam);
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/student/exam/start", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> startExam(@RequestBody StartExamRequest request){
        try{
            String id = authUtil.getUserID();
            log.info(String.format(" -%s- 开始考试 -%s- ", id, request.getExamID()));
            ResponseModel questions = examService.getQuestionList(request.getExamID());
            ResponseModel startExamResponse = examService.startExam(id,request);
            if(startExamResponse.getStatus() != ResponseCode.SUCCESS.getCode())
                return ResponseEntity.ok().body(startExamResponse);
            return ResponseEntity.ok().body(questions);
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/student/exam/check/time", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> checkTime(@RequestBody TimeCheckRequest request){
        try {
            String id = authUtil.getUserID();
            return ResponseEntity.ok().body(examService.checkTime(id,request));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/student/exam/end", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> endExam(@RequestBody StopExamRequest stopExamRequest){
        try {
            String id = authUtil.getUserID();
            log.info(String.format(" -%s- 结束考试 -%s- ", id, stopExamRequest.getExamID()));
            ResponseModel stopExamResponse = examService.stopExam(id, stopExamRequest);
            if (ResponseCode.get(stopExamResponse.getStatus()) != ResponseCode.SUCCESS)
                return ResponseEntity.ok().body(stopExamResponse);
            return ResponseEntity.ok().body(ResponseUtil.success());
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

    @RequestMapping(value = "/student/exam/score", method = RequestMethod.POST)
    public ResponseEntity<ResponseModel> getScore(){
        try {
            String id = authUtil.getUserID();
            log.info(String.format("查看 -%s- 的成绩", id));
            return ResponseEntity.ok().body(examService.getScoreList(id));
        }catch (Exception e){
            return ResponseEntity.ok().body(ResponseUtil.error(e.getMessage()));
        }
    }

}
