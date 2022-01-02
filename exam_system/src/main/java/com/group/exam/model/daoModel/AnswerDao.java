package com.group.exam.model.daoModel;

import com.group.exam.model.entity.Answer;
import com.group.exam.util.ExamUtil;

import java.util.List;

public class AnswerDao {
    private String examID;
    private String questionID;
    private String answer;

    public AnswerDao(){}

    public AnswerDao(Answer a){
        examID = a.getExamID();
        questionID = a.getQuestionID();
        answer = ExamUtil.StringList2String(a.getAnswer());
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
