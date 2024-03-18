package com.resitechpro.service.impl;

import com.resitechpro.domain.entity.Image;
import com.resitechpro.domain.entity.Building;
import com.resitechpro.domain.entity.Property;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.repository.ImageRepository;
import com.resitechpro.web.feign.UuidClient;
import com.resitechpro.repository.BuildingRepository;
import com.resitechpro.repository.PropertyRepository;
import com.resitechpro.service.PropertyService;
import com.resitechpro.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final BuildingRepository buildingRepository;
    private final UuidClient uuidClient;
    private final ImageRepository imageRepository;

    public PropertyServiceImpl
    (
        PropertyRepository propertyRepository,
        BuildingRepository buildingRepository,
        UuidClient uuidClient,
        ImageRepository imageRepository) {
        this.propertyRepository = propertyRepository;
        this.buildingRepository = buildingRepository;
        this.uuidClient = uuidClient;
        this.imageRepository = imageRepository;
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

    @Override
    public Property attachImage(String propertyId, String imageUrl) throws ValidationException {
        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if(optionalProperty.isPresent()){
            Property property = optionalProperty.get();
            Image imageToAttach = Image.builder()
                    .url(imageUrl)
                    .build();
            List<Image> propertyImages = property.getImages();
            propertyImages.add(
                    imageRepository.save(imageToAttach)
            );
            property.setImages(propertyImages);
            return propertyRepository.save(property);
        }else throw new ValidationException(
                List.of(
                        ErrorMessage.builder()
                                .field("property Id")
                                .message("Property with id does not exists")
                                .build()
                )
        );
    }
}
