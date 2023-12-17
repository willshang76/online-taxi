package com.zhitong.passengeruser.service;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public ResponseResult loginAndCreateUserWhenNeed(String phoneNumber){
        System.out.println("phoneNumber = " + phoneNumber);
        // Search user based on the phone number in Mysql.

        // Check if the user exists.

        // Insert user if the user doesn't exist.


        return ResponseResult.success();
    }
}
