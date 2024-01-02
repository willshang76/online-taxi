package com.zhitong.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.google.common.base.Strings;
import com.zhitong.internalcommon.constant.JwtInfo;
import com.zhitong.internalcommon.constant.ResponseStatus;
import com.zhitong.internalcommon.datatoobject.JwtTokenResult;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.util.Jwt;
import com.zhitong.internalcommon.util.RedisGeneral;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtVerificationInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean verifyResult = true;
        String errorMessage = "";

        String token = request.getHeader("Authorization");

        JwtTokenResult tokenResult = null;

        try {
            tokenResult = Jwt.decodeToken(token);
        } catch (SignatureVerificationException e) {
            verifyResult = false;
            errorMessage = "Signature verification failed";
        } catch (TokenExpiredException e) {
            verifyResult = false;
            errorMessage = "Token expired";
        } catch (AlgorithmMismatchException e) {
            verifyResult = false;
            errorMessage = "Algorithm mismatched";
        } catch (Exception e) {
            verifyResult = false;
            errorMessage = "Invalid token";
        }

        if (tokenResult != null) {

            String phoneNumber = tokenResult.getPhoneNumber();
            String userType = tokenResult.getUserType();

            String tokenRedisKey = RedisGeneral.generateTokenKey(phoneNumber, userType, JwtInfo.Token.ACCESS_TOKEN.toString());

            String tokenValue = stringRedisTemplate.opsForValue().get(tokenRedisKey);
            if (Strings.isNullOrEmpty(tokenValue)) {
                verifyResult = false;
                errorMessage = "No token record";
            } else if (!tokenValue.trim().equals(token.trim())) {
                verifyResult = false;
                errorMessage = "Token doesn't match";
            }
        }

        if (!verifyResult) {
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(ResponseStatus.FAIL.getCode(), errorMessage)).toString());
        }

        return verifyResult;
    }
}
