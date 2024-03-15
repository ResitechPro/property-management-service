package com.resitechpro.propertymanagmentservice.domain.mapper;

import com.resitechpro.propertymanagmentservice.domain.entity.Image;
import com.resitechpro.propertymanagmentservice.domain.dto.response.image.ImageResponseDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImageMapper {
    ImageResponseDto toDto(Image image);

}