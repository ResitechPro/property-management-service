package com.resitechpro.propertymanagmentservice.service;

import com.resitechpro.propertymanagmentservice.domain.entity.Residence;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResidenceService {

    List<Residence> getAllResidences();

    Residence createResidence(Residence residence) throws ValidationException;

    void attachImage(String residenceId, String imageUrl);
}
