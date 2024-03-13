package com.resitechpro.propertymanagmentservice.domain.dto.request.tenant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TenantCreationRequestDto {

    @NotBlank(message = "Id is required")
    @NotNull(message = "Id is required")
    private String userId;

    @NotBlank(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Personal email is required")
    @NotNull(message = "Personal email is required")
    @Email(message = "Personal email is not valid")
    private String personalEmail;

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Organization Name is required")
    @NotNull(message = "Organization Name is required")
    private String organizationName;

    @NotBlank(message = "Organization id is required")
    @NotNull(message = "Organization id is required")
    private String organizationId;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Phone is required")
    @NotNull(message = "Phone is required")
    private String phone;
}
