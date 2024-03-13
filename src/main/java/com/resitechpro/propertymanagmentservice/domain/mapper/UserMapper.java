package com.resitechpro.propertymanagmentservice.domain.mapper;

import com.resitechpro.propertymanagmentservice.domain.dto.request.tenant.TenantCreationRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.request.user.UserRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.user.UserResponseDto;
import com.resitechpro.propertymanagmentservice.domain.entity.Tenant;
import com.resitechpro.propertymanagmentservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserRequestDto userDto);
    UserResponseDto toDto(User user);
    User tenantToUser(Tenant tenant);

    @Mapping(target = "id", source = "userId")
    User creationTenantDtoToUser(TenantCreationRequestDto tenantCreationRequestDto);

}
