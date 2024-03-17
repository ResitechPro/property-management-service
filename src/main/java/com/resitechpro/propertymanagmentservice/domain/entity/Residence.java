package com.resitechpro.propertymanagmentservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "residences")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Residence {
    @Id
    private String id;
    private String label;
    private String description;
    private String location;
    private Long surface;
    private Long longitude;
    private Long latitude;

    @OneToMany
    private List<Image> images;

    @ManyToOne
    private User owner;

}