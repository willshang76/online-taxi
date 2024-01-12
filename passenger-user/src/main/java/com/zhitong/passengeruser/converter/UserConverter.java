package com.zhitong.passengeruser.converter;

import com.zhitong.internalcommon.response.GetUserResponse;
import com.zhitong.passengeruser.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    GetUserResponse userToGetUserResponse(User user);
}
