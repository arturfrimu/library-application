package com.arturfrimu.library.mapper;

import com.arturfrimu.library.dto.response.UserResponse;
import com.arturfrimu.library.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "addressId", source = "address.id")
    UserResponse toResponse(User user);
}