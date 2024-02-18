package com.zhitong.order.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.GetPriceRequest;
import com.zhitong.internalcommon.response.GetPriceResponse;
import com.zhitong.order.service.PriceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/price")
    public ResponseResult<GetPriceResponse> getPrice(@RequestBody GetPriceRequest request) {
        return priceService.getPrice(request.getPhoneNumber(), request.getSouLocation(), request.getDesLocation());
    }
}
