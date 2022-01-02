package com.group.exam.model.responseModel;

import java.util.List;

public class TeacherExamListResponse {
    private String courseID;
    private String courseName;
    private List<TeacherExamResponse> examList;

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<TeacherExamResponse> getExamList() {
        return examList;
    }

    public void setExamList(List<TeacherExamResponse> examList) {
        this.examList = examList;
    }
}
