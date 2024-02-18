package com.zhitong.internalcommon.datatoobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceOption {
    private String vehicleType;
    private float price;
    private String currency;
}
