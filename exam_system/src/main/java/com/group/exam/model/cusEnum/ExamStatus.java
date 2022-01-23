package com.group.exam.model.cusEnum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum  ExamStatus {
    UNFINISHED(0,"未完成"),
    FINISHED(1,"已完成，未评分"),
    MARKED(2,"已评分"),
    CANCELLED(3,"已被取消"),
    MARKING(4,"正在评分"),
    DELETED(5,"已过期");

    private final int value;
    private final String msg;
    private static final Map<Integer, ExamStatus> lookup = new HashMap<>();

    ExamStatus(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public static ExamStatus get(int value) {
        return lookup.get(value);
    }

    static {
        for (ExamStatus x : EnumSet.allOf(ExamStatus.class)) {
            lookup.put(x.value, x);
        }

    }
}
