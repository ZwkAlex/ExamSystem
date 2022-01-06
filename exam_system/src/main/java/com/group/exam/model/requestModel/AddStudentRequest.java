package com.group.exam.model.requestModel;

import com.group.exam.model.entity.Student;

public class AddStudentRequest extends Student {
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
