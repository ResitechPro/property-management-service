package com.resitechpro.propertymanagmentservice.web.rest;

import com.resitechpro.propertymanagmentservice.domain.dto.request.building.BuildingRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.building.BuildingResponseDto;
import com.resitechpro.propertymanagmentservice.domain.mapper.BuildingMapper;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.service.BuildingService;
import com.resitechpro.propertymanagmentservice.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/buildings")
public class BuildingRest {

    private final BuildingService buildingService;
    private final BuildingMapper buildingMapper;

    public BuildingRest(
            BuildingService buildingService,
            BuildingMapper buildingMapper
    ) {
        this.buildingService = buildingService;
        this.buildingMapper = buildingMapper;
    }
    @GetMapping
    public ResponseEntity<Response<List<BuildingResponseDto>>> getAllResidences() {
        Response<List<BuildingResponseDto>> response = new Response<>();
        response.setResult(
                buildingService.getAllBuildings().stream()
                        .map(buildingMapper::toDto)
                        .toList()
        );
        response.setMessage("Buildings retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<BuildingResponseDto>> createResidence(@Valid @RequestBody BuildingRequestDto buildingRequestDto) throws ValidationException {
        Response<BuildingResponseDto> response = new Response<>();
        response.setResult(
                buildingMapper.toDto(
                        buildingService.createBuilding(buildingMapper.toBuilding(buildingRequestDto))
                )
        );
        response.setMessage("Residence created successfully");
        return ResponseEntity.ok(response);
    }
}
