package com.group.exam.model.responseModel;


import com.group.exam.model.entity.Student;
import com.group.exam.model.entity.User;

public class StudentListResponse {
    private String sID;
    private String sName;
    private String college;
    private String sMajorID;
    private String password;

    public StudentListResponse(Student student, User user) {
        sID = student.getsID();
        sName = student.getsName();
        college = student.getCollege();
        sMajorID = student.getsMajorID();
        password = user.getPassword();
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
