package com.resitechpro.usermanagmentservice.service.impl;


import com.resitechpro.usermanagmentservice.domain.entity.User;
import com.resitechpro.usermanagmentservice.repository.PermissionRepository;
import com.resitechpro.usermanagmentservice.repository.RoleRepository;
import com.resitechpro.usermanagmentservice.repository.UserRepository;
import com.resitechpro.usermanagmentservice.service.UserService;
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
