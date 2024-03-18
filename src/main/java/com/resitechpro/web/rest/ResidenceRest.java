package com.resitechpro.web.rest;

import com.resitechpro.config.kafka.producer.KafkaProducer;
import com.resitechpro.domain.dto.request.residence.RequestResidenceDto;
import com.resitechpro.domain.dto.response.residence.ResidenceResponseDto;
import com.resitechpro.domain.mapper.ResidenceMapper;
import com.resitechpro.cloud.aws.service.S3service;
import com.resitechpro.domain.entity.Residence;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.service.ResidenceService;
import com.resitechpro.utils.Response;
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
    private final KafkaProducer kafkaProducer;

    public ResidenceRest(
            ResidenceService residenceService,
            ResidenceMapper residenceMapper,
            S3service s3Service,
            KafkaProducer kafkaProducer
    ) {
        this.residenceService = residenceService;
        this.residenceMapper = residenceMapper;
        this.s3Service = s3Service;
        this.kafkaProducer = kafkaProducer;
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
        Residence createdResidence = residenceService.createResidence(residenceMapper.toResidence(requestResidenceDto));
        response.setResult(
                residenceMapper.toDto(createdResidence)
        );
        response.setMessage("Residence created successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/{residenceId}/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Response<Boolean>> uploadResidenceImage(@PathVariable String residenceId, @RequestParam("file") MultipartFile file) throws IOException,ValidationException {
        Response<Boolean> response = new Response<>();
        String imageUrl = s3Service.uploadFile(file.getOriginalFilename(), file);
        Residence residence = residenceService.attachImage(residenceId, imageUrl);
        kafkaProducer.send("residences", residence);
        response.setMessage("Image uploaded successfully");
        response.setResult(true);
        return ResponseEntity.ok(response);
    }
}
