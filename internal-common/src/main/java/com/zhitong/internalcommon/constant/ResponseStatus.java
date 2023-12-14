package com.zhitong.internalcommon.constant;

import lombok.Getter;

public enum ResponseStatus {
    SUCCESS(201, "success"),
    FAIL(0, "fail")

    ;

    @Getter
    private int code;
    @Getter
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
