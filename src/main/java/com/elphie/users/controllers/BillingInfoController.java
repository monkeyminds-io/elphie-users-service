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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elphie.users.libs.Utiles;
import com.elphie.users.models.BillingInfo;
import com.elphie.users.repositories.IBillingInfoRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/users/billing/")
public class BillingInfoController {

    // PROPERTIES ////////////////
    @Autowired
    private IBillingInfoRepository billingInfoRepository;

    // HTTP REQUEST METHODS ////////////////

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
    
}
