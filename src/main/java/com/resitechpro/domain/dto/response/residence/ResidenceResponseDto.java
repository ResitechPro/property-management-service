package com.resitechpro.domain.dto.response.residence;

import com.resitechpro.domain.dto.response.image.ImageResponseDto;
import com.resitechpro.domain.dto.response.user.UserResponseDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResidenceResponseDto implements Serializable {
    private String id;
    private String label;
    private String description;
    private String location;
    private Long surface;
    private Long longitude;
    private Long latitude;
    private UserResponseDto owner;
    private List<ImageResponseDto> images;
}