package com.zhitong.order.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "price_cal_rules")
@Data
@ToString
public class PriceCalRule {
    @Id
    @EmbeddedId
    private PriceCalRuleId id;

    private double baseFare;
    private int baseMiles;
    private double farePerMile;
    private double farePerMinute;
}
