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
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elphie.users.definitions.UpdatePasswordBody;
import com.elphie.users.definitions.UpdateUserBody;
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
import org.springframework.web.bind.annotation.RequestParam;

// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/users/")
public class UserController {
    
    // PROPERTIES ////////////////
    @Autowired
    private IUserRepository userRepository;

    // HTTP REQUEST METHODS ////////////////

    /**
     * Used to CREATE a user and add it to the DB.
     * Strategy: Validate data coming from FE, Try add user to DB and Catch Errors.
     * Steps: 
     *    1 -> If validateUser has ERRORS return ERROR Response with 400 Bad Request Status with ERRORS Array
     *    3 -> Try add User to DB
     *    4 -> If added to DB then return SUCCESS Response with 200 Ok status with createdUser Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param user type User from Request Body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody User user) {

        // Get ERRORS ArrayList
        ArrayList<String> errors = Utiles.validateUser(user);
        
        // Return ERROR Response 400 Bad Request
        if(errors != null && errors.size() > 0) {
            return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        }

        try {
            // Set User created_on Timestamp as current time.
            user.setCreatedOn(new Timestamp(System.currentTimeMillis()));

            // Add user to DB
            User createdUser = userRepository.save(user);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success creating User.", createdUser);

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to add User to DB.");
        }
    }

    /**
     * Used to GET a user by ID from the DB.
     * Strategy: Validate data coming from FE, Try find user in DB and Catch Errors.
     * Steps: 
     *    1 -> If id is NULL return ERROR Response with 400 Bad Request Status with Message
     *    3 -> Try find User in DB
     *    4 -> If found in DB then return SUCCESS Response with 200 Ok status with User Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from Request Params
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params="id")
    public ResponseEntity<Object> getUserById(@RequestParam(name="id") String id) {
        // Validate that email is NOT NULL
        if(id == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Id cannot be NULL.");

        try {
            // Find User or Throw Exception
            User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
            
            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success getting User.", user); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to find User to DB.");
        }
    }

    /**
     * Used to GET a user by email from the DB.
     * Strategy: Validate data coming from FE, Try find user in DB and Catch Errors.
     * Steps: 
     *    1 -> If email is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try find User in DB
     *    4 -> If found in DB then return SUCCESS Response with 200 Ok status with User Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param email type String from URL Params
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params="email")
    public ResponseEntity<Object> getUserByEmail(@RequestParam(name="email") String email) {

        // Validate that email is NOT NULL
        if(email == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Email cannot be NULL.");

        try {
            // Find User or Throw Exception
            User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found."));
            
            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success getting User.", user); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to find User to DB.");
        }
    }
    
    /**
     * Used to UPDATE the password of a user by id from the DB.
     * Strategy: Validate data coming from FE, Try update password of user in DB and Catch Errors.
     * Steps: 
     *    1 -> If password is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try update password of User in DB
     *    4 -> If updated in DB then return SUCCESS Response with 200 Ok status with User Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @param requestBody type UpdatePasswordBody from the request body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PutMapping("/{id}/update/password")
    public ResponseEntity<Object> updateUserPassword(@PathVariable Long id, @RequestBody UpdatePasswordBody updatePasswordBody) {
        
        // Validate new password is not null
        if(updatePasswordBody.getNewPassword() == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request Body cannot be NULL.");

        try {
            // Find User or Throw Exception
            User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
            
            // Set new password and updated_on data
            user.setPassword(updatePasswordBody.getNewPassword());
            user.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Save user with new data
            User updatedUser = userRepository.save(user);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success updating the password.", updatedUser);

        } catch (Exception error) {
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to update password to DB.");
        }
        
    }

    /**
     * Used to UPDATE the user by id from the DB.
     * Strategy: Validate data coming from FE, Try update user in DB and Catch Errors.
     * Steps: 
     *    1 -> If Request Body is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try update User in DB
     *    4 -> If updated in DB then return SUCCESS Response with 200 Ok status with User Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @param updateUserBody type UpdateUserBody from the request body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateUserBody updateUserBody) {
        
        // Validate new password is not null
        if(updateUserBody == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request Body cannot be NULL.");

        try {
            // Find User or Throw Exception
            User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
            
            // Set new data
            if(updateUserBody.getFirstName() != null) user.setFirstName(updateUserBody.getFirstName());
            if(updateUserBody.getLastName() != null)user.setLastName(updateUserBody.getLastName());
            if(updateUserBody.getEmail() != null)user.setEmail(updateUserBody.getEmail());
            if(updateUserBody.getPassword() != null)user.setPassword(updateUserBody.getPassword());
            if(updateUserBody.getAccountType() != null)user.setAccountType(updateUserBody.getAccountType());

            // Set new updated_on date
            user.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Save user with new data
            User updatedUser = userRepository.save(user);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success updating the User.", updatedUser);

        } catch (Exception error) {
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to update User in DB.");
        }
        
    }

    /**
     * Used to DELETE the user by id from the DB.
     * Strategy: Validate data coming from FE, Try delete user in DB and Catch Errors.
     * Steps: 
     *    1 -> If userId is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try delete User in DB
     *    4 -> If delete from DB then return SUCCESS Response with 200 Ok status with message
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        // Validate new password is not null
        if(id == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "User ID cannot be NULL.");

        try {
            // Find User or Throw Exception
            User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
        
            // Delete user
            userRepository.delete(user);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success deleting the User.", null);

        } catch (Exception error) {
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to delete User from DB.");
        }
    }
}
