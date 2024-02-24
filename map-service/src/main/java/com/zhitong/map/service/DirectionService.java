package com.zhitong.map.service;

import com.google.maps.*;
import com.google.maps.model.*;
import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.datatoobject.UnitValue;
import com.zhitong.internalcommon.response.Geocode;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Slf4j
public class DirectionService {
    private static final int SECONDS_PER_MINUTE = 60;
    @Value("${map.google.key}")
    private String googleKey;

    public ResponseResult<Geocode> getCounty(Location curLocation) {
        // Create a GeoApiContext object.
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleKey)
                .build();

        GeocodingApiRequest request = GeocodingApi.newRequest(context);
        request.latlng(new LatLng(Double.parseDouble(curLocation.getLatitude()), Double.parseDouble(curLocation.getLongitude())));
        try {
            GeocodingResult[] resultList = request.await();

            Optional<AddressComponent> addressComp = Arrays.stream(resultList)
                    .flatMap(geocodingResult -> Stream.of(geocodingResult.addressComponents))
                    .filter(addressComponent -> Arrays.asList(addressComponent.types).contains(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2)).findFirst();

            return addressComp.isPresent() ? ResponseResult.success(new Geocode(addressComp.get().shortName)) : ResponseResult.success(new Geocode("DEFAULT"));

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.fail();
        }
    }

    public ResponseResult<GetDirectionResponse> getDirection(Location souLocation, Location desLocation) {
        // Create a GeoApiContext object.
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleKey)
                .build();

        // Create a DirectionsApiRequest object.
        DirectionsApiRequest request = DirectionsApi.newRequest(context);
        // Set the origin and destination.
        request.origin(souLocation.getLatitude() + "," + souLocation.getLongitude());
        request.destination(desLocation.getLatitude() + "," + desLocation.getLongitude());

        // Set the travel mode.
        request.mode(TravelMode.DRIVING);

        DirectionsResult directionsResult = null;

        try {
            // Send the request and get the response.
            directionsResult = request.await();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseResult.fail();
        }

        // Get the routes.
        DirectionsRoute[] routes = directionsResult.routes;

        DirectionsLeg theRouteDetails = routes[0].legs[0];
        UnitValue<Integer> distance = new UnitValue<Integer>((int) theRouteDetails.distance.inMeters, "meter");
        UnitValue<Integer> duration = new UnitValue<Integer>((int) theRouteDetails.duration.inSeconds / SECONDS_PER_MINUTE, "minute");

        GetDirectionResponse response = new GetDirectionResponse(distance, duration);

        return ResponseResult.success(response);
    }
}
