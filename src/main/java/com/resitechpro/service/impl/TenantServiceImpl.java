package com.resitechpro.service.impl;

import com.resitechpro.domain.entity.Tenant;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.service.TenantService;
import com.resitechpro.repository.TenantRepository;
import com.resitechpro.utils.ErrorMessage;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class TenantServiceImpl implements TenantService {
    private final DataSource dataSource;
    private final TenantRepository tenantRepository;

    public TenantServiceImpl
    (
        DataSource dataSource,
        TenantRepository tenantRepository
    ) {
        this.dataSource = dataSource;
        this.tenantRepository = tenantRepository;
    }
    @Override
    public Boolean checkAvailableTenant(String tenantId) {
        return (! tenantRepository.checkAvailableTenant(tenantId) );
    }

    @Override
    public Tenant checkAndPrepareTenantCreation(Tenant tenant) throws ValidationException {
        List<ErrorMessage> errors = new ArrayList<>();
        if( getTenantByOrganizationName(tenant.getOrganizationName()).isPresent())
            errors.add(ErrorMessage.builder()
                    .field("organization name")
                    .message("organization name already exists")
                    .build()
            );
        if( getTenantByPersonalEmail(tenant.getPersonalEmail()).isPresent())
            errors.add(ErrorMessage.builder()
                    .field("email")
                    .message("Email already exists")
                    .build()
            );

        if( Boolean.FALSE.equals(checkAvailableTenant(tenant.getTenantId())))
            errors.add(
                    ErrorMessage.builder()
                            .field("organization id")
                            .message("Organization id already exists")
                            .build()
            );
        if(!errors.isEmpty()) throw new ValidationException(errors);
        return tenant;
    }

    @Override
    @Transactional
    public void createTenant(Tenant tenant,String tenantId) {
        //Todo: look for an alternative way to create schemas using jpa
        try(Statement statement = dataSource.getConnection().createStatement()) {
            //create schema
            statement.execute("CREATE SCHEMA " + tenantId);
            // run liquibase changelogs on the new schema
            SpringLiquibase liquibase = new SpringLiquibase();
            liquibase.setDataSource(dataSource);
            liquibase.setDefaultSchema(tenantId);
            liquibase.setChangeLog("db/changelog/tenants/db.tenant.changelog-master.yml");
            liquibase.afterPropertiesSet();
            //insert tenant
            tenant.setAccessionDate(LocalDateTime.now());
            tenant.setIsActive(true);
            tenant.setTenantId(tenantId);
            tenantRepository.save(tenant);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Tenant> getTenantByOrganizationName(String organizationName) {
        return tenantRepository.findByOrganizationName(organizationName);
    }

    @Override
    public Optional<Tenant> getTenantByPersonalEmail(String personalEmail) {
        return tenantRepository.findByPersonalEmail(personalEmail);
    }
}
