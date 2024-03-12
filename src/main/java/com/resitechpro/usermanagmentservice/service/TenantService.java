package com.resitechpro.usermanagmentservice.service;

import com.resitechpro.usermanagmentservice.domain.entity.Tenant;
import com.resitechpro.usermanagmentservice.exception.customexceptions.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public interface TenantService {
    Boolean checkAvailableTenant(String tenantId);

    Tenant checkAndPrepareTenantCreation(Tenant tenant) throws ValidationException;
    void createTenant(Tenant tenant, String tenantId);
    Optional<Tenant> getTenantByOrganizationName(String organizationName);
    Optional<Tenant> getTenantByPersonalEmail(String personalEmail);
}
