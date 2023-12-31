package com.zhitong.internalcommon.response;

import lombok.Data;

@Data
public class VerifiedTokenResponse {
    // private String token;
    private String accessToken;
    private String refreshToken;
}
