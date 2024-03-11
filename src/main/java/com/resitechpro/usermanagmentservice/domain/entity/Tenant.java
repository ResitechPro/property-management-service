package com.resitechpro.usermanagmentservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tenants",schema = "public")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String organizationName;
    @Column(unique = true)
    private String tenantId;
    @Column(unique = true)
    private String personalEmail;
    private String email;
    private LocalDateTime accessionDate;
    private Boolean isActive;
}
