package com.zhitong.apipassenger.service;

import com.zhitong.internalcommon.datatoobject.JwtTokenResult;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.entity.User;
import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.internalcommon.util.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    public ResponseResult<User> getUser(String accessToken) {
        JwtTokenResult tokenResult = Jwt.decodeToken(accessToken);
        log.info("tokenResult.getPhoneNumber() = " + tokenResult.getPhoneNumber());
        User user = new User();
        user.setName("tests");
        user.setAvatarUrl("tests url");
        return ResponseResult.success(user);
    }
}
