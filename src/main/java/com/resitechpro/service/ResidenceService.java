package com.resitechpro.service;

import com.resitechpro.domain.entity.Residence;
import com.resitechpro.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResidenceService {

    List<Residence> getAllResidences();

    Residence createResidence(Residence residence) throws ValidationException;

    Residence attachImage(String residenceId, String imageUrl) throws ValidationException;

    List<String> getResidenceLabels();
}
