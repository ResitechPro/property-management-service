package com.resitechpro.web.rest;

import com.resitechpro.cloud.aws.service.S3service;
import com.resitechpro.config.kafka.producer.KafkaProducer;
import com.resitechpro.domain.dto.request.property.PropertyRequestDto;
import com.resitechpro.domain.dto.response.property.OnlyPropertyResponseDto;
import com.resitechpro.domain.dto.response.property.PropertyResponseDto;
import com.resitechpro.domain.entity.Property;
import com.resitechpro.domain.mapper.PropertyMapper;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.service.PropertyService;
import com.resitechpro.utils.Response;
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
    private final KafkaProducer kafkaProducer;

    public PropertyRest(
            PropertyService propertyService,
            PropertyMapper propertyMapper,
            S3service s3Service,
            KafkaProducer kafkaProducer
    ) {
        this.propertyService = propertyService;
        this.propertyMapper = propertyMapper;
        this.s3Service = s3Service;
        this.kafkaProducer = kafkaProducer;
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
        Property createdProperty = propertyService.createProperty(propertyMapper.toProperty(propertyRequestDto));
        response.setResult(
                propertyMapper.toDto(createdProperty)
        );
        response.setMessage("Property created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{propertyId}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<Boolean>> uploadImage(@PathVariable String propertyId, @RequestParam("file") MultipartFile file) throws IOException,ValidationException {
        Response<Boolean> response = new Response<>();
        String imageUrl = s3Service.uploadFile(file.getOriginalFilename(), file);
        Property property = propertyService.attachImage(propertyId, imageUrl);
        kafkaProducer.send("properties",property);
        response.setMessage("Image uploaded successfully");
        response.setResult(true);
        return ResponseEntity.ok(response);
    }
}
