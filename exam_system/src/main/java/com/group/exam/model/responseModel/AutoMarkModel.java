package com.group.exam.model.responseModel;

public class AutoMarkModel {
    private boolean canAutoMark;
    private boolean result;
    private double score;

    public boolean isCanAutoMark() {
        return canAutoMark;
    }

    public void setCanAutoMark(boolean canAutoMark) {
        this.canAutoMark = canAutoMark;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
