package com.zhitong.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zhitong.internalcommon.constant.ResponseStatus;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.util.Jwt;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

public class JwtVerificationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean verifyResult = true;
        String errorMessage = "";

        String token = request.getHeader("Authorization");

        try {
            Jwt.decodeToken(token);
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
            errorMessage = "Someting wrong";
        }

        if (!verifyResult) {
            PrintWriter out = response.getWriter();
            out.print(JSONObject.fromObject(ResponseResult.fail(ResponseStatus.FAIL.getCode(), errorMessage).toString()));
        }

        return verifyResult;
    }
}
