package com.resitechpro.usermanagmentservice.web.rest;


import com.resitechpro.usermanagmentservice.domain.dto.request.tenant.TenantCreationRequestDto;
import com.resitechpro.usermanagmentservice.domain.dto.response.user.UserResponseDto;
import com.resitechpro.usermanagmentservice.domain.entity.Tenant;
import com.resitechpro.usermanagmentservice.domain.entity.User;
import com.resitechpro.usermanagmentservice.domain.mapper.TenantMapper;
import com.resitechpro.usermanagmentservice.domain.mapper.UserMapper;
import com.resitechpro.usermanagmentservice.exception.customexceptions.ValidationException;
import com.resitechpro.usermanagmentservice.service.TenantService;
import com.resitechpro.usermanagmentservice.service.UserService;
import com.resitechpro.usermanagmentservice.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantRest {
    private final TenantService tenantService;
    private final TenantMapper tenantMapper;
    private final UserService userService;
    private final UserMapper userMapper;
    public TenantRest(
            TenantService tenantService,
            TenantMapper tenantMapper,
            UserService userService,
            UserMapper userMapper
    ) {
        this.tenantService = tenantService;
        this.tenantMapper = tenantMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @GetMapping("/check-tenant/{tenantId}")
    public ResponseEntity<Response<Boolean>> checkTenant(@PathVariable String tenantId) {
        Response<Boolean> response = new Response<>();
        Boolean isTenantAvailable = tenantService.checkAvailableTenant(tenantId);
        if(Boolean.TRUE.equals(isTenantAvailable)) response.setMessage("Tenant is available");
        else response.setMessage("Tenant is not available");
        response.setResult(
                isTenantAvailable
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<UserResponseDto>> createTenant(@RequestBody TenantCreationRequestDto tenantCreationRequestDto) throws ValidationException {
        Response<UserResponseDto> response = new Response<>();
        Tenant preparedTenant = tenantService.checkAndPrepareTenantCreation(tenantMapper.toTenant(tenantCreationRequestDto));
        tenantService.createTenant(preparedTenant, preparedTenant.getTenantId());
        User tenantOwner = userService.createUserWithOwnerRole(userMapper.tenantToUser(preparedTenant));
        response.setMessage("Tenant is created");
        response.setResult(
                userMapper.toDto(tenantOwner)
        );
        return ResponseEntity.ok(response);
    }
}
