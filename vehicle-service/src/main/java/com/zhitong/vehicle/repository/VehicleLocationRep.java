package com.zhitong.vehicle.repository;

import com.zhitong.vehicle.entity.VehicleLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleLocationRep extends JpaRepository<VehicleLocation, Integer> {
}
