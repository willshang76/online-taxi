package com.zhitong.vehicle.service;

import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.vehicle.entity.VehicleLocation;
import com.zhitong.vehicle.repository.VehicleLocationRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

    private final VehicleLocationRep vehicleLocationRep;

    public LocationService(VehicleLocationRep vehicleLocationRep) {
        this.vehicleLocationRep = vehicleLocationRep;
    }

    public ResponseResult<?> uploadLocation(int vehicleId, Location currentLocation) {

        VehicleLocation vehicleLocation = new VehicleLocation();
        vehicleLocation.setVehicleId(vehicleId);
        vehicleLocation.setLatitude(currentLocation.getLatitude());
        vehicleLocation.setLongitude(currentLocation.getLongitude());

        vehicleLocationRep.save(vehicleLocation);

        return ResponseResult.success(vehicleLocation);
    }
}
