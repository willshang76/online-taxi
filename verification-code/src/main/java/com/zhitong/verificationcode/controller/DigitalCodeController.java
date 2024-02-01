package com.zhitong.verificationcode.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.DigitalCodeResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DigitalCodeController {

    @GetMapping("/digitalCode")
    public String digitalCode(@RequestParam("size") int size){

        // Version 1: Skeleton code.
        System.out.println("size = " + size);

        JSONObject result = new JSONObject();
        result.put("code", "201");
        result.put("message", "success");
        JSONObject payload = new JSONObject();
        payload.put("digitalCode", generateCode(size));
        result.put("payload", payload);

        return result.toString();
    }

    @GetMapping("/digitalCode2")
    public ResponseResult<DigitalCodeResponse> digitalCode2(@RequestParam("size") int size){

        DigitalCodeResponse digitalCodeResponse = new DigitalCodeResponse();
        digitalCodeResponse.setDigitalCode(generateCode(size));

        return ResponseResult.success(digitalCodeResponse);
    }
    // 0.23 X 100 ==> 23 random 0 - 1 ==> 0.002342 ===> 2342
    // 0 - 9 ==> 0.0034214, 1.0002131, 2.0901231 ==> 8 --> + 1
    // ==> 1 - 9 ===> 9
    private int generateCode(int size) {
        return (int) ((Math.random()*8 + 1) * Math.pow(10, size - 1));
    }
}
