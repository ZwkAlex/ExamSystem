package com.group.exam.model.requestModel;


public class AssignMajorExamRequest  {
    private String tID;
    private String examID;
    private String smajorID;

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getSmajorID() {
        return smajorID;
    }

    public void setSmajorID(String smajorID) {
        this.smajorID = smajorID;
    }
}