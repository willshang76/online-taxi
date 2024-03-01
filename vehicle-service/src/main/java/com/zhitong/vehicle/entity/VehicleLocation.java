package com.zhitong.vehicle.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicle_locations")
@Data
@ToString
public class VehicleLocation {
    @Id
    private Integer vehicleId;
    private String latitude;
    private String longitude;
}
