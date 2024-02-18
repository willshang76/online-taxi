package com.zhitong.internalcommon.response;

import com.zhitong.internalcommon.datatoobject.PriceOption;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetPriceResponse {
    private List<PriceOption> options;
}
