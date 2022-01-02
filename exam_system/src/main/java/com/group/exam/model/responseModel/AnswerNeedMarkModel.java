package com.group.exam.model.responseModel;

import java.util.List;

public class AnswerNeedMarkModel {
    private String title;
    private int type;
    private String typeString;
    private String studentAnswer;
    private String trueAnswer;
    private AutoMarkModel autoMark;
    private double score;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTrueAnswer() {
        return trueAnswer;
    }

    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    public AutoMarkModel isAutoMark() {
        return autoMark;
    }

    public void setAutoMark(AutoMarkModel autoMark) {
        this.autoMark = autoMark;
    }

    public String getTypeString() {
        return typeString;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
