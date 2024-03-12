package com.resitechpro.usermanagmentservice.service.impl;

import com.resitechpro.usermanagmentservice.domain.entity.Tenant;
import com.resitechpro.usermanagmentservice.domain.entity.User;
import com.resitechpro.usermanagmentservice.domain.mapper.UserMapper;
import com.resitechpro.usermanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.usermanagmentservice.repository.PermissionRepository;
import com.resitechpro.usermanagmentservice.repository.RoleRepository;
import com.resitechpro.usermanagmentservice.repository.TenantRepository;
import com.resitechpro.usermanagmentservice.repository.UserRepository;
import com.resitechpro.usermanagmentservice.service.TenantService;
import com.resitechpro.usermanagmentservice.utils.ErrorMessage;
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
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;

    public TenantServiceImpl
    (
        DataSource dataSource,
        TenantRepository tenantRepository,
        UserRepository userRepository,
        RoleRepository roleRepository,
        PermissionRepository permissionRepository,
        UserMapper userMapper
    ) {
        this.dataSource = dataSource;
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userMapper = userMapper;
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
        String email = tenant.getLastName() + "-" +
                UUID.randomUUID().toString().substring(0,8) + "@" +
                tenant.getTenantId() + ".com";
        tenant.setEmail(email);
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
