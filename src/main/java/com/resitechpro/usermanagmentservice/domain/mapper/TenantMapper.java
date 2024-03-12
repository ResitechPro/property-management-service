package com.resitechpro.usermanagmentservice.domain.mapper;

import com.resitechpro.usermanagmentservice.domain.dto.request.tenant.TenantCreationRequestDto;
import com.resitechpro.usermanagmentservice.domain.entity.Tenant;
import com.resitechpro.usermanagmentservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TenantMapper {
    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);
    @Mapping(target = "tenantId", source = "organizationId")
    Tenant toTenant(TenantCreationRequestDto tenantCreationRequestDto);
    Tenant userToTenant(User user);


}
