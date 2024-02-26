package com.zhitong.order.repository;

import com.zhitong.order.entity.PriceCalRule;
import com.zhitong.order.entity.PriceCalRuleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PriceCalRuleRep extends JpaRepository<PriceCalRule, PriceCalRuleId> {
    @Query("SELECT e FROM PriceCalRule e WHERE e.id.county = :county")
    List<PriceCalRule> findByCounty(@Param("county") String county);
}
