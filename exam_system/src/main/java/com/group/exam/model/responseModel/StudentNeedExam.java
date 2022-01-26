package com.group.exam.model.responseModel;

import com.group.exam.model.cusEnum.ExamStatus;
import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.StudentExam;
import com.group.exam.util.ExamUtil;

public class StudentNeedExam{
    private String sID;
    private String sName;
    private String college;
    private String mName;
    private String sMajorID;
    private String status;
    private String score;

    public StudentNeedExam(){}

    public void set(StudentExam studentExam){
        this.sID = studentExam.getsID();
        this.status = ExamStatus.get(studentExam.getStatus()).getMsg();
        this.score = ExamUtil.Score2String(studentExam.getScore(), studentExam.getStatus());
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
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

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
