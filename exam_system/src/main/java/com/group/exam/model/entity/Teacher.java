package com.group.exam.model.entity;

public class Teacher{
    private String tName;
    private String tID;

    @Override
    public String toString() {
        return "Teacher{" +
                "tName='" + tName + '\'' +
                ", tID='" + tID + '\'' +
                '}';
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettID() {
        return tID;
    }

    public void settID(String tID) {
        this.tID = tID;
    }
}
