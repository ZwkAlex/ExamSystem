package com.group.exam.model.requestModel;

public class StudentExamListRequest {
    private String tID;
    private String examID;

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
}
