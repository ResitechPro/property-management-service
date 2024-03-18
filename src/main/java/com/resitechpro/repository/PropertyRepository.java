package com.resitechpro.repository;

import com.resitechpro.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PropertyRepository extends JpaRepository<Property, String> {
    @Query("SELECT p FROM Property p WHERE p.label = :label")
    Optional<Property> findByLabel(String label);

    List<Property> findByBuildingLabel(String buildingLabel);
}