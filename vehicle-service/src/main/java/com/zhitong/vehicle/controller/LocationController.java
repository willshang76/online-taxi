package com.zhitong.vehicle.controller;


import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.vehicle.service.LocationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/upload-location/{id}")
    public ResponseResult<?> uploadLocation(@PathVariable("id") int vehicleId, @RequestBody Location currentLocation) {
        return locationService.uploadLocation(vehicleId, currentLocation);
    }
}
