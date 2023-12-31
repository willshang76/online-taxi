package com.zhitong.internalcommon.constant;

public class JwtInfo {
    public static final String PHONE_KEY = "phone_number";
    public static final String USER_TYPE_KEY = "user_type";
    public static final String PASSENGER_TYPE = "1";
    public static final String DRIVER_TYPE = "2";
    public static final String TOKEN_TYPE = "token_type";

    public enum Token {
        ACCESS_TOKEN,
        REFRESH_TOKEN
    }
}
