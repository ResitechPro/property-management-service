package com.resitechpro.propertymanagmentservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "residences")
public class Residence {
    @Id
    private String id;
    private String label;
    private String description;
    private String location;
    private Long surface;
    private Long longitude;
    private Long latitude;

}