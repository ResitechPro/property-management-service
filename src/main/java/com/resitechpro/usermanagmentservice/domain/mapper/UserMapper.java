package com.resitechpro.usermanagmentservice.domain.mapper;

import com.resitechpro.usermanagmentservice.domain.dto.request.user.UserRequestDto;
import com.resitechpro.usermanagmentservice.domain.dto.response.user.UserResponseDto;
import com.resitechpro.usermanagmentservice.domain.entity.Tenant;
import com.resitechpro.usermanagmentservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequestDto userDto);
    UserResponseDto toDto(User user);
    User tenantToUser(Tenant tenant);

}
