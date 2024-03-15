package com.resitechpro.propertymanagmentservice.web.rest;

import com.resitechpro.propertymanagmentservice.cloud.aws.service.S3service;
import com.resitechpro.propertymanagmentservice.domain.dto.request.residence.RequestResidenceDto;
import com.resitechpro.propertymanagmentservice.domain.dto.response.residence.ResidenceResponseDto;
import com.resitechpro.propertymanagmentservice.domain.mapper.ResidenceMapper;
import com.resitechpro.propertymanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.propertymanagmentservice.service.ResidenceService;
import com.resitechpro.propertymanagmentservice.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/residences")
public class ResidenceRest {
    private final ResidenceService residenceService;
    private final ResidenceMapper residenceMapper;
    private final S3service s3Service;

    public ResidenceRest(
            ResidenceService residenceService,
            ResidenceMapper residenceMapper,
            S3service s3Service
    ) {
        this.residenceService = residenceService;
        this.residenceMapper = residenceMapper;
        this.s3Service = s3Service;
    }
    @GetMapping
    public ResponseEntity<Response<List<ResidenceResponseDto>>> getAllResidences() {
        Response<List<ResidenceResponseDto>> response = new Response<>();
        response.setResult(
                residenceService.getAllResidences().stream()
                        .map(residenceMapper::toDto)
                        .toList()
        );
        response.setMessage("Residences retrieved successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<ResidenceResponseDto>> createResidence(@Valid @RequestBody RequestResidenceDto requestResidenceDto) throws ValidationException {
        Response<ResidenceResponseDto> response = new Response<>();
        response.setResult(
                residenceMapper.toDto(
                        residenceService.createResidence(residenceMapper.toResidence(requestResidenceDto))
                )
        );
        response.setMessage("Residence created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{residenceId}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<Boolean>> uploadResidenceImage(@PathVariable String residenceId, @RequestParam("file") MultipartFile file) throws IOException {
        Response<Boolean> response = new Response<>();
        String imageUrl = s3Service.uploadFile(file.getOriginalFilename(), file);
        residenceService.attachImage(residenceId, imageUrl);
        response.setMessage("Image uploaded successfully");
        response.setResult(true);
        return ResponseEntity.ok(response);
    }
}
