package com.zhitong.order.serviceclient;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.GetUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("passenger-user")
public interface PassengerUserClient {
    @RequestMapping(method = RequestMethod.GET, value = "/user/{phoneNumber}")
    ResponseResult<GetUserResponse> getUser(@PathVariable String phoneNumber);
}
