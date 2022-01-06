package com.group.exam.model.responseModel;

import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.StudentExam;

public class StudentNeedExam{
    private String sID;
    private String sName;
    private String college;
    private String sMajorID;
    private int status;
    private double score;

    public StudentNeedExam(){}

    public void set(StudentExam studentExam){
        this.sID = studentExam.getsID();
        this.status = studentExam.getStatus();
        this.score = studentExam.getScore();
    }

    public void set(Student student){
        this.sName = student.getsName();
        this.college = student.getCollege();
        this.sMajorID = student.getsMajorID();
    }


    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
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
