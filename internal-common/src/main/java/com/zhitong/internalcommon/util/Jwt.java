package com.zhitong.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Jwt {

    private static final String SIGNATURE = "eKKF2Q#T4fwpM@eJf3";

    public static String generateToken(Map<String, String> info){
        // Store information
        JWTCreator.Builder jwtBuilder = JWT.create();
        info.forEach(jwtBuilder::withClaim);

        // Set expiration date
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        Date date = calendar.getTime();
        jwtBuilder.withExpiresAt(date);

        // Sign the token
        return jwtBuilder.sign(Algorithm.HMAC256(SIGNATURE));
    }
}
