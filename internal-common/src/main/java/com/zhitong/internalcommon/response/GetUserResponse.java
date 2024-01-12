package com.zhitong.internalcommon.response;

import com.zhitong.internalcommon.constant.Gender;
import com.zhitong.internalcommon.constant.Status;
import com.zhitong.internalcommon.constant.UserTier;
import lombok.Data;

@Data
public class GetUserResponse {
    private String phoneNumber;
    private String name;
    private Gender gender;
    private Status status;
    private String avatarUrl;
    private UserTier userTier;
}
