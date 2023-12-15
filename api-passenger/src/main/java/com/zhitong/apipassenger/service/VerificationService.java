package com.zhitong.apipassenger.service;

import com.google.common.base.Strings;
import com.zhitong.apipassenger.serviceclient.VerificationCodeClient;
import com.zhitong.internalcommon.constant.ResponseStatus;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.DigitalCodeResponse;
import com.zhitong.internalcommon.response.VerifiedTokenResponse;
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

    /**
     * Generate verifycation code
     * @param phoneNumber
     * @return ResponseResult
     */
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
        String verficationCodeKey = generateVerificationCodeKey(phoneNumber);
        stringRedisTemplate.opsForValue().set(verficationCodeKey, Integer.toString(digitalCode), 2, TimeUnit.MINUTES);

        // Return result
        /*JSONObject resultJson = new JSONObject();
        resultJson.put("code", 201);
        resultJson.put("message", "success");*/

        return ResponseResult.success();

    }

    /**
     * Verify Code
     * @param phoneNumber
     * @param code
     * @return ResponseResult
     */
    public ResponseResult  verifyCode(String phoneNumber, String code){

        // Generate the key of the verification code in the redis
        String verficationCodeKey = generateVerificationCodeKey(phoneNumber);

        // Retrieve the value from Redis
        String verficationCodeInRedis = stringRedisTemplate.opsForValue().get(verficationCodeKey);
        System.out.println("verficationCodeInRedis = " + verficationCodeInRedis);

        // Verify the Code
        if (Strings.isNullOrEmpty(verficationCodeInRedis)) {
            return ResponseResult.fail(ResponseStatus.VERIFICATION_ERROR.getCode(), ResponseStatus.VERIFICATION_ERROR.getMessage());
        }
        if (!code.trim().equals(verficationCodeInRedis.trim())) {
            return ResponseResult.fail(ResponseStatus.VERIFICATION_ERROR.getCode(), ResponseStatus.VERIFICATION_ERROR.getMessage());
        }

        // Check user information in Database

        // Generate token

        // return response
        VerifiedTokenResponse verifiedTokenResponse = new VerifiedTokenResponse();
        verifiedTokenResponse.setToken("Token String");
        return ResponseResult.success(verifiedTokenResponse);
    }

    private String generateVerificationCodeKey(String phoneNumber) {
        return verificationCodeKeyPrefix + phoneNumber;
    }

}
