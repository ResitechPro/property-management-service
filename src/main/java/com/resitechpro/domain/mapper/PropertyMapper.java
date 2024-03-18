package com.resitechpro.domain.mapper;

import com.resitechpro.domain.dto.request.property.PropertyRequestDto;
import com.resitechpro.domain.dto.response.property.PropertyResponseDto;
import com.resitechpro.domain.entity.Property;
import com.resitechpro.domain.dto.response.property.OnlyPropertyResponseDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "building.label", source = "buildingLabel")
    Property toProperty(PropertyRequestDto propertyRequestDto);

    PropertyResponseDto toDto(Property property);
    OnlyPropertyResponseDto toOnlyPropertyDto(Property property);
}