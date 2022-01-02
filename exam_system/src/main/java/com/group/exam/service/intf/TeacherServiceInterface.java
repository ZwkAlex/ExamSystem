package com.group.exam.service.intf;

import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.Course;
import com.group.exam.model.requestModel.*;
import com.group.exam.model.responseModel.ResponseModel;

public interface TeacherServiceInterface {
    ResponseModel addStudent(Student student);
    ResponseModel deleteStudent(String sid);
    ResponseModel updateStudent(Student student);
    ResponseModel addQuestion(QuestionRequest questionRequest);
    ResponseModel deleteQuestion(String questionID);
    ResponseModel updateQuestion(QuestionRequest questionRequest);
    ResponseModel addCourse(Course course);
    ResponseModel deleteCourse(String courseID);
    ResponseModel updateCourse(Course course);
    ResponseModel assignExam(AssignMajorExamRequest assignMajorExamRequest);
    ResponseModel getTeacherExamList(String tID);
    ResponseModel getStudentExamList(StudentExamListRequest studentExamListRequest);
    ResponseModel getStudentExamNeedMark(StudentExamNeedMarkRequest studentExamNeedMarkRequest);
    ResponseModel getNextStudentExamNeedMark(NextStudentExamNeedMarkRequest nextStudentExamNeedMarkRequest);
    ResponseModel submitMarkedExam(MarkedExamRequest markedExamRequest);
}
