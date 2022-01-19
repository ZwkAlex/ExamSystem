package com.group.exam.model.responseModel;

import com.group.exam.model.entity.Major;
import com.group.exam.model.entity.Student;

public class StudentInfoResponse {
    private String id;
    private String name;
    private String college;
    private String major;

    public StudentInfoResponse(Student student, Major major){
        this.id = student.getsID();
        this.name = student.getsName();
        this.college = student.getCollege();
        this.major = major.getmName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}
