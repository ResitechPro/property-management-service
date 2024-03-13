package com.resitechpro.propertymanagmentservice.repository;

import com.resitechpro.propertymanagmentservice.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findBySubjectAndAction(String subject, String action);

}