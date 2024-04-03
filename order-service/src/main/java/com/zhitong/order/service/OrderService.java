package com.zhitong.order.service;

import com.zhitong.internalcommon.constant.OrderStatus;
import com.zhitong.internalcommon.constant.ResponseStatus;
import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.PriceOption;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.GetPriceResponse;
import com.zhitong.order.entity.Order;
import com.zhitong.order.repository.OrderRep;
import lombok.var;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final PriceService priceService;
    private final OrderRep orderRep;
    private final RedissonClient redissonClient;

    public OrderService(PriceService priceService, OrderRep orderRep, RedissonClient redissonClient) {
        this.priceService = priceService;
        this.orderRep = orderRep;
        this.redissonClient = redissonClient;
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

    public synchronized ResponseResult<?> assignOrder(int orderId, int vehicleId) {

        var vehicleLock = redissonClient.getLock("vehicleId:" + vehicleId);
        var orderLock = redissonClient.getLock("orderId:" + orderId);

        try {
            // Acquire the lock
            boolean isVehicleLocked = vehicleLock.tryLock();

            if (isVehicleLocked) {

                List<Order> existingOrderList = this.orderRep.findByStatusAndVehicleId(OrderStatus.ASSIGNED, vehicleId);
                if (!existingOrderList.isEmpty()) {
                    return ResponseResult.fail(ResponseStatus.EXISTING_ORDER.getCode(), ResponseStatus.EXISTING_ORDER.getMessage());
                }
                // Acquire the lock
                boolean isOrderLocked = orderLock.tryLock();

                if (isOrderLocked) {

                    Optional<Order> optionalPendingOrder = this.orderRep.findById(orderId);

                    if (!optionalPendingOrder.isPresent() || optionalPendingOrder.get().getStatus() != OrderStatus.PENDING) {
                        return ResponseResult.fail(ResponseStatus.ORDER_NOT_FOUND.getCode(), ResponseStatus.ORDER_NOT_FOUND.getMessage());
                    }

                    Order pendingOrder = optionalPendingOrder.get();
                    pendingOrder.setVehicleId(vehicleId);
                    pendingOrder.setStatus(OrderStatus.ASSIGNED);

                    orderRep.save(pendingOrder);

                    System.out.println("pendingOrder = " + pendingOrder);

                    return ResponseResult.success(pendingOrder);
                } else {
                    System.out.println("Could not acquire the order lock");
                    return ResponseResult.fail(ResponseStatus.ORDER_NOT_FOUND.getCode(), ResponseStatus.ORDER_NOT_FOUND.getMessage());
                }


            } else {
                System.out.println("Could not acquire the vehicle lock");
                return ResponseResult.fail(ResponseStatus.ORDER_NOT_FOUND.getCode(), ResponseStatus.ORDER_NOT_FOUND.getMessage());
            }
        } finally {
            // Ensure the lock is released even if an exception occurs
            vehicleLock.unlock();
            orderLock.unlock();
        }
    }
}
