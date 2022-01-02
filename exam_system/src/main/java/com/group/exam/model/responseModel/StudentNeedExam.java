package com.group.exam.model.responseModel;

import com.group.exam.model.entity.StudentExam;

public class StudentNeedExam extends StudentExam {
    private String sName;
    private String college;
    private String sMajorID;

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getsMajorID() {
        return sMajorID;
    }

    public void setsMajorID(String sMajorID) {
        this.sMajorID = sMajorID;
    }
}
