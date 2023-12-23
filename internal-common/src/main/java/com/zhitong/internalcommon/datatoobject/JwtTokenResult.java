package com.zhitong.internalcommon.datatoobject;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JwtTokenResult {
    private String phoneNumber;
    private String userType;
}
