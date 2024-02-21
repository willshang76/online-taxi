package com.zhitong.order.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.zhitong.internalcommon.constant.UserTier;
import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.PriceOption;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.datatoobject.UnitValue;
import com.zhitong.internalcommon.request.GetDirectionRequest;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import com.zhitong.internalcommon.response.GetPriceResponse;
import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.order.asyncserviceclient.MapServiceClientAsync;
import com.zhitong.order.asyncserviceclient.PassengerUserClientAsync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class PriceService {

    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private final MapServiceClientAsync mapServiceClientAsync;
    private final PassengerUserClientAsync passengerUserClientAsync;
    ImmutableMap<UserTier, Float> discountMap = ImmutableMap.of(UserTier.TIER_0, (float) 1.0, UserTier.TIER_VIP, (float) 0.9, UserTier.TIER_VIP1, (float) 0.8);

    public PriceService(MapServiceClientAsync mapServiceClientAsync, PassengerUserClientAsync passengerUserClientAsync) {
        this.passengerUserClientAsync = passengerUserClientAsync;
        this.mapServiceClientAsync = mapServiceClientAsync;
    }

    public ResponseResult<GetPriceResponse> getPrice(String phoneNumber, Location souLocation, Location desLocation) {
        CompletableFuture<ResponseResult<GetUserResponse>> userResponseResponseResultFuture = passengerUserClientAsync.getUser(phoneNumber);
        CompletableFuture<ResponseResult<GetDirectionResponse>> directionResponseResponseResultFuture = mapServiceClientAsync.getDirection(
                new GetDirectionRequest(souLocation, desLocation));

        CompletableFuture.allOf(userResponseResponseResultFuture, directionResponseResponseResultFuture).join();

        try {
            ResponseResult<GetUserResponse> userResponseResponseResult = userResponseResponseResultFuture.get();
            ResponseResult<GetDirectionResponse> directionResponseResponseResult = directionResponseResponseResultFuture.get();

            System.out.println("userResponseResponseResult = " + userResponseResponseResult);
            System.out.println("directionResponseResponseResult = " + directionResponseResponseResult);

            PriceOption priceOption = getPriceOption(userResponseResponseResult, directionResponseResponseResult);

            return ResponseResult.success(new GetPriceResponse(ImmutableList.of(priceOption)));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseResult.fail();
        }
    }

    private PriceOption getPriceOption(ResponseResult<GetUserResponse> userResponseResponseResult, ResponseResult<GetDirectionResponse> directionResponseResponseResult) {
        UserTier userTier = userResponseResponseResult.getPayload().getUserTier();
        UnitValue<Integer> distance = directionResponseResponseResult.getPayload().getDistance();
        UnitValue<Integer> duration = directionResponseResponseResult.getPayload().getDuration();
        Float tierDiscount = discountMap.getOrDefault(userTier, (float) 1.0);
        if (tierDiscount == null) {
            tierDiscount = (float) 1.0;
        }

        return new PriceOption("flash", (float) (distance.getValue() * 1.5 + duration.getValue() / 10) * tierDiscount, "USD");
    }
}
