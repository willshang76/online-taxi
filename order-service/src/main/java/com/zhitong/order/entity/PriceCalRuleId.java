package com.zhitong.order.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class PriceCalRuleId implements Serializable {
    private String county;
    private String vehicleType;
}
