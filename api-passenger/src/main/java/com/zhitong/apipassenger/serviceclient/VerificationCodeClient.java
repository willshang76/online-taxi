package com.zhitong.apipassenger.serviceclient;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.DigitalCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("verification-code")
public interface VerificationCodeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/digitalCode")
    ResponseResult<DigitalCodeResponse> getDigitalCode(@RequestParam int size);
}
