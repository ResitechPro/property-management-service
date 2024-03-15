package com.resitechpro.propertymanagmentservice.service;

import com.resitechpro.propertymanagmentservice.domain.entity.Property;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PropertyService {

    List<Property> getAllProperties();

    List<Property> getPropertiesByBuildingLabel(String buildingLabel);

    Property createProperty(Property property) throws ValidationException;

    void attachImage(String propertyId, String imageUrl);
}
