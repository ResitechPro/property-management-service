package com.resitechpro.propertymanagmentservice.service;

import com.resitechpro.propertymanagmentservice.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUserWithOwnerRole(User user);
}
