package com.zhitong.order.service;

import com.zhitong.internalcommon.constant.OrderStatus;
import com.zhitong.internalcommon.constant.ResponseStatus;
import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.PriceOption;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.GetPriceResponse;
import com.zhitong.order.entity.Order;
import com.zhitong.order.repository.OrderRep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final PriceService priceService;
    private final OrderRep orderRep;

    public OrderService(PriceService priceService, OrderRep orderRep) {
        this.priceService = priceService;
        this.orderRep = orderRep;
    }

    public ResponseResult<?> initializeOrder(String phoneNumber, Location souLocation, Location desLocation, String vehicleType) {
        ResponseResult<GetPriceResponse> getPriceResponseResponseResult = priceService.getPrice(phoneNumber, souLocation, desLocation);
        Optional<PriceOption> priceOptionOptional = getPriceResponseResponseResult.getPayload().getOptions().stream().filter(priceOption -> priceOption.getVehicleType().equals(vehicleType)).findFirst();
        Order initailOrder = new Order();
        initailOrder.setPhoneNumber(phoneNumber);
        initailOrder.setOriLatitude(souLocation.getLatitude());
        initailOrder.setOriLongitude(souLocation.getLongitude());
        initailOrder.setDesLatitude(desLocation.getLatitude());
        initailOrder.setDesLongitude(desLocation.getLongitude());
        if (priceOptionOptional.isPresent()) {
            initailOrder.setPrice(priceOptionOptional.get().getPrice());
            initailOrder.setCurrency(priceOptionOptional.get().getCurrency());
        } else {
            return ResponseResult.fail();
        }

        orderRep.save(initailOrder);

        return ResponseResult.success(initailOrder);
    }

    public ResponseResult<?> assignOrder(int orderId, int vehicleId) {

        List<Order> existingOrderList = this.orderRep.findByStatusAndVehicleId(OrderStatus.ASSIGNED, vehicleId);
        if (!existingOrderList.isEmpty()) {
            return ResponseResult.fail(ResponseStatus.EXISTING_ORDER.getCode(), ResponseStatus.EXISTING_ORDER.getMessage());
        }
        Optional<Order> optionalPendingOrder = this.orderRep.findById(orderId);

        if (!optionalPendingOrder.isPresent()) {
            return ResponseResult.fail(ResponseStatus.ORDER_NOT_FOUND.getCode(), ResponseStatus.ORDER_NOT_FOUND.getMessage());
        }

        Order pendingOrder = optionalPendingOrder.get();
        pendingOrder.setVehicleId(vehicleId);
        pendingOrder.setStatus(OrderStatus.ASSIGNED);

        orderRep.save(pendingOrder);

        return ResponseResult.success(pendingOrder);
    }
}
