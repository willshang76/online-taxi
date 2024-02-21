package com.zhitong.order.asyncserviceclient;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.order.serviceclient.PassengerUserClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PassengerUserClientAsync {
    private final PassengerUserClient passengerUserClient;

    PassengerUserClientAsync(PassengerUserClient passengerUserClient) {
        this.passengerUserClient = passengerUserClient;
    }

    @Async
    public CompletableFuture<ResponseResult<GetUserResponse>> getUser(String phoneNumber) {
        return CompletableFuture.completedFuture(passengerUserClient.getUser(phoneNumber));
    }

}
