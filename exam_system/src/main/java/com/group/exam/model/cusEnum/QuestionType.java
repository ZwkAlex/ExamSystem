package com.group.exam.model.cusEnum;


import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum QuestionType {
    SINGLE(1,"单选题"),
    MULTI(2,"多选题"),
    JUDGE(3,"判断题"),
    BLANK(4,"填空题"),
    TEXT(5,"主观题");

    private final int value;
    private final String msg;
    private static final Map<Integer, QuestionType> lookup = new HashMap<>();

    QuestionType(int value, String msg) {
        this.value = value;
        this.msg = msg;
    }

    public int getValue() {
        return value;
    }

    public String getMsg() {
        return msg;
    }

    public static QuestionType get(int value) {
        return lookup.get(value);
    }

    static {
        for (QuestionType x : EnumSet.allOf(QuestionType.class)) {
            lookup.put(x.value, x);
        }

    }

}
