package com.zhitong.apipassenger.service;

import com.zhitong.apipassenger.serviceclient.PassengerUserClient;
import com.zhitong.internalcommon.datatoobject.JwtTokenResult;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.internalcommon.util.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final PassengerUserClient passengerUserClient;

    public UserService(PassengerUserClient passengerUserClient) {
        this.passengerUserClient = passengerUserClient;
    }

    public ResponseResult<GetUserResponse> getUser(String accessToken) {
        JwtTokenResult tokenResult = Jwt.decodeToken(accessToken);
        log.info("tokenResult.getPhoneNumber() = " + tokenResult.getPhoneNumber());

        return passengerUserClient.getUser(tokenResult.getPhoneNumber());
    }
}
