package com.zhitong.order.asyncserviceclient;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.GetDirectionRequest;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import com.zhitong.order.serviceclient.MapServiceClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MapServiceClientAsync {
    private final MapServiceClient mapServiceClient;

    MapServiceClientAsync(MapServiceClient mapServiceClient) {
        this.mapServiceClient = mapServiceClient;
    }

    @Async
    public CompletableFuture<ResponseResult<GetDirectionResponse>> getDirection(GetDirectionRequest request) {
        return CompletableFuture.completedFuture(mapServiceClient.getDirection(request));
    }
}
