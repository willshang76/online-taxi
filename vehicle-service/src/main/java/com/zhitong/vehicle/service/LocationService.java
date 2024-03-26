package com.zhitong.vehicle.service;

import com.google.common.collect.ImmutableList;
import com.zhitong.internalcommon.datatoobject.Location;
import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.vehicle.entity.VehicleLocation;
import com.zhitong.vehicle.repository.VehicleLocationRep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private static final double radiusInMeter = 1600;
    private static final double earthCirc = 40000 * 1000;

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

    public ResponseResult<?> listVehiclesLocation(Location currentLocation) {

        double offsetMeters = radiusInMeter / Math.cos(Math.toRadians(Double.parseDouble(currentLocation.getLatitude())));

        List<VehicleLocation> listOfVehicleLocations = vehicleLocationRep.findByLocationWithinSquareAndRadius(
                Double.parseDouble(currentLocation.getLatitude()),
                offsetMeters,
                earthCirc,
                Double.parseDouble(currentLocation.getLongitude()),
                radiusInMeter);

        return ResponseResult.success(listOfVehicleLocations);
    }
}
