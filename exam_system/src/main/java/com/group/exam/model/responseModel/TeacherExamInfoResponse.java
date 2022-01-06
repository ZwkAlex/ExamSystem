package com.group.exam.model.responseModel;

public class TeacherExamInfoResponse {
    private String courseName;
    private String examID;
    private String startDate;
    private String endDate;
    private String duration;
    private String totalScore;
    private int unFinished;
    private int totalFinished;
    private int finished;
    private int marked;
    private int totalNumber;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public int getUnFinished() {
        return unFinished;
    }

    public void setUnFinished(int unFinished) {
        this.unFinished = unFinished;
    }

    public int getTotalFinished() {
        return totalFinished;
    }

    public void setTotalFinished(int totalFinished) {
        this.totalFinished = totalFinished;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public int getMarked() {
        return marked;
    }

    public void setMarked(int marked) {
        this.marked = marked;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
}
