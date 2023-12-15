package com.zhitong.internalcommon.constant;

import lombok.Getter;

public enum ResponseStatus {
    /**
     * 1000 - 1099 verification related codes.
     */
    VERIFICATION_ERROR(1099, "Verification code error."),
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
