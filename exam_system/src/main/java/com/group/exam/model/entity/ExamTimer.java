package com.group.exam.model.entity;

import java.sql.Timestamp;

public class ExamTimer {
    private String sID;
    private String examID;
    private Timestamp endTime;

    @Override
    public String toString() {
        return "ExamTimer{" +
                "sID='" + sID + '\'' +
                ", examID='" + examID + '\'' +
                ", endTime=" + endTime +
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

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
