package com.zhitong.order.entity;

import com.zhitong.internalcommon.constant.OrderStatus;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer vehicleId;
    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    private String oriLatitude;
    private String oriLongitude;
    private String desLatitude;
    private String desLongitude;
    private double price;
    private String currency;
}
