package com.elphie.users.repositories;

// =============================================================================
// File Name: reposoitories/UserRepository.java
// File Description:
// This file contains the code of the UserRepository Interface that
// handles the queries to the users table in the DB
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
public interface IUserRepository extends JpaRepository<User, Long> {
    
    // Query find User by Email
    Optional<User> findByEmail(String email);
}
