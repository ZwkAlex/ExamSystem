package com.group.exam.model.responseModel;

import java.util.List;

public class StudentExamNeedMarkResponse {
    private String sID;
    private String sName;
    private String examID;
    private List<AnswerNeedMarkModel> answers;

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

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public List<AnswerNeedMarkModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerNeedMarkModel> answers) {
        this.answers = answers;
    }
}
