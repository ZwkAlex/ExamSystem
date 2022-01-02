package com.group.exam.model.requestModel;

import java.util.List;

public class MarkedExamRequest {
    private String tID;
    private String sID;
    private String examID;
    private List<MarkedAnswerModel> markedAnswers;

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
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

    public List<MarkedAnswerModel> getMarkedAnswers() {
        return markedAnswers;
    }

    public void setMarkedAnswers(List<MarkedAnswerModel> markedAnswers) {
        this.markedAnswers = markedAnswers;
    }
}
