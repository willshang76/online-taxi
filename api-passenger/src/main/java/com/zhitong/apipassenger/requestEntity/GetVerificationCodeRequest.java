package com.zhitong.apipassenger.requestEntity;

import lombok.Data;

@Data
public class GetVerificationCodeRequest {
    private String phoneNumber;
}
