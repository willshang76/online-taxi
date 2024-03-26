package com.zhitong.order.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.InitializeOrderRequest;
import com.zhitong.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/initialize")
    public ResponseResult<?> initializeOrder(@RequestBody InitializeOrderRequest request) {
        return orderService.initializeOrder(request.getPhoneNumber(), request.getSouLocation(), request.getDesLocation(), request.getVehicleType());
    }

    @GetMapping("/{orderId}/assign/{vehicleId}")
    public ResponseResult<?> assignOrder(@PathVariable(name = "orderId") String orderId, @PathVariable(name = "vehicleId") String vehicleId) {
        System.out.println("orderId = " + orderId);
        System.out.println("vehicleId = " + vehicleId);
        return ResponseResult.success();
    }
}
