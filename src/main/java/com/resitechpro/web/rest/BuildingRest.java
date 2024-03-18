package com.resitechpro.web.rest;

import com.resitechpro.cloud.aws.service.S3service;
import com.resitechpro.config.kafka.producer.KafkaProducer;
import com.resitechpro.domain.dto.request.building.BuildingRequestDto;
import com.resitechpro.domain.dto.response.building.BuildingResponseDto;
import com.resitechpro.domain.entity.Building;
import com.resitechpro.domain.mapper.BuildingMapper;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.service.BuildingService;
import com.resitechpro.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pms/buildings")
public class BuildingRest {

    private final BuildingService buildingService;
    private final BuildingMapper buildingMapper;
    private final S3service s3Service;
    private final KafkaProducer kafkaProducer;

    public BuildingRest(
            BuildingService buildingService,
            BuildingMapper buildingMapper,
            S3service s3Service,
            KafkaProducer kafkaProducer
    ) {
        this.buildingService = buildingService;
        this.buildingMapper = buildingMapper;
        this.s3Service = s3Service;
        this.kafkaProducer = kafkaProducer;
    }
    @GetMapping
    public ResponseEntity<Response<List<BuildingResponseDto>>> getAllBuildings() {
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
    public ResponseEntity<Response<BuildingResponseDto>> createBuilding(@Valid @RequestBody BuildingRequestDto buildingRequestDto) throws ValidationException {
        Response<BuildingResponseDto> response = new Response<>();
        Building createdBuilding = buildingService.createBuilding(buildingMapper.toBuilding(buildingRequestDto));
        response.setResult(
                buildingMapper.toDto(createdBuilding)
        );
        response.setMessage("Residence created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{buildingId}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<Boolean>> uploadImage(@PathVariable String buildingId, @RequestParam("file") MultipartFile file) throws IOException, ValidationException {
        Response<Boolean> response = new Response<>();
        String imageUrl = s3Service.uploadFile(file.getOriginalFilename(), file);
        Building building = buildingService.attachImage(buildingId, imageUrl);
        kafkaProducer.send("buildings", building);
        response.setMessage("Image uploaded successfully");
        response.setResult(true);
        return ResponseEntity.ok(response);
    }
}
