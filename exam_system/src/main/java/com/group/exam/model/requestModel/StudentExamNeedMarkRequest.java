package com.group.exam.model.requestModel;

public class StudentExamNeedMarkRequest extends NextStudentExamNeedMarkRequest {
    private String sID;

    public StudentExamNeedMarkRequest(){}


    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }
}
