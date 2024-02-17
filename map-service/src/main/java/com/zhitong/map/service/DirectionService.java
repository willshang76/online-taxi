package com.zhitong.map.service;

import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DirectionService {
    @Value("${map.google.key}")
    String googleKey;

    public ResponseResult<GetDirectionResponse> getDirection(Location souLocation, Location desLocation) {
        System.out.println("souLocation = " + souLocation);
        System.out.println("desLocation = " + desLocation);
        System.out.println("googleKey = " + googleKey);
        return null;
    }
}
