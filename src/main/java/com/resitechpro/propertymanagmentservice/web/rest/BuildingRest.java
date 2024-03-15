package com.resitechpro.propertymanagmentservice.web.rest;

import com.resitechpro.propertymanagmentservice.cloud.aws.service.S3service;
import com.resitechpro.propertymanagmentservice.domain.dto.request.building.BuildingRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.building.BuildingResponseDto;
import com.resitechpro.propertymanagmentservice.domain.mapper.BuildingMapper;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.service.BuildingService;
import com.resitechpro.propertymanagmentservice.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/buildings")
public class BuildingRest {

    private final BuildingService buildingService;
    private final BuildingMapper buildingMapper;
    private final S3service s3Service;

    public BuildingRest(
            BuildingService buildingService,
            BuildingMapper buildingMapper,
            S3service s3Service
    ) {
        this.buildingService = buildingService;
        this.buildingMapper = buildingMapper;
        this.s3Service = s3Service;
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

    @PostMapping(path = "/{buildingId}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<Boolean>> uploadImage(@PathVariable String buildingId, @RequestParam("file") MultipartFile file) throws IOException {
        Response<Boolean> response = new Response<>();
        String imageUrl = s3Service.uploadFile(file.getOriginalFilename(), file);
        buildingService.attachImage(buildingId, imageUrl);
        response.setMessage("Image uploaded successfully");
        response.setResult(true);
        return ResponseEntity.ok(response);
    }
}
