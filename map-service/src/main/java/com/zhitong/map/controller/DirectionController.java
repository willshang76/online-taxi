package com.zhitong.map.controller;

import com.zhitong.internalcommon.datatoobject.ResponseResult;
import com.zhitong.internalcommon.request.GetDirectionRequest;
import com.zhitong.internalcommon.response.GetDirectionResponse;
import com.zhitong.map.service.DirectionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DirectionController {

    private final DirectionService directionService;

    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    @PostMapping("/direction")
    public ResponseResult<GetDirectionResponse> getDirection(@RequestBody GetDirectionRequest request) {
        return directionService.getDirection(request.getSouLocation(), request.getDesLocation());
    }
}
