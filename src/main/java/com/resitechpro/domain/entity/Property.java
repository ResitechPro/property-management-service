package com.resitechpro.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resitechpro.domain.enums.PropertyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany
    private List<Image> images;

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
