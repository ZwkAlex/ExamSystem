package com.group.exam.model.responseModel;

import java.util.List;

public class ExamLiteListResponse {
    private String courseID;
    private String courseName;
    private List<ExamLiteModel> examList;

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

    public List<ExamLiteModel> getExamList() {
        return examList;
    }

    public void setExamList(List<ExamLiteModel> examList) {
        this.examList = examList;
    }
}
