package com.zhitong.order.serviceclient;

import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.GetDirectionRequest;
import com.zhitong.internalcommon.response.Geocode;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("map-service")
public interface MapServiceClient {
    @RequestMapping(method = RequestMethod.POST, value = "/direction")
    ResponseResult<GetDirectionResponse> getDirection(@RequestBody GetDirectionRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/geocode")
    ResponseResult<Geocode> getGeo(@RequestBody Location request);
}
