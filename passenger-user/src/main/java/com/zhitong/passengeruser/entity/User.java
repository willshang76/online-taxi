package com.zhitong.passengeruser.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhitong.internalcommon.constant.Gender;
import com.zhitong.internalcommon.constant.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    private Long id;
    private String phoneNumber;
    private String name;
    private Gender gender;
    private Status status;
    //    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
