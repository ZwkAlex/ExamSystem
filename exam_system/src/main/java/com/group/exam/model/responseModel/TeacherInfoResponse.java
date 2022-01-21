package com.group.exam.model.responseModel;

import com.group.exam.model.entity.Teacher;

public class TeacherInfoResponse {
    private String id;
    private String name;
    private String college;

    public TeacherInfoResponse(Teacher teacher){
        this.id = teacher.gettID();
        this.name = teacher.gettName();
        this.college = teacher.getCollege();
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
}
