package com.resitechpro.propertymanagmentservice.domain.mapper;

import com.resitechpro.propertymanagmentservice.domain.dto.response.residence.ResidenceResponseDto;
import com.resitechpro.propertymanagmentservice.domain.dto.request.residence.RequestResidenceDto;
import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ResidenceMapper {
    ResidenceResponseDto toDto(Residence residence);
    Residence toResidence(RequestResidenceDto requestResidenceDto);
}