package com.group.exam.model.requestModel;

public class MarkedAnswerModel {
    private String questionID;
    private double score;

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
