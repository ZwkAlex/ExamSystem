package com.group.exam.model.entity;

public class Student{
    private String sName;
    private String sID;
    private String college;
    private String sMajorID;

    @Override
    public String toString() {
        return "Student{" +
                "sName='" + sName + '\'' +
                ", sID='" + sID + '\'' +
                ", college='" + college + '\'' +
                ", sMajorID='" + sMajorID + '\'' +
                '}';
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
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
