package com.resitechpro.service;

import com.resitechpro.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUserWithOwnerRole(User user);
}
