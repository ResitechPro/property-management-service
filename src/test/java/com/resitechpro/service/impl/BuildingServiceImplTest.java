package com.resitechpro.service.impl;

import com.resitechpro.domain.dto.response.feign.UuidResponseDto;
import com.resitechpro.domain.entity.Building;
import com.resitechpro.domain.entity.Residence;
import com.resitechpro.domain.entity.User;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.repository.BuildingRepository;
import com.resitechpro.repository.ResidenceRepository;
import com.resitechpro.web.feign.UuidClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BuildingServiceImplTest {

    @Mock
    private BuildingRepository buildingRepository;

    @Mock
    private ResidenceRepository residenceRepository;

    @Mock
    private UuidClient uuidClient;

    @InjectMocks
    private BuildingServiceImpl buildingService;

    private List<Building> buildings;

    @BeforeEach
    void setUp() {
        buildings = List.of(
            Building.builder().label("Building 1").build(),
            Building.builder().label("Building 2").residence(Residence.builder().label("Residence 1").build()).build(),
            Building.builder().label("Building 3")
                    .residence(Residence.builder().label("Residence 2").build())
                    .build()
        );
    }

    @Test
    @DisplayName("createBuilding: building with building label and residence label already exists")
    void createBuilding_existBuildingLabelAndExistingResidenceLabel_throwsError() {
        when(buildingRepository.findByLabel(buildings.get(1).getLabel())).thenReturn(Optional.of(buildings.get(1)));
        ValidationException exception = assertThrows(ValidationException.class, () -> buildingService.createBuilding(buildings.get(1)));
        assertEquals("Building with label already exists", exception.getErrors().get(0).getMessage());
        assertEquals("Residence with label does not exists", exception.getErrors().get(1).getMessage());
        assertEquals(2, exception.getErrors().size());
    }

    @Test
    @DisplayName("createBuilding: building with label and residence label are valid")
    void createBuilding_validData_returnsResidence() throws ValidationException {
        when(buildingRepository.findByLabel(buildings.get(2).getLabel())).thenReturn(Optional.empty());
        when(residenceRepository.findByLabel(buildings.get(2).getResidence().getLabel()))
                .thenReturn(Optional.of(buildings.get(2).getResidence()));
        when(uuidClient.generateUuid()).thenReturn(UuidResponseDto.builder().uuid("1234").build());
        when(buildingRepository.save(buildings.get(2))).thenReturn(buildings.get(2));
        Building building = buildingService.createBuilding(buildings.get(2));
        assertEquals(building.getLabel(), buildings.get(2).getLabel());
    }
}