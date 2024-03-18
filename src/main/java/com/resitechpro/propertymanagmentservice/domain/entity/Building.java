package com.resitechpro.propertymanagmentservice.domain.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buildings")
public class Building {
    @Id
    private String id;
    private String label;
    private String description;
    private Integer numberFloors;

    @ManyToOne
    @JoinColumn(name = "residence_id")
    private Residence residence;

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