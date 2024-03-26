package com.resitechpro.service.impl;

import com.resitechpro.domain.dto.response.feign.UuidResponseDto;
import com.resitechpro.domain.entity.Residence;
import com.resitechpro.domain.entity.User;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.repository.ResidenceRepository;
import com.resitechpro.repository.UserRepository;
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
class ResidenceServiceImplTest {


    @Mock
    private ResidenceRepository residenceRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private UuidClient uuidClient;

    @InjectMocks
    private ResidenceServiceImpl residenceService;

    private List<Residence> residences;

    @BeforeEach
    void setUp() {
        residences = List.of(
                Residence.builder().label("Residence 1").build(),
                Residence.builder()
                        .label("Residence 2")
                        .longitude(1L)
                        .latitude(1L)
                        .build(),
                Residence.builder().label("Residence 3").build()
        );
    }

    @Test
    @DisplayName("createResidence: residence with label already exists")
    void createResidence_existLabel_throwsError() {
        when(residenceRepository.findByLabel(residences.get(0).getLabel())).thenReturn(Optional.of(residences.get(0)));
        ValidationException exception = assertThrows(ValidationException.class, () -> residenceService.createResidence(residences.get(0)));
        assertEquals("Residence with label already exists", exception.getErrors().get(0).getMessage());
    }

    @Test
    @DisplayName("createResidence: residence with longitude and latitude already exists")
    void createResidence_existLongitudeLatitude_throwsError() {
        when(residenceRepository.findByLongitudeAndLatitude(residences.get(1).getLongitude(), residences.get(1).getLatitude()))
                .thenReturn(Optional.of(residences.get(1)));
        ValidationException exception = assertThrows(ValidationException.class, () -> residenceService.createResidence(residences.get(1)));
        assertEquals("Residence with longitude and latitude already exists", exception.getErrors().get(0).getMessage());
    }

    @Test
    @DisplayName("createResidence: residence with label and longitude and latitude does exist")
    void createResidence_existLabelAndLongitudeAndLatitude_throwsError() {
        when(residenceRepository.findByLabel(residences.get(1).getLabel())).thenReturn(Optional.of(residences.get(1)));
        when(residenceRepository.findByLongitudeAndLatitude(residences.get(1).getLongitude(), residences.get(1).getLatitude()))
                .thenReturn(Optional.of(residences.get(1)));
        ValidationException exception = assertThrows(ValidationException.class, () -> residenceService.createResidence(residences.get(1)));
        assertEquals(exception.getErrors().size(), 2);
    }

    @Test
    @DisplayName("createResidence: residence with label and longitude and latitude does not exist")
    void createResidence_validData_returnsResidence() throws ValidationException {
        when(residenceRepository.findByLabel(residences.get(2).getLabel())).thenReturn(Optional.empty());
        when(residenceRepository.findByLongitudeAndLatitude(residences.get(2).getLongitude(), residences.get(2).getLatitude()))
                .thenReturn(Optional.empty());
        when(uuidClient.generateUuid()).thenReturn(UuidResponseDto.builder().uuid("1234").build());
        when(residenceRepository.save(residences.get(2))).thenReturn(residences.get(2));
        when(userRepository.findByRoles_name()).thenReturn(Optional.of(User.builder().build()));
        Residence residence = residenceService.createResidence(residences.get(2));
        assertEquals(residence.getLabel(), residences.get(2).getLabel());
    }

}