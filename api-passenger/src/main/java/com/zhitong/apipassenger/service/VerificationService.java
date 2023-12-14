package com.zhitong.apipassenger.service;

import com.zhitong.apipassenger.serviceclient.VerificationCodeClient;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.DigitalCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    @Autowired
    private VerificationCodeClient verificationCodeClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final String verificationCodeKeyPrefix = "verification-code:";

    public ResponseResult generateVerificationCode(String phoneNumber){
        // Get the code from the verification service API
        /*System.out.println("Call verification service to get code.");
        String verificaitonCode = "12345678";*/
        ResponseResult<DigitalCodeResponse> digitalCodeResponseResponseResult = verificationCodeClient.getDigitalCode(6);
        int digitalCode = digitalCodeResponseResponseResult.getPayload().getDigitalCode();
        System.out.println("digitalCode = " + digitalCode);

        // Save the code to redis with ttl.
        //System.out.println("Save verification code into Redis.");
        // Key --> Value
        String verficationCodeKey = verificationCodeKeyPrefix + phoneNumber;
        stringRedisTemplate.opsForValue().set(verficationCodeKey, Integer.toString(digitalCode), 2, TimeUnit.MINUTES);

        // Return result
        /*JSONObject resultJson = new JSONObject();
        resultJson.put("code", 201);
        resultJson.put("message", "success");*/

        return ResponseResult.success();

    }
}
