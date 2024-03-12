package com.resitechpro.usermanagmentservice.service;

import com.resitechpro.usermanagmentservice.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUserWithOwnerRole(User user);
}
