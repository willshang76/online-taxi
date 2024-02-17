package com.zhitong.internalcommon.datatoobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnitValue<T> {
    private T value;
    private String unit;
}
