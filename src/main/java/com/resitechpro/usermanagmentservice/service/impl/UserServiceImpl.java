package com.resitechpro.usermanagmentservice.service.impl;


import com.resitechpro.usermanagmentservice.repository.UserRepository;
import com.resitechpro.usermanagmentservice.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

}
