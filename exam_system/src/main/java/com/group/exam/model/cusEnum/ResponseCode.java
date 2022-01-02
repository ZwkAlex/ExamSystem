package com.group.exam.model.cusEnum;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
    UNKNOWN_ERROR(-1,"服务端未知错误,请稍后重试。"),
    SUCCESS(1,"成功"),
    DATA_IS_EMPTY(0,"空信息"),
    ;
    private Integer code;
    private String msg;
    private static final Map<Integer, ResponseCode> lookup = new HashMap<>();

    ResponseCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ResponseCode get(int value) {
        return lookup.get(value);
    }

    static {
        for (ResponseCode x : EnumSet.allOf(ResponseCode.class)) {
            lookup.put(x.code, x);
        }

    }
}