package com.zhitong.order.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.zhitong.internalcommon.constant.UserTier;
import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.PriceOption;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.datatoobject.UnitValue;
import com.zhitong.internalcommon.request.GetDirectionRequest;
import com.zhitong.internalcommon.response.Geocode;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import com.zhitong.internalcommon.response.GetPriceResponse;
import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.order.asyncserviceclient.MapServiceClientAsync;
import com.zhitong.order.asyncserviceclient.PassengerUserClientAsync;
import com.zhitong.order.asyncserviceclient.PriceCalRuleClientAsync;
import com.zhitong.order.entity.PriceCalRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class PriceService {

    private static final int METERS_PER_MILE = 1600;
    private static final int SECONDS_PER_MIN = 60;
    private static final Logger logger = LoggerFactory.getLogger(PriceService.class);

    private final MapServiceClientAsync mapServiceClientAsync;
    private final PassengerUserClientAsync passengerUserClientAsync;
    private final PriceCalRuleClientAsync priceCalRuleClientAsync;
    ImmutableMap<UserTier, Float> discountMap = ImmutableMap.of(UserTier.TIER_0, (float) 1.0, UserTier.TIER_VIP, (float) 0.9, UserTier.TIER_VIP1, (float) 0.8);

    public PriceService(MapServiceClientAsync mapServiceClientAsync, PassengerUserClientAsync passengerUserClientAsync, PriceCalRuleClientAsync priceCalRuleClientAsync) {
        this.passengerUserClientAsync = passengerUserClientAsync;
        this.mapServiceClientAsync = mapServiceClientAsync;
        this.priceCalRuleClientAsync = priceCalRuleClientAsync;
    }

    public ResponseResult<List<PriceCalRule>> getRules(String county) throws ExecutionException, InterruptedException {
        return ResponseResult.success(priceCalRuleClientAsync.getRuleByCounty(county).get());
    }

    public ResponseResult<GetPriceResponse> getPrice(String phoneNumber, Location souLocation, Location desLocation) {
        CompletableFuture<ResponseResult<GetUserResponse>> userResponseResponseResultFuture = passengerUserClientAsync.getUser(phoneNumber);
        CompletableFuture<ResponseResult<GetDirectionResponse>> directionResponseResponseResultFuture = mapServiceClientAsync.getDirection(
                new GetDirectionRequest(souLocation, desLocation));
        CompletableFuture<List<PriceCalRule>> priceRulesFuture = getPriceRule(souLocation);

        CompletableFuture.allOf(userResponseResponseResultFuture, directionResponseResponseResultFuture, priceRulesFuture).join();

        try {
            ResponseResult<GetUserResponse> userResponseResponseResult = userResponseResponseResultFuture.get();
            ResponseResult<GetDirectionResponse> directionResponseResponseResult = directionResponseResponseResultFuture.get();
            List<PriceCalRule> priceCalRules = priceRulesFuture.get();

            System.out.println("userResponseResponseResult = " + userResponseResponseResult);
            System.out.println("directionResponseResponseResult = " + directionResponseResponseResult);
            priceCalRules.forEach(priceRule -> System.out.println(priceRule.toString()));

            List<PriceOption> priceOptions = getPriceOption(userResponseResponseResult, directionResponseResponseResult, priceCalRules);

            return ResponseResult.success(new GetPriceResponse(priceOptions));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseResult.fail();
        }
    }

    @Async
    protected CompletableFuture<List<PriceCalRule>> getPriceRule(Location location) {
        CompletableFuture<ResponseResult<Geocode>> geoCodeResult = mapServiceClientAsync.getGeocode(location);
        try {
            ResponseResult<Geocode> geocodeResponseResult = geoCodeResult.get();
            System.out.println("geocodeResponseResult = " + geocodeResponseResult);
            System.out.println("geocodeResponseResult.getPayload() = " + geocodeResponseResult.getPayload());
            Geocode geocode = geocodeResponseResult.getPayload();

            return priceCalRuleClientAsync.getRuleByCounty(geocode.getCounty());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return CompletableFuture.completedFuture(null);
        }
    }

    private List<PriceOption> getPriceOption(ResponseResult<GetUserResponse> userResponseResponseResult, ResponseResult<GetDirectionResponse> directionResponseResponseResult, List<PriceCalRule> priceCalRules) {
        UserTier userTier = userResponseResponseResult.getPayload().getUserTier();
        UnitValue<Integer> distance = directionResponseResponseResult.getPayload().getDistance();
        UnitValue<Integer> duration = directionResponseResponseResult.getPayload().getDuration();
        double miles = (double) distance.getValue() / METERS_PER_MILE;
        double minutes = (double) duration.getValue() / SECONDS_PER_MIN;
        ImmutableList.Builder<PriceOption> listBuilder = ImmutableList.builder();

        Optional<Float> tierDiscount = Optional.ofNullable(discountMap.getOrDefault(userTier, (float) 1.0));

        priceCalRules.forEach(priceCalRule -> {
            double price = priceCalRule.getBaseFare() + minutes * priceCalRule.getFarePerMinute();


            price = (price + Math.max(0, (miles - priceCalRule.getBaseMiles()) * priceCalRule.getFarePerMile())) * tierDiscount.orElse((float) 1.0);
            BigDecimal bigDecimal = new BigDecimal(price).setScale(2, RoundingMode.CEILING);
            listBuilder.add(new PriceOption(priceCalRule.getId().getVehicleType(), bigDecimal.floatValue(), "USD"));

        });

        return listBuilder.build();
    }
}
