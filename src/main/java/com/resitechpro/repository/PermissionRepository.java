package com.resitechpro.repository;

import com.resitechpro.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    Optional<Permission> findBySubjectAndAction(String subject, String action);

}