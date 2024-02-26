package com.zhitong.order.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.GetPriceRequest;
import com.zhitong.internalcommon.response.GetPriceResponse;
import com.zhitong.order.entity.PriceCalRule;
import com.zhitong.order.service.PriceService;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/rules/{county}")
    public ResponseResult<List<PriceCalRule>> getRules(@PathVariable(name = "county") String county) throws ExecutionException, InterruptedException {
        System.out.println("county = " + county);
        return priceService.getRules(county);
    }
}
