package com.elphie.users.controllers;

// =============================================================================
// File Name: controllers/UserController.java
// File Description:
// This file contains the code of the User Controller that handles
// the Http requests, manipulates the data and returns the responses.
// =============================================================================

// =============================================================================
// Controller Imports
// =============================================================================
import java.util.Map;
import java.sql.Timestamp;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elphie.users.exceptions.ResourceNotFoundException;
import com.elphie.users.libs.Utiles;
import com.elphie.users.models.User;
import com.elphie.users.repositories.IUserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/users/")
public class UserController {
    
    // PROPERTIES ////////////////
    @Autowired
    private IUserRepository userRepository;

    // HTTP REQUEST METHODS ////////////////

    /**
     * Used to CREATE a user and add it to the DB.
     * Strategy: Validate data coming from FE, Try add user to DB, return User or Error.
     * Steps: 
     *    1 -> Validate data from Front End
     *       A -> Check email is a valid email using a Regular Expresion comparison 
     *            (Check libs/Utiles.java Method validateEmail(email: String))
     *       B -> Check that Account Type is either "elphie" or "calphie".
     *    2 -> If data is ok then create validatedUser and set data, 
     *         else return ERROR Response with 400 Bad Request Status
     *    3 -> Try add to DB createdUser with validatedUser data
     *    4 -> If adds to DB ok then return SUCCESS Response with 200 Ok status
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error
     * @param user type User from Request Body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody User user) {

        // Create validatedUser instance
        User validatedUser = new User();

        // Validate FE data
        if(Utiles.validateEmail(user.getEmail()) && 
        user.getPassword() != null &&
        (user.getAccountType().equals("elphie") || user.getAccountType().equals("calphie"))) {

            // Populate validatedUser with FE User data
            validatedUser.setFirstName(user.getFirstName());
            validatedUser.setLastName(user.getLastName());
            validatedUser.setEmail(user.getEmail());
            validatedUser.setPassword(user.getPassword());
            validatedUser.setAccountType(user.getAccountType());
            validatedUser.setCreatedOn(new Timestamp(System.currentTimeMillis()));

        } else {

            // Return ERROR Response 400 Bad Request
            return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
        }

        try {
            // Add user to DB
            User createdUser = userRepository.save(validatedUser);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, "Success creating User.", createdUser);

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage(), null);
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
        
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setAvatarPath(userDetails.getAvatarPath());
        
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
