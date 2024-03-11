package com.resitechpro.usermanagmentservice.domain.mapper;

import com.resitechpro.usermanagmentservice.domain.dto.request.user.UserRequestDto;
import com.resitechpro.usermanagmentservice.domain.dto.response.user.UserResponseDto;
import com.resitechpro.usermanagmentservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequestDto userDto);
    UserResponseDto toDto(User user);

}
