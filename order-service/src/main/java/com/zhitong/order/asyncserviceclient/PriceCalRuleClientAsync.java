package com.zhitong.order.asyncserviceclient;

import com.zhitong.order.entity.PriceCalRule;
import com.zhitong.order.repository.PriceCalRuleRep;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PriceCalRuleClientAsync {
    private final PriceCalRuleRep priceCalRuleRep;

    public PriceCalRuleClientAsync(PriceCalRuleRep priceCalRuleRep) {
        this.priceCalRuleRep = priceCalRuleRep;
    }

    @Async
    public CompletableFuture<List<PriceCalRule>> getRuleByCounty(String county) {
        return CompletableFuture.completedFuture(priceCalRuleRep.findByCounty(county));
    }
}
