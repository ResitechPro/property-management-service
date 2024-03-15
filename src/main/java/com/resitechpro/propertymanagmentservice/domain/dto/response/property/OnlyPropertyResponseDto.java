package com.resitechpro.propertymanagmentservice.domain.dto.response.property;

import com.resitechpro.propertymanagmentservice.domain.dto.response.image.ImageResponseDto;
import com.resitechpro.propertymanagmentservice.domain.enums.PropertyType;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.resitechpro.propertymanagmentservice.domain.entity.Property}
 */
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class OnlyPropertyResponseDto implements Serializable {
    private String id;
    private String label;
    private String description;
    private PropertyType propertyType;
    private Long surface;
    private Integer numberRooms;
    private Integer numberBathrooms;
    private Integer numberBedrooms;
    private Integer numberWindows;
    private String floorNumber;
    private String rentValue;
    private List<ImageResponseDto> images;
}