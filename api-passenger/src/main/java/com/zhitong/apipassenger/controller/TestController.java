package com.zhitong.apipassenger.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test passenger api";
    }

    @GetMapping("/auth")
    public ResponseResult authTest() {
        return ResponseResult.success("token verified successfully.");
    }

    @GetMapping("/noauth")
    public ResponseResult noauthTest() {
        return ResponseResult.success("Auth not required");
    }
}
