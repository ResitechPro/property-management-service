package com.resitechpro.propertymanagmentservice.domain.entity;

import com.resitechpro.propertymanagmentservice.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "properties")
public class Property {
    @Id
    private String id;
    private String label;
    private String description;
    private PropertyType propertyType;
    private Long surface;
    private Integer numberRooms;
    private Integer numberBathrooms;
    private Integer numberBedrooms;
    private Integer numberWindows;
    private String floorNumber;
    private String rentValue;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;
}