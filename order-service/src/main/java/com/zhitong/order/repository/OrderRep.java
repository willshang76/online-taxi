package com.zhitong.order.repository;

import com.zhitong.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRep extends JpaRepository<Order, Integer> {
}
