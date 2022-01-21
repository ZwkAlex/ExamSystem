package com.group.exam.model.entity;

import com.group.exam.model.requestModel.AlterExamRequest;
import com.group.exam.util.ExamUtil;

import java.sql.Timestamp;

public class Exam {
    private String examID;
    private String courseID;
    private Timestamp startDate;
    private Timestamp endDate;
    private int duration;
    private double totalScore;

    public Exam(){}

    public Exam(AlterExamRequest request){
        examID = request.getExamID();
        courseID = request.getCourseID();
        startDate = ExamUtil.String2Timestamp(request.getStartDate());
        endDate = ExamUtil.String2Timestamp(request.getEndDate());
        duration = ExamUtil.String2Sec(request.getDuration());
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
