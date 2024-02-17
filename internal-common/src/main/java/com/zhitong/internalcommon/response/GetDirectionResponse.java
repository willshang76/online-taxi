package com.zhitong.internalcommon.response;

import com.zhitong.internalcommon.datatoobject.UnitValue;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetDirectionResponse {
    private UnitValue<Integer> distance;
    private UnitValue<Integer> duration;
}
