package com.resitechpro.service.impl;

import com.resitechpro.domain.entity.Building;
import com.resitechpro.domain.entity.Image;
import com.resitechpro.domain.entity.Residence;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.repository.BuildingRepository;
import com.resitechpro.repository.ImageRepository;
import com.resitechpro.repository.ResidenceRepository;
import com.resitechpro.utils.ErrorMessage;
import com.resitechpro.web.feign.UuidClient;
import com.resitechpro.service.BuildingService;
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
        ImageRepository imageRepository
    ) {
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
    public Building attachImage(String buildingId, String imageUrl) throws ValidationException {
        Optional<Building> optionalBuilding = buildingRepository.findById(buildingId);
        if(optionalBuilding.isPresent()){
            Building building = optionalBuilding.get();
            Image imageToAttach = Image.builder()
                    .url(imageUrl)
                    .build();
            List<Image> propertyImages = building.getImages();
            propertyImages.add(
                    imageRepository.save(imageToAttach)
            );
            building.setImages(propertyImages);
            return buildingRepository.save(building);
        }else throw new ValidationException(
                List.of(
                        ErrorMessage.builder()
                                .field("Residence id")
                                .message("Residence with does not exist")
                                .build()
                )
        );
    }
}
