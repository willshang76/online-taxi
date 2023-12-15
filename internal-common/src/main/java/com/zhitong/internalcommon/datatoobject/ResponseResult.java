package com.zhitong.internalcommon.datatoobject;

import com.zhitong.internalcommon.constant.ResponseStatus;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseResult<T> {
    private int code;
    private String message;
    private T payload;

    public static <T> ResponseResult success() {
        return new ResponseResult().setCode(ResponseStatus.SUCCESS.getCode()).setMessage(ResponseStatus.SUCCESS.getMessage());
    }

    public static <T> ResponseResult success(T payload) {
        return new ResponseResult().setCode(ResponseStatus.SUCCESS.getCode()).setMessage(ResponseStatus.SUCCESS.getMessage()).setPayload(payload);
    }

    public static <T> ResponseResult fail() {
        return new ResponseResult().setCode(ResponseStatus.FAIL.getCode()).setMessage(ResponseStatus.FAIL.getMessage());
    }

    public static <T> ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }
}
