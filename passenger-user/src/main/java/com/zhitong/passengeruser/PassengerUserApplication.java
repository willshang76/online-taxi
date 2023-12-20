package com.zhitong.passengeruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("com.zhitong.passengeruser.mapper")
@EnableDiscoveryClient
public class PassengerUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(PassengerUserApplication.class);
    }
}
