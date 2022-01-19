package com.group.exam.service.intf;


import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.StudentExam;
import com.group.exam.model.requestModel.StartExamRequest;
import com.group.exam.model.requestModel.StopExamRequest;
import com.group.exam.model.requestModel.StudentExamInfoRequest;
import com.group.exam.model.requestModel.TimeCheckRequest;
import com.group.exam.model.responseModel.ResponseModel;


public interface ExamServiceInterface {
    ResponseModel getStudentExamList(String sID);
    ResponseModel getExamInfo(String id ,StudentExamInfoRequest request);
    ResponseModel getQuestionList(String examID);
    ResponseModel getScoreList(String id);
    ResponseModel startExam(String id, StartExamRequest request);
    ResponseModel stopExam(String id, StopExamRequest request);
    ResponseModel checkTime(String id, TimeCheckRequest request);
}
