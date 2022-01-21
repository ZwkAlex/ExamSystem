package com.group.exam.service.intf;

import com.group.exam.model.entity.Course;
import com.group.exam.model.requestModel.*;
import com.group.exam.model.responseModel.ResponseModel;

public interface TeacherServiceInterface {
    ResponseModel getTeacherInfo(String tID);
    ResponseModel getStudentList();
    ResponseModel addStudent(AlterStudentRequest request);
    ResponseModel deleteStudent(String sID);
    ResponseModel updateStudent(AlterStudentRequest request);
    ResponseModel addQuestion(QuestionRequest questionRequest);
    ResponseModel deleteQuestion(String questionID);
    ResponseModel updateQuestion(QuestionRequest questionRequest);
    ResponseModel getCourseList(String id);
    ResponseModel getCourseListLite(String id);
    ResponseModel addCourse(Course course);
    ResponseModel deleteCourse(String courseID);
    ResponseModel updateCourse(Course course);
    ResponseModel addExam(AlterExamRequest request);
    ResponseModel updateExam(AlterExamRequest request);
    ResponseModel deleteExam(AlterExamRequest request);
    ResponseModel assignExam(AssignMajorExamRequest assignMajorExamRequest);
    ResponseModel getTeacherExamList(String tID);
    ResponseModel getTeacherExamListLite(String courseID);
    ResponseModel getMajorList();
    ResponseModel getTeacherExamInfo(TeacherExamInfoRequest teacherExamInfoRequest);
    ResponseModel getStudentExamList(TeacherExamInfoRequest teacherExamInfoRequest);
    ResponseModel getStudentExamNeedMark(StudentExamNeedMarkRequest studentExamNeedMarkRequest);
    ResponseModel getNextStudentExamNeedMark(NextStudentExamNeedMarkRequest nextStudentExamNeedMarkRequest);
    ResponseModel submitMarkedExam(MarkedExamRequest markedExamRequest);
}
