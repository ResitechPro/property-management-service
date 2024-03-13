package com.resitechpro.propertymanagmentservice.service.impl;

import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.repository.ResidenceRepository;
import com.resitechpro.propertymanagmentservice.service.ResidenceService;
import com.resitechpro.propertymanagmentservice.utils.ErrorMessage;
import com.resitechpro.propertymanagmentservice.web.feign.UuidClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResidenceServiceImpl implements ResidenceService {

    private final UuidClient uuidClient;
    private final ResidenceRepository residenceRepository;

    public ResidenceServiceImpl(UuidClient uuidClient,
                                ResidenceRepository residenceRepository) {
        this.uuidClient = uuidClient;
        this.residenceRepository = residenceRepository;
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
        String residenceId = uuidClient.generateUuid().getUuid();
        residence.setId(residenceId);
        return residenceRepository.save(residence);
    }
}
