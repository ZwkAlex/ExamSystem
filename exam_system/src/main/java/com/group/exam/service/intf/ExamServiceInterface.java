package com.group.exam.service.intf;


import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.StudentExam;
import com.group.exam.model.requestModel.StartExamRequest;
import com.group.exam.model.requestModel.StopExamRequest;
import com.group.exam.model.responseModel.ResponseModel;


public interface ExamServiceInterface {
    ResponseModel getStudentExamList(Student student);
    ResponseModel getExamInfo(StudentExam studentExam);
    ResponseModel getQuestionList(String examID);
    ResponseModel getScoreList(Student student);
    ResponseModel startExam(StartExamRequest startExamRequest);
    ResponseModel stopExam(StopExamRequest stopExamRequest);
}
