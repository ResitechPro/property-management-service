package com.resitechpro.domain.dto.response.building;

import com.resitechpro.domain.dto.response.image.ImageResponseDto;
import com.resitechpro.domain.dto.response.residence.ResidenceResponseDto;
import com.resitechpro.domain.entity.Building;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Building}
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuildingResponseDto implements Serializable {
    private String id;
    private String label;
    private String description;
    private Integer numberFloors;
    private ResidenceResponseDto residence;
    private List<ImageResponseDto> images;
}