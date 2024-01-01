package com.zhitong.apipassenger.service;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.zhitong.internalcommon.constant.JwtInfo;
import com.zhitong.internalcommon.constant.ResponseStatus;
import com.zhitong.internalcommon.datatoobject.JwtTokenResult;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.VerifiedTokenResponse;
import com.zhitong.internalcommon.util.Jwt;
import com.zhitong.internalcommon.util.RedisGeneral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult<VerifiedTokenResponse> refreshToken(String sourceRefreshToken) {
        System.out.println("sourceRefreshToken = " + sourceRefreshToken);
        // Decrypt Token
        JwtTokenResult tokenResult = null;

        try {
            tokenResult = Jwt.decodeToken(sourceRefreshToken);
        } catch (Exception e) {
            return ResponseResult.fail(ResponseStatus.INVALID_TOKEN.getCode(), ResponseStatus.INVALID_TOKEN.getMessage());
        }

        // Get the Token stored in redis
        String phoneNumber = tokenResult.getPhoneNumber();
        String userType = tokenResult.getUserType();

        String tokenRedisKey = RedisGeneral.generateTokenKey(phoneNumber, userType, JwtInfo.Token.REFRESH_TOKEN.toString());

        String tokenValue = stringRedisTemplate.opsForValue().get(tokenRedisKey);
        System.out.println("tokenValue = " + tokenValue);

        // Verify token
        if (Strings.isNullOrEmpty(tokenValue) || !tokenValue.trim().equals(sourceRefreshToken.trim())) {
            return ResponseResult.fail(ResponseStatus.INVALID_TOKEN.getCode(), ResponseStatus.INVALID_TOKEN.getMessage());
        }

        // Generate token
        String accessToken = Jwt.generateToken(ImmutableMap.of(JwtInfo.PHONE_KEY, phoneNumber, JwtInfo.USER_TYPE_KEY, userType, JwtInfo.TOKEN_TYPE, JwtInfo.Token.ACCESS_TOKEN.toString()));
        String refreshToken = Jwt.generateToken(ImmutableMap.of(JwtInfo.PHONE_KEY, phoneNumber, JwtInfo.USER_TYPE_KEY, userType, JwtInfo.TOKEN_TYPE, JwtInfo.Token.REFRESH_TOKEN.toString()));

        // Store token in Redis
        stringRedisTemplate.opsForValue().set(RedisGeneral.generateTokenKey(phoneNumber, userType, JwtInfo.Token.ACCESS_TOKEN.toString()), accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(RedisGeneral.generateTokenKey(phoneNumber, userType, JwtInfo.Token.REFRESH_TOKEN.toString()), refreshToken, 40, TimeUnit.DAYS);

        // return response
        VerifiedTokenResponse verifiedTokenResponse = new VerifiedTokenResponse();
        verifiedTokenResponse.setAccessToken(accessToken);
        verifiedTokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(verifiedTokenResponse);
    }
}
