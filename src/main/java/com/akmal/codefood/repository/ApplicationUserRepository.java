package com.akmal.codefood.repository;

import com.akmal.codefood.entity.ApplicationUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends OptionalHandlingRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByUsername(String username);
}
