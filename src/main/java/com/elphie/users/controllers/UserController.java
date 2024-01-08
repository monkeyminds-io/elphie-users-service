package com.elphie.users.controllers;

// =============================================================================
// File Name: controllers/UserController.java
// File Description:
// This file contains the code of the User Controller
// =============================================================================

// =============================================================================
// Imports
// =============================================================================
import java.util.Map;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.elphie.users.exceptions.ResourceNotFoundException;
import com.elphie.users.models.User;
import com.elphie.users.repositories.UserRepository;
import com.elphie.users.utiles.ResponseHandler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// =============================================================================
// Controller
// =============================================================================
@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/users/")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    // Create User
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) throws URISyntaxException {
        try {
            User createdUser = userRepository.save(user);

            return ResponseHandler.generateResponse(
                HttpStatus.OK, 
                true, 
                "Success creating User.", 
                createdUser
            );
        } catch (Exception error) {
            return ResponseHandler.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, 
                false,
                "Error creating User.",
                error.getMessage()
            );
        }
    }

    // TODO Refactor using ResponseHandler
    // Get User by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
        return ResponseEntity.ok(user);
    }

    // TODO Refactor using ResponseHandler
    // Get user by Email
    @GetMapping("/get?email={email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
        return ResponseEntity.ok(user);
    }
    
    
    // TODO Refactor using ResponseHandler
    // Update User
    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
        
        user.setFirstname(userDetails.getFirstname());
        user.setLastname(userDetails.getLastname());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setAvatar_url(userDetails.getAvatar_url());
        
        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    // TODO Refactor using ResponseHandler
    // Delete User
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
        
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
