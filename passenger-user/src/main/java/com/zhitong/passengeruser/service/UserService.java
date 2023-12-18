package com.zhitong.passengeruser.service;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.passengeruser.entity.User;
import com.zhitong.passengeruser.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public ResponseResult loginAndCreateUserWhenNeed(String phoneNumber){
        System.out.println("phoneNumber = " + phoneNumber);
        // Search user based on the phone number in Mysql.
        Map<String, Object> phoneMap = new HashMap();
        phoneMap.put("phone_number", phoneNumber);

        List<User> users = userMapper.selectByMap(phoneMap);

        System.out.println("users = " + users);

        users.forEach(user -> {
            System.out.println("user.getId() = " + user.getId());
            System.out.println("user.getPhone_number() = " + user.getPhoneNumber());
            System.out.println("user.getName() = " + user.getName());
            System.out.println("user.getGender() = " + user.getGender());
            System.out.println("user.getStatus() = " + user.getStatus());
            System.out.println("user.getCreatedAt() = " + user.getCreatedAt());
            System.out.println("user.getUpdatedAt() = " + user.getUpdatedAt());
        });

        // Check if the user exists.

        // Insert user if the user doesn't exist.


        return ResponseResult.success();
    }
}
