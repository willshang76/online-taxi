package com.zhitong.passengeruser.service;

import com.zhitong.internalcommon.constant.Status;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.entity.Users;
import com.zhitong.passengeruser.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public ResponseResult loginAndCreateUserWhenNeed(String phoneNumber) {
        System.out.println("phoneNumber = " + phoneNumber);
        // Search user based on the phone number in Mysql.
        Map<String, Object> phoneMap = new HashMap();
        phoneMap.put("phone_number", phoneNumber);

        List<Users> users = userMapper.selectByMap(phoneMap);

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
        if (users.isEmpty()) {
            // Insert user if the user doesn't exist.
            Users newUser = new Users();
            newUser.setPhoneNumber(phoneNumber);
            newUser.setName("random name" + UUID.randomUUID().toString().substring(0, 5));
            newUser.setStatus(Status.ACTIVE);
            userMapper.insert(newUser);
        }

        return ResponseResult.success();
    }
}
