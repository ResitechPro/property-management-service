package com.resitechpro.service;

import com.resitechpro.domain.entity.Building;
import com.resitechpro.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BuildingService {

    List<Building> getAllBuildings();

    Building createBuilding(Building building) throws ValidationException;

    Building attachImage(String buildingId, String imageUrl) throws ValidationException;
}
