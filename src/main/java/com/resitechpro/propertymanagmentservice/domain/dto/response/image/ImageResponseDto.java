package com.resitechpro.propertymanagmentservice.domain.dto.response.image;

import com.resitechpro.propertymanagmentservice.domain.entity.Image;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link Image}
 */
@AllArgsConstructor
@Getter
@Builder
@Setter
@NoArgsConstructor
public final class ImageResponseDto implements Serializable {
    private String url;
}