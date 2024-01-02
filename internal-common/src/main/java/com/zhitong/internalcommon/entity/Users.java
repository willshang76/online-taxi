package com.zhitong.internalcommon.entity;

import com.zhitong.internalcommon.constant.Gender;
import com.zhitong.internalcommon.constant.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Users {
    private Long id;
    private String phoneNumber;
    private String name;
    private Gender gender;
    private Status status;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
