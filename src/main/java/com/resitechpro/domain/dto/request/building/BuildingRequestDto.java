package com.resitechpro.domain.dto.request.building;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class BuildingRequestDto implements Serializable {
    @NotNull(message = "Label is required")
    private String label;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Number of floors is required")
    @Min(message = "minimum floors number  is one", value = 1)
    private Integer numberFloors;
    @NotNull(message = "Residence label is required")
    private String residenceLabel;
}