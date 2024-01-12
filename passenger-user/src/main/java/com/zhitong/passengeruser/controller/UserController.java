package com.zhitong.passengeruser.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.LoginRequest;
import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.passengeruser.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseResult<Void> loginAndCreateUserWhenNeed(@RequestBody LoginRequest loginRequest) {
        return userService.loginAndCreateUserWhenNeed(loginRequest.getPhoneNumber());
    }

    @GetMapping("/user/{phoneNumber}")
    public ResponseResult<GetUserResponse> getUser(@PathVariable String phoneNumber) {
        return userService.getUser(phoneNumber);
    }
}
