package com.group.exam.model.daoModel;

import com.group.exam.model.entity.Question;
import com.group.exam.util.ExamUtil;

public class QuestionDao {
    private String questionID;
    private String examID;
    private int type;
    private String title;
    private String options;
    private double score;

    public QuestionDao(){}

    public QuestionDao(Question q) {
        this.questionID = q.getQuestionID();
        this.examID = q.getExamID();
        this.type = q.getType().getValue();
        this.title = q.getTitle();
        this.options = ExamUtil.StringList2String(q.getOptions());
        this.score = q.getScore();
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
