package com.resitechpro.propertymanagmentservice.domain.dto.response.residence;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResidenceResponseDto implements Serializable {
    private  String id;
    private  String label;
    private  String description;
    private  String location;
    private  Long surface;
    private  Long longitude;
    private  Long latitude;
}