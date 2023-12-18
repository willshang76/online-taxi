package com.zhitong.passengeruser.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhitong.passengeruser.constants.Gender;
import com.zhitong.passengeruser.constants.Status;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
