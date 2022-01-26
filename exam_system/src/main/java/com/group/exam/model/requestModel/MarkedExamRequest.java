package com.group.exam.model.requestModel;

import java.util.List;

public class MarkedExamRequest {
    private String sID;
    private String examID;
    private List<MarkedScoreModel> scores;

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

    public List<MarkedScoreModel> getScores() {
        return scores;
    }

    public void setScores(List<MarkedScoreModel> scores) {
        this.scores = scores;
    }
}
