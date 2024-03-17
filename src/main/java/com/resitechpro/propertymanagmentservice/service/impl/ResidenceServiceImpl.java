package com.resitechpro.propertymanagmentservice.service.impl;

import com.resitechpro.propertymanagmentservice.domain.entity.Image;
import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import com.resitechpro.propertymanagmentservice.domain.entity.User;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.repository.ImageRepository;
import com.resitechpro.propertymanagmentservice.repository.ResidenceRepository;
import com.resitechpro.propertymanagmentservice.repository.UserRepository;
import com.resitechpro.propertymanagmentservice.service.ResidenceService;
import com.resitechpro.propertymanagmentservice.utils.ErrorMessage;
import com.resitechpro.propertymanagmentservice.web.feign.UuidClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ResidenceServiceImpl implements ResidenceService {

    private final UuidClient uuidClient;
    private final ResidenceRepository residenceRepository;
    private final ImageRepository imageRepository;

    private final UserRepository userRepository;

    public ResidenceServiceImpl(
            UuidClient uuidClient,
            ResidenceRepository residenceRepository,
            ImageRepository imageRepository,
            UserRepository userRepository
    ) {
        this.uuidClient = uuidClient;
        this.residenceRepository = residenceRepository;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<Residence> getAllResidences() {
        return residenceRepository.findAll();
    }

    @Override
    public Residence createResidence(Residence residence) throws ValidationException {
        List<ErrorMessage> errors = new ArrayList<>();
        if(residenceRepository.findByLabel(residence.getLabel()).isPresent())
            errors.add(
                    ErrorMessage.builder()
                            .field("label")
                            .message("Residence with label already exists")
                            .build()
            );
        if(residenceRepository.findByLongitudeAndLatitude(residence.getLongitude(),residence.getLatitude()).isPresent())
            errors.add(
                    ErrorMessage.builder()
                            .field("Longitude and Latitude")
                            .message("Residence with longitude and latitude already exists")
                            .build()
            );
        if(!errors.isEmpty()) throw new ValidationException(errors);
        userRepository.findByRoles_name().ifPresent(residence::setOwner);
        String residenceId = uuidClient.generateUuid().getUuid();
        residence.setId(residenceId);
        return residenceRepository.save(residence);
    }

    @Override
    public void attachImage(String residenceId, String imageUrl) {
        Optional<Residence> optionalResidence = residenceRepository.findById(residenceId);
        optionalResidence.ifPresent(residence -> {
            Image imageToAttach = Image.builder()
                    .url(imageUrl)
                    .build();
            List<Image> propertyImages = residence.getImages();
            propertyImages.add(
                    imageRepository.save(imageToAttach)
            );
            residence.setImages(propertyImages);
            residenceRepository.save(residence);
        });
    }
}
