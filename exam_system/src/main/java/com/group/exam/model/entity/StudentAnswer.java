package com.group.exam.model.entity;

import com.group.exam.model.daoModel.StudentAnswerDao;
import com.group.exam.util.ExamUtil;

import java.util.List;

public class StudentAnswer {
    private String sID;
    private String examID;
    private String questionID;
    private List<String> answer;
    private boolean isMarked;
    private double score;

    public StudentAnswer(){}

    public StudentAnswer(StudentAnswerDao s_a) {
        this.sID = s_a.getsID();
        this.examID = s_a.getExamID();
        this.questionID = s_a.getQuestionID();
        this.answer = ExamUtil.String2StringList(s_a.getAnswer());
        this.isMarked = s_a.isMarked();
        this.score = s_a.getScore();
    }

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

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
