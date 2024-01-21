package com.elphie.users.controllers;

// =============================================================================
// File Name: controllers/BillingInfoController.java
// File Description:
// This file contains the code of the Billing Info Controller that handles
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elphie.users.definitions.UpdateBillingBody;
import com.elphie.users.exceptions.ResourceNotFoundException;
import com.elphie.users.libs.Utiles;
import com.elphie.users.models.BillingInfo;
import com.elphie.users.repositories.IBillingInfoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

// =============================================================================
// Controller Data Types
// =============================================================================


// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/users/billing/")
public class BillingInfoController {

    // PROPERTIES ////////////////
    @Autowired
    private IBillingInfoRepository billingInfoRepository;

    // HTTP REQUEST METHODS ////////////////

    // TODO Check all comments
    
    /**
     * Used to CREATE a billing info and add it to the DB.
     * Strategy: Validate data coming from FE, Try add billing info to DB, return Billing Infpo or Error.
     * Steps: 
     *    1 -> If validateBillingInfo has ERRORS return ERROR Response with 400 Bad Request Status with ERRORS Array
     *    3 -> Try add BillingInfo to DB
     *    4 -> If added to DB then return SUCCESS Response with 200 Ok status with createdBillingInfo Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param billingInfo type BillingInfo from Request Body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody BillingInfo billingInfo) {
         // Create validatedBillingInfo instance
         ArrayList<String> errors = Utiles.validateBillingInfo(billingInfo);

         // Return ERROR Response 400 Bad Request
         if(errors != null && errors.size() > 0) {
            return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errors);
        }

         try {

            // Manipulate the Expiry Date to match DB requirements
            String expiryDate = "20" + billingInfo.getCardExpiry().substring(billingInfo.getCardExpiry().length() - 2) + 
                                    "-" + billingInfo.getCardExpiry().substring(0, 2) 
                                        + "-00";

            // Set missing data in Billing Info Object
            billingInfo.setCardExpiry(expiryDate);
            billingInfo.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Add billing Info to DB
            BillingInfo createdBillingInfo = billingInfoRepository.save(billingInfo);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success creating Billing Info.", createdBillingInfo);

         } catch (Exception error) {
            
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage(), "Failed to add Billing Info to DB.");
         }
    }

    /**
     * Used to GET a billing by user ID from the DB.
     * Strategy: Validate data coming from FE, Try find billing in DB and Catch Errors.
     * Steps: 
     *    1 -> If user ID is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try find billing in DB
     *    4 -> If found in DB then return SUCCESS Response with 200 Ok status with Billing Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param userId type String from URL Params
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @GetMapping(value="/get", params="userId")
    public ResponseEntity<Object> getUserByEmail(@RequestParam(name="userId") String userId) {

        // Validate that email is NOT NULL
        if(userId == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "User ID cannot be NULL.");

        try {
            // Find User or Throw Exception
            BillingInfo billingInfo = billingInfoRepository.findByUserId(Long.parseLong(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Billing with user ID " + userId + " not found."));
            
            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success getting Billing.", billingInfo); 

        } catch (Exception error) {

            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to find User to DB.");
        }
    }

    /**
     * Used to UPDATE the billing by id from the DB.
     * Strategy: Validate data coming from FE, Try update password of user in DB and Catch Errors.
     * Steps: 
     *    1 -> If Request Body is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try update Billing in DB
     *    4 -> If updated in DB then return SUCCESS Response with 200 Ok status with Billing Object
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @param requestBody type UpdatePasswordBody from the request body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PutMapping("/{id}/update")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UpdateBillingBody updateBillingBody) {
        
        // Validate new password is not null
        if(updateBillingBody == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request Body cannot be NULL.");

        try {
            // Find billing or Throw Exception
            BillingInfo billingInfo = billingInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Info with id " + id + " not found."));
            
            // Set new data
            if(updateBillingBody.getAddressLine1() != null) billingInfo.setAddressLine1(updateBillingBody.getAddressLine1());
            if(updateBillingBody.getAddressLine2() != null) billingInfo.setAddressLine2(updateBillingBody.getAddressLine2());
            if(updateBillingBody.getCounty() != null) billingInfo.setCounty(updateBillingBody.getCounty());
            if(updateBillingBody.getEircode() != null) billingInfo.setEircode(updateBillingBody.getEircode());
            if(updateBillingBody.getCardName() != null) billingInfo.setCardName(updateBillingBody.getCardName());
            if(updateBillingBody.getCardNumber() != null) billingInfo.setCardNumber(updateBillingBody.getCardNumber());
            if(updateBillingBody.getCardExpiry() != null) billingInfo.setCardExpiry(updateBillingBody.getCardExpiry());
            if(updateBillingBody.getCardCVC() != null) billingInfo.setCardCVC(updateBillingBody.getCardCVC());
            

            // Set new updated_on date
            billingInfo.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            
            // Save user with new data
            BillingInfo updatedBillingInfo = billingInfoRepository.save(billingInfo);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success updating the Billing Info.", updatedBillingInfo);

        } catch (Exception error) {
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to update Billing Info in DB.");
        }
        
    }

    /**
     * Used to DELETE the billing by id from the DB.
     * Strategy: Validate data coming from FE, Try delete billing in DB and Catch Errors.
     * Steps: 
     *    1 -> If billingId is NULL return ERROR Response with 400 Bad Request Status with message
     *    3 -> Try delete billing in DB
     *    4 -> If delete from DB then return SUCCESS Response with 200 Ok status with message
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error with Message.
     * @param id type Long from URL Params
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        // Validate new password is not null
        if(id == null) return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "Billing ID cannot be NULL.");

        try {
            // Find billing or Throw Exception
            BillingInfo billingInfo = billingInfoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billing Info with id " + id + " not found."));
        
            // Delete user
            billingInfoRepository.delete(billingInfo);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(HttpStatus.OK, "Success deleting the Billing Info.", null);

        } catch (Exception error) {
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,  error.getMessage(), "Failed to delete Billing Info from DB.");
        }
    }
    
}
