package com.resitechpro.propertymanagmentservice.web.rest;

import com.resitechpro.propertymanagmentservice.cloud.aws.service.S3service;
import com.resitechpro.propertymanagmentservice.domain.dto.request.property.PropertyRequestDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.property.OnlyPropertyResponseDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.property.PropertyResponseDto;
import com.resitechpro.propertymanagmentservice.domain.mapper.PropertyMapper;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.service.PropertyService;
import com.resitechpro.propertymanagmentservice.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyRest {

    private final PropertyService propertyService;
    private final PropertyMapper propertyMapper;
    private final S3service s3Service;

    public PropertyRest(
            PropertyService propertyService,
            PropertyMapper propertyMapper,
            S3service s3Service
    ) {
        this.propertyService = propertyService;
        this.propertyMapper = propertyMapper;
        this.s3Service = s3Service;
    }
    @GetMapping
    public ResponseEntity<Response<List<PropertyResponseDto>>> getAllProperties() {
        Response<List<PropertyResponseDto>> response = new Response<>();
        response.setResult(
                propertyService.getAllProperties().stream()
                        .map(propertyMapper::toDto)
                        .toList()
        );
        response.setMessage("Properties retrieved successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/building/{buildingLabel}")
    public ResponseEntity<Response<List<OnlyPropertyResponseDto>>> getPropertiesByBuilding(@PathVariable("buildingLabel") String buildingLabel) {
        Response<List<OnlyPropertyResponseDto>> response = new Response<>();
        response.setResult(
                propertyService.getPropertiesByBuildingLabel(buildingLabel).stream()
                        .map(propertyMapper::toOnlyPropertyDto)
                        .toList()
        );
        response.setMessage("Properties by building retrieved successfully");
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<Response<PropertyResponseDto>> createProperty(@Valid @RequestBody PropertyRequestDto propertyRequestDto) throws ValidationException {
        Response<PropertyResponseDto> response = new Response<>();
        response.setResult(
                propertyMapper.toDto(
                        propertyService.createProperty(propertyMapper.toProperty(propertyRequestDto))
                )
        );
        response.setMessage("Property created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{propertyId}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<Boolean>> uploadImage(@PathVariable String propertyId, @RequestParam("file") MultipartFile file) throws IOException {
        Response<Boolean> response = new Response<>();
        String imageUrl = s3Service.uploadFile(file.getOriginalFilename(), file);
        propertyService.attachImage(propertyId, imageUrl);
        response.setMessage("Image uploaded successfully");
        response.setResult(true);
        return ResponseEntity.ok(response);
    }
}
