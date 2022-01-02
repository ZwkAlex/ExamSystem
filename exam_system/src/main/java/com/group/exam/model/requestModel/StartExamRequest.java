package com.group.exam.model.requestModel;

public class StartExamRequest {
    private String sID;
    private String examID;

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
}
