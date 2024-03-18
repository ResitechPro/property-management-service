package com.resitechpro.repository;

import com.resitechpro.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("select u from User u where u.personalEmail = :personalEmail")
    Optional<User> findByPersonalEmail(String personalEmail);

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.organizationName = :organizationName")
    Optional<User> findByOrganizationName(String organizationName);

    @Query("select u from User u inner join u.roles roles where roles.name = 'OWNER'")
    Optional<User> findByRoles_name();
}
