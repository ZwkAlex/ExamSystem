package com.group.exam.model.entity;

import com.group.exam.model.daoModel.AnswerDao;
import com.group.exam.model.requestModel.QuestionRequest;
import com.group.exam.util.ExamUtil;

import java.util.List;

public class Answer {
    private String examID;
    private String questionID;
    private List<String> answer;

    public Answer(){}

    public Answer(AnswerDao a){
        examID = a.getExamID();
        questionID = a.getQuestionID();
        answer = ExamUtil.String2StringList(a.getAnswer());
    }

    public Answer(QuestionRequest questionRequest){
        examID = questionRequest.getExamID();
        questionID = questionRequest.getQuestionID();
        answer = questionRequest.getAnswers();
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

    public List<String> getAnswer() {
        return answer;
    }

    public void setAnswer(List<String> answer) {
        this.answer = answer;
    }
}
