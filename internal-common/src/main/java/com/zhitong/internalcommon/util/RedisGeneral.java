package com.zhitong.internalcommon.util;

public class RedisGeneral {
    private final static String verificationCodeKeyPrefix = "verification-code:";

    private final static String tokenKeyPrefix = "token:";

    public static String generateTokenKey(String phoneNumber, String userType, String tokenType) {
        return tokenKeyPrefix + phoneNumber + "-" + userType + "-" + tokenType;
    }

    public static String generateVerificationCodeKey(String phoneNumber) {
        return verificationCodeKeyPrefix + phoneNumber;
    }


}
