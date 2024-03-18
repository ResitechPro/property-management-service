package com.resitechpro.web.rest;


import com.resitechpro.domain.dto.response.user.UserResponseDto;
import com.resitechpro.domain.entity.Tenant;
import com.resitechpro.domain.entity.User;
import com.resitechpro.domain.mapper.TenantMapper;
import com.resitechpro.domain.mapper.UserMapper;
import com.resitechpro.domain.dto.request.tenant.TenantCreationRequestDto;
import com.resitechpro.exception.customexceptions.ValidationException;
import com.resitechpro.service.TenantService;
import com.resitechpro.service.UserService;
import com.resitechpro.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pms/tenants")
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
        User tenantOwner = userService.createUserWithOwnerRole(userMapper.creationTenantDtoToUser(tenantCreationRequestDto));
        response.setMessage("Tenant is created");
        response.setResult(
                userMapper.toDto(tenantOwner)
        );
        return ResponseEntity.ok(response);
    }
}
