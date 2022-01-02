package com.group.exam.model.requestModel;

import com.group.exam.model.entity.Answer;
import com.group.exam.model.entity.StudentExam;

import java.util.List;

public class StopExamRequest{
    private String sID;
    private String examID;
    private List<Answer> answers;

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
