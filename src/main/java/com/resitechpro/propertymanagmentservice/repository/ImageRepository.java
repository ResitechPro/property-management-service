package com.resitechpro.propertymanagmentservice.repository;

import com.resitechpro.propertymanagmentservice.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}