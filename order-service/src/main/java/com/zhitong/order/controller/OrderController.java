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

    @PostMapping("/{orderId}/assign/{vehicleId}")
    public ResponseResult<?> assignOrder(@PathVariable(name = "orderId") int orderId, @PathVariable(name = "vehicleId") int vehicleId) {

        return orderService.assignOrder(orderId, vehicleId);
    }
}
