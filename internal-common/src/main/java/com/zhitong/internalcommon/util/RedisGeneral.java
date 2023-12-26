package com.zhitong.internalcommon.util;

public class RedisGeneral {
    private final static String verificationCodeKeyPrefix = "verification-code:";

    private final static String tokenKeyPrefix = "token:";

    public static String generateTokenKey(String phoneNumber, String userType) {
        return tokenKeyPrefix + phoneNumber + "-" + userType;
    }

    public static String generateVerificationCodeKey(String phoneNumber) {
        return verificationCodeKeyPrefix + phoneNumber;
    }


}
