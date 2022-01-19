package com.group.exam.model.responseModel;

import com.group.exam.model.entity.Course;
import com.group.exam.model.entity.Exam;
import com.group.exam.model.entity.Teacher;

public class StudentExamInfoResponse extends StudentExamResponse {
    private String tName;
    private String totalScore;
    private String numberOfQuestions;
    private boolean isValid;
    private String status;

    public StudentExamInfoResponse(Course course, Exam exam, Teacher teacher) {
        super(course,exam);
        tName = teacher.gettName();
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(String numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getStatus() {
        return status;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
