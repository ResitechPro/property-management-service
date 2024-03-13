package com.resitechpro.propertymanagmentservice.service.impl;


import com.resitechpro.propertymanagmentservice.domain.entity.User;
import com.resitechpro.propertymanagmentservice.repository.PermissionRepository;
import com.resitechpro.propertymanagmentservice.repository.RoleRepository;
import com.resitechpro.propertymanagmentservice.repository.UserRepository;
import com.resitechpro.propertymanagmentservice.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public User createUserWithOwnerRole(User user) {
        roleRepository.findByName("OWNER").ifPresent(role ->
                permissionRepository.findBySubjectAndAction("manage","all").ifPresent(permission ->{
                    role.setPermissions(Set.of(permission));
                    user.setRoles(Set.of(role));
                })
        );
        return userRepository.save(user);
    }
}
