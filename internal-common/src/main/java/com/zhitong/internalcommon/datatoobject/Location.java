package com.zhitong.internalcommon.datatoobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String latitude;
    private String longitude;
}
