package com.resitechpro.propertymanagmentservice.domain.mapper;

import com.resitechpro.propertymanagmentservice.domain.dto.request.property.PropertyRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.property.OnlyPropertyResponseDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.property.PropertyResponseDto;
import com.resitechpro.propertymanagmentservice.domain.entity.Property;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PropertyMapper {

    @Mapping(target = "building.label", source = "buildingLabel")
    Property toProperty(PropertyRequestDto propertyRequestDto);

    PropertyResponseDto toDto(Property property);
    OnlyPropertyResponseDto toOnlyPropertyDto(Property property);
}