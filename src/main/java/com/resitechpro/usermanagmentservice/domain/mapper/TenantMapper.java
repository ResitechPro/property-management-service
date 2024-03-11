package com.resitechpro.usermanagmentservice.domain.mapper;

import com.resitechpro.usermanagmentservice.domain.entity.Tenant;
import com.resitechpro.usermanagmentservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);
    Tenant userToTenant(User user);
}
