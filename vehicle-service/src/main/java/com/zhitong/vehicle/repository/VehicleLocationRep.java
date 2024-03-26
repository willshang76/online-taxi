package com.zhitong.vehicle.repository;

import com.zhitong.vehicle.entity.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleLocationRep extends JpaRepository<VehicleLocation, Integer> {

    @Query(value = "SELECT * FROM vehicle_locations i WHERE cast(i.latitude AS DOUBLE) >= :centerLatitude - :offsetMeters / :earthCirc " +
            "AND cast(i.latitude AS DOUBLE) <= :centerLatitude + :offsetMeters / :earthCirc " +
            "AND cast(i.longitude AS DOUBLE) >= :centerLongitude - :offsetMeters / (:earthCirc * cos(radians(:centerLatitude))) " +
            "AND cast(i.longitude AS DOUBLE) <= :centerLongitude + :offsetMeters / (:earthCirc * cos(radians(:centerLatitude)))" +
            "AND ST_Distance_Sphere(POINT(i.longitude, i.latitude), POINT(:centerLongitude, :centerLatitude)) <= :radius", nativeQuery = true)
    List<VehicleLocation> findByLocationWithinSquareAndRadius(@Param("centerLatitude") double centerLat,
                                                              @Param("offsetMeters") double offsetMeters,
                                                              @Param("earthCirc") double earthCirc,
                                                              @Param("centerLongitude") double centerLng,
                                                              @Param("radius") double radius);
}
