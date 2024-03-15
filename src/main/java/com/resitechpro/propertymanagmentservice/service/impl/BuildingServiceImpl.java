package com.resitechpro.propertymanagmentservice.service.impl;

import com.resitechpro.propertymanagmentservice.domain.entity.Building;
import com.resitechpro.propertymanagmentservice.domain.entity.Image;
import com.resitechpro.propertymanagmentservice.domain.entity.Property;
import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.repository.BuildingRepository;
import com.resitechpro.propertymanagmentservice.repository.ImageRepository;
import com.resitechpro.propertymanagmentservice.repository.ResidenceRepository;
import com.resitechpro.propertymanagmentservice.service.BuildingService;
import com.resitechpro.propertymanagmentservice.utils.ErrorMessage;
import com.resitechpro.propertymanagmentservice.web.feign.UuidClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BuildingServiceImpl implements BuildingService {

    private final UuidClient uuidClient;
    private final BuildingRepository buildingRepository;
    private final ResidenceRepository residenceRepository;
    private final ImageRepository imageRepository;


    public BuildingServiceImpl
    (
            UuidClient uuidClient,
            BuildingRepository buildingRepository,
            ResidenceRepository residenceRepository,
            ImageRepository imageRepository) {
        this.uuidClient = uuidClient;
        this.buildingRepository = buildingRepository;
        this.residenceRepository = residenceRepository;
        this.imageRepository = imageRepository;
    }
    @Override
    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    @Override
    public Building createBuilding(Building building) throws ValidationException {
        List<ErrorMessage> errors = new ArrayList<>();
        if(buildingRepository.findByLabel(building.getLabel()).isPresent())
            errors.add(
                    ErrorMessage.builder()
                            .field("label")
                            .message("Building with label already exists")
                            .build()
            );
        Optional<Residence> optionalResidence = residenceRepository.findByLabel(building.getResidence().getLabel());
        if(optionalResidence.isEmpty())
            errors.add(
                    ErrorMessage.builder()
                            .field("Residence label")
                            .message("Residence with label does not exists")
                            .build()
            );
        else building.setResidence(optionalResidence.get());
        if(!errors.isEmpty()) throw new ValidationException(errors);
        String buildingId = uuidClient.generateUuid().getUuid();
        building.setId(buildingId);
        return buildingRepository.save(building);
    }
    @Override
    public void attachImage(String buildingId, String imageUrl) {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        optionalBuilding.ifPresent(building -> {
            Image imageToAttach = Image.builder()
                    .url(imageUrl)
                    .build();
            List<Image> propertyImages = building.getImages();
            propertyImages.add(
                    imageRepository.save(imageToAttach)
            );
            building.setImages(propertyImages);
            buildingRepository.save(building);
        });
    }
}
