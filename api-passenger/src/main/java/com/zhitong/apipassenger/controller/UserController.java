package com.zhitong.apipassenger.controller;

import com.zhitong.apipassenger.service.UserService;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseResult getUser(HttpServletRequest request) {
        return userService.getUser(request.getHeader("Authorization"));
    }
}
