package com.resitechpro.propertymanagmentservice.domain.dto.response.building;

import com.resitechpro.propertymanagmentservice.domain.dto.response.residence.ResidenceResponseDto;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.resitechpro.propertymanagmentservice.domain.entity.Building}
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuildingResponseDto implements Serializable {
    private  String label;
    private  String description;
    private  Integer numberFloors;
    private  ResidenceResponseDto residence;
}