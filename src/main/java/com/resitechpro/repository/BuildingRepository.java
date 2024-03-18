package com.resitechpro.repository;

import com.resitechpro.domain.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuildingRepository extends JpaRepository<Building, String> {
    @Query("SELECT b FROM Building b WHERE b.label = :label")
    Optional<Building> findByLabel(String label);
}