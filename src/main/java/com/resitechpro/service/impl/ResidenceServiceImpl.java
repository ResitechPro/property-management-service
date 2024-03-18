package com.resitechpro.service.impl;

import com.resitechpro.domain.entity.Image;
import com.resitechpro.domain.entity.Residence;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.repository.ResidenceRepository;
import com.resitechpro.service.ResidenceService;
import com.resitechpro.web.feign.UuidClient;
import com.resitechpro.repository.ImageRepository;
import com.resitechpro.repository.UserRepository;
import com.resitechpro.utils.ErrorMessage;
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
    public Residence attachImage(String residenceId, String imageUrl) throws ValidationException {
        Optional<Residence> optionalResidence = residenceRepository.findById(residenceId);
        if(optionalResidence.isPresent()){
            Residence residence = optionalResidence.get();
            Image imageToAttach = Image.builder()
                    .url(imageUrl)
                    .build();
            List<Image> propertyImages = residence.getImages();
            propertyImages.add(
                    imageRepository.save(imageToAttach)
            );
            residence.setImages(propertyImages);
            return residenceRepository.save(residence);
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
