package com.resitechpro.propertymanagmentservice.service;

import com.resitechpro.propertymanagmentservice.domain.entity.Building;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BuildingService {

    List<Building> getAllBuildings();

    Building createBuilding(Building building) throws ValidationException;
}
