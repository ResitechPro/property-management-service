package com.resitechpro.propertymanagmentservice.domain.mapper;

import com.resitechpro.propertymanagmentservice.domain.dto.request.building.BuildingRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.building.BuildingResponseDto;
import com.resitechpro.propertymanagmentservice.domain.entity.Building;
import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BuildingMapper {

    @Mapping(target = "residence.label", source = "residenceLabel")
    Building toBuilding(BuildingRequestDto buildingResponseDto);

    BuildingResponseDto toDto(Building building);

}