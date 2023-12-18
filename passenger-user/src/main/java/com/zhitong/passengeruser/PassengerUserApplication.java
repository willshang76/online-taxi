package com.zhitong.passengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhitong.passengeruser.mapper")
public class PassengerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassengerUserApplication.class);
    }
}
