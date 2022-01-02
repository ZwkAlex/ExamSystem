package com.group.exam.model.entity;


import com.group.exam.model.daoModel.QuestionDao;
import com.group.exam.model.requestModel.QuestionRequest;
import com.group.exam.util.ExamUtil;
import com.group.exam.model.cusEnum.QuestionType;

import java.util.List;

public class Question {
    private String questionID;
    private String examID;
    private QuestionType type;
    private String title;
    private List<String> options;
    private double score;

    public Question(){}

    public Question(QuestionDao q) {
        this.questionID = q.getQuestionID();
        this.examID = q.getExamID();
        this.type = QuestionType.get(q.getType());
        this.title = q.getTitle();
        this.options = ExamUtil.String2StringList(q.getOptions());
        this.score = q.getScore();
    }

    public Question(QuestionRequest questionRequest){
        examID = questionRequest.getExamID();
        questionID = questionRequest.getQuestionID();
        type = QuestionType.valueOf(questionRequest.getType());
        title = questionRequest.getTitle();
        options = questionRequest.getOptions();
        score =questionRequest.getScore();
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionID='" + questionID + '\'' +
                ", examID='" + examID + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", options=" + options +
                ", score=" + score +
                '}';
    }


    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
