package com.resitechpro.propertymanagmentservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buildings")
public class Building {
    @Id
    private Long id;
    private String label;
    private String description;
    private Integer numberFloors;

    @ManyToOne
    @JoinColumn(name = "residence_id")
    private Residence residence;

}