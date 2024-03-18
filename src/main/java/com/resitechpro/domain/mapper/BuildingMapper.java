package com.resitechpro.domain.mapper;

import com.resitechpro.domain.dto.request.building.BuildingRequestDto;
import com.resitechpro.domain.dto.response.building.BuildingResponseDto;
import com.resitechpro.domain.entity.Building;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BuildingMapper {

    @Mapping(target = "residence.label", source = "residenceLabel")
    Building toBuilding(BuildingRequestDto buildingResponseDto);

    BuildingResponseDto toDto(Building building);

}