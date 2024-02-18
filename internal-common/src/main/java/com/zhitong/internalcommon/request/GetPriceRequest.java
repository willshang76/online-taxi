package com.zhitong.internalcommon.request;

import com.zhitong.internalcommon.datatoobject.Location;
import lombok.Data;

@Data
public class GetPriceRequest {
    private String phoneNumber;
    private Location souLocation;
    private Location desLocation;
}
