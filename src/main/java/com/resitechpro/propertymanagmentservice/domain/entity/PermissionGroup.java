package com.resitechpro.propertymanagmentservice.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "groups")
public class PermissionGroup {
    @Id
    private String id;
    @Column(unique = true)
    private String name;
    @Column(nullable = true)
    private LocalDateTime deadline;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "group_permission",
            joinColumns = @JoinColumn(
                    name = "group_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id"
            )
    )
    private Set<Permission> permissions;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return super.toString();
        }
    }
}
