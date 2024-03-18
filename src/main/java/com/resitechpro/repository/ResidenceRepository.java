package com.resitechpro.repository;

import com.resitechpro.domain.entity.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResidenceRepository extends JpaRepository<Residence, String> {
    @Query("SELECT r FROM Residence r WHERE r.label = :label")
    Optional<Residence> findByLabel(String label);
    @Query("SELECT r FROM Residence r WHERE r.longitude = :longitude AND r.latitude = :latitude")
    Optional<Object> findByLongitudeAndLatitude(Long longitude, Long latitude);
}
