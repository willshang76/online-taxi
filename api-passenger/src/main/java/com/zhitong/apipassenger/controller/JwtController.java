package com.zhitong.apipassenger.controller;

import com.zhitong.apipassenger.requestEntity.RefreshTokenRequest;
import com.zhitong.apipassenger.service.JwtService;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.VerifiedTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/refresh-token")
    public ResponseResult<VerifiedTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return jwtService.refreshToken(refreshTokenRequest.getRefreshToken());
    }
}
