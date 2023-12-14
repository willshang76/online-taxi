package com.zhitong.apipassenger.controller;

import com.zhitong.apipassenger.requestEntity.GetVerificationCodeRequest;
import com.zhitong.apipassenger.service.VerificationService;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationCodeController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping("/verification-code")
    public ResponseResult verificationCode(@RequestBody GetVerificationCodeRequest requestBody){
        String phoneNumber = requestBody.getPhoneNumber();
        System.out.println("phoneNumber = " + phoneNumber);
        return verificationService.generateVerificationCode(phoneNumber);
    }
}
