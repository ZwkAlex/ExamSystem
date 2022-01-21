package com.group.exam.model.responseModel;

public class TimeCheckResponse {
    private boolean expired;
    private int duration;

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
