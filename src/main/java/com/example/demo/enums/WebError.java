package com.example.demo.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum WebError {

    NO_STATUS(-1, "無狀態"),
    CURRENCY_ID_NOT_FOUND(1000, "幣別 ID 不存在"),
    CURRENCY_CODE_AlREADY_EXISTS(1001, "幣別代碼 已存在");

    private final Integer code;
    private final String message;

    WebError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private static final Map<Integer, WebError> lookup = new HashMap<>();

    static {
        for (WebError suit : EnumSet.allOf(WebError.class)) {
            lookup.put(suit.ordinal(), suit);
        }
    }

    public static WebError fromOrdinal(Integer ordinal) {
        return (ordinal == null) ? NO_STATUS : lookup.get(ordinal);
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
