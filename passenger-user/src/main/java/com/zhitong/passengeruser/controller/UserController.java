package com.zhitong.passengeruser.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.LoginRequest;
import com.zhitong.passengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public ResponseResult loginAndCreateUserWhenNeed(@RequestBody LoginRequest loginRequest){
        return userService.loginAndCreateUserWhenNeed(loginRequest.getPhoneNumber());
    }
}
