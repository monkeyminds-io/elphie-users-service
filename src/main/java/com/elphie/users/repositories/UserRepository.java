package com.elphie.users.repositories;

// =============================================================================
// File Name: reposoitory/UserRepository.java
// File Description:
// This file contains the code of the UserRepository Interface
// =============================================================================

// =============================================================================
// Imports
// =============================================================================
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elphie.users.models.User;

// =============================================================================
// Interface
// =============================================================================
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Query find User by Email
    Optional<User> findByEmail(String email);
}
