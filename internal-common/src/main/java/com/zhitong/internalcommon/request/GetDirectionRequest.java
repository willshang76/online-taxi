package com.zhitong.internalcommon.request;

import com.zhitong.internalcommon.datatoobject.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetDirectionRequest {
    private Location souLocation;
    private Location desLocation;
}
