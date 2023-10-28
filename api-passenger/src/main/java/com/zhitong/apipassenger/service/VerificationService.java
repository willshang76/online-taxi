package com.zhitong.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {
    public String generateVerificationCode(String phoneNumber){
        // Get the code from the verification service API
        System.out.println("Call verification service to get code.");
        String verificaitonCode = "12345678";

        // Save the code to redis with ttl.
        System.out.println("Save verification code into Redis.");

        // Return result
        JSONObject resultJson = new JSONObject();
        resultJson.put("code", 201);
        resultJson.put("message", "success");

        return resultJson.toString();

    }
}
