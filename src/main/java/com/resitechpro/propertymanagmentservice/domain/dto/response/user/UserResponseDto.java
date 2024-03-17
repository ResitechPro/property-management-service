package com.resitechpro.propertymanagmentservice.domain.dto.response.user;

import com.resitechpro.propertymanagmentservice.domain.dto.response.image.ImageResponseDto;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto implements Serializable {
    private String id;
    private String personalEmail;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private ImageResponseDto image;
    private Set<String> rolePermissions;
    private Set<String> permissionGroupPermissions;
}
