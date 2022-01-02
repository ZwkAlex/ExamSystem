package com.group.exam.model.entity;

public class StudentExam {
    private String sID;
    private String examID;
    private int status;
    private double score;

    @Override
    public String toString() {
        return "StudentExam{" +
                "sID='" + sID + '\'' +
                ", examID='" + examID + '\'' +
                ", status=" + status +
                ", score=" + score +
                '}';
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
