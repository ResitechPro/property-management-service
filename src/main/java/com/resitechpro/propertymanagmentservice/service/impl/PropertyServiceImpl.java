package com.resitechpro.propertymanagmentservice.service.impl;

import com.resitechpro.propertymanagmentservice.domain.entity.Building;
import com.resitechpro.propertymanagmentservice.domain.entity.Property;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.repository.BuildingRepository;
import com.resitechpro.propertymanagmentservice.repository.PropertyRepository;
import com.resitechpro.propertymanagmentservice.service.PropertyService;
import com.resitechpro.propertymanagmentservice.utils.ErrorMessage;
import com.resitechpro.propertymanagmentservice.web.feign.UuidClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final BuildingRepository buildingRepository;
    private final UuidClient uuidClient;
    public PropertyServiceImpl
    (
        PropertyRepository propertyRepository,
        BuildingRepository buildingRepository,
        UuidClient uuidClient
    ) {
        this.propertyRepository = propertyRepository;
        this.buildingRepository = buildingRepository;
        this.uuidClient = uuidClient;
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Override
    public List<Property> getPropertiesByBuildingLabel(String buildingLabel) {
        return propertyRepository.findByBuildingLabel(buildingLabel);
    }

    @Override
    public Property createProperty(Property property) throws ValidationException {
        List<ErrorMessage> errors = new ArrayList<>();
        if(buildingRepository.findByLabel(property.getLabel()).isPresent())
            errors.add(
                    ErrorMessage.builder()
                            .field("label")
                            .message("Property with label already exists")
                            .build()
            );
        Optional<Building> optionalBuilding = buildingRepository.findByLabel(property.getBuilding().getLabel());
        if(optionalBuilding.isEmpty())
            errors.add(
                    ErrorMessage.builder()
                            .field("Building label")
                            .message("Building with label does not exists")
                            .build()
            );
        else property.setBuilding(optionalBuilding.get());
        if(!errors.isEmpty()) throw new ValidationException(errors);
        String propertyId = uuidClient.generateUuid().getUuid();
        property.setId(propertyId);
        return propertyRepository.save(property);
    }
}