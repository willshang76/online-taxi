package com.zhitong.order.repository;

import com.zhitong.internalcommon.constant.OrderStatus;
import com.zhitong.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRep extends JpaRepository<Order, Integer> {
    List<Order> findByStatusAndVehicleId(OrderStatus status, int vehicleId);
}
