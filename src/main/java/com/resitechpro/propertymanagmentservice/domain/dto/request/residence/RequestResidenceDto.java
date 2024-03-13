package com.resitechpro.propertymanagmentservice.domain.dto.request.residence;

import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Residence}
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@Getter
public class RequestResidenceDto implements Serializable {
    @NotNull(message = "Label is required")
    private  String label;
    @NotNull(message = "Description is required")
    private  String description;
    @NotNull(message = "Location is required")
    private  String location;
    @NotNull(message = "Surface is required")
    @Min(message = "Surface cannot be negative", value = 0)
    private  Long surface;
    @NotNull(message = "longitude is required")
    private  Long longitude;
    @NotNull(message = "Latitude is required")
    private  Long latitude;
}