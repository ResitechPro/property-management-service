package com.resitechpro.domain.mapper;

import com.resitechpro.domain.entity.Image;
import com.resitechpro.domain.dto.response.image.ImageResponseDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ImageMapper {
    ImageResponseDto toDto(Image image);

}