package com.elphie.users.controllers;

import java.sql.Timestamp;
import java.util.Date;

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
// File Name: controllers/BillingInfoController.java
// File Description:
// This file contains the code of the Billing Info Controller that handles
// the Http requests, manipulates the data and returns the responses.
// =============================================================================

// =============================================================================
// Controller Imports
// =============================================================================

// =============================================================================
// Controller Class
// =============================================================================
@CrossOrigin(origins = "http://localhost:9000")
@RestController
@RequestMapping("/billing/")
public class BillingInfoController {

    // PROPERTIES ////////////////
    @Autowired
    private IBillingInfoRepository billingInfoRepository;

    // HTTP REQUEST METHODS ////////////////

    /**
     * Used to CREATE a billing info and add it to the DB.
     * Strategy: Validate data coming from FE, Try add billing info to DB, return Billing Infpo or Error.
     * Steps: 
     *    1 -> Validate data from Front End
     *       A -> 
     *    2 -> If data is ok then create validatedBillingInfo and set data, 
     *         else return ERROR Response with 400 Bad Request Status
     *    3 -> Try add to DB createdBillingInfo with validatedBillingInfo data
     *    4 -> If adds to DB ok then return SUCCESS Response with 200 Ok status
     *    5 -> Catch Server side errors -> If any then return ERRO Response with 500 Internal Server Error
     * @param billingInfo type BillingInfo from Request Body
     * @return ResponseEntity<Object> -> either SUCCESS Response 200 ok | ERROR Response 400 Bad Request | ERROR 500 Internal Server Error
     */
    @PostMapping("/create")
    public ResponseEntity<Object> createBillingInfo(@RequestBody BillingInfo billingInfo) {
         // Create validatedBillingInfo instance
         BillingInfo validatedBillingInfo = new BillingInfo();

         // Validate Frint End data
         if(billingInfo.getUserId() != null) { // TODO Add more validations

            // Manipulate the Expiry Date to match DB requirements
            String expiryDate = "20" + billingInfo.getCardExpiry().substring(billingInfo.getCardExpiry().length() - 2) + 
                                    "-" + billingInfo.getCardExpiry().substring(0, 2) 
                                        + "-00";

            // Populate validatedBillingInfo with billingInfo FE data
            validatedBillingInfo.setUserId(billingInfo.getUserId());
            validatedBillingInfo.setAddressLine1(billingInfo.getAddressLine1());
            validatedBillingInfo.setAddressLine2(billingInfo.getAddressLine2());
            validatedBillingInfo.setCounty(billingInfo.getCounty());
            validatedBillingInfo.setEircode(billingInfo.getEircode());
            validatedBillingInfo.setCardName(billingInfo.getCardName());
            validatedBillingInfo.setCardNumber(billingInfo.getCardNumber());
            validatedBillingInfo.setCardExpiry(expiryDate);
            validatedBillingInfo.setCardCVC(validatedBillingInfo.getCardCVC());
            validatedBillingInfo.setCreatedOn(new Timestamp(System.currentTimeMillis()));

         } else {

            // Return ERROR Response 400 Bad Request
            return Utiles.generateResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null);
         }

         try {
            
            // Add billing Info to DB
            BillingInfo createdBillingInfo = billingInfoRepository.save(validatedBillingInfo);

            // Return SUCCESS Response 200 OK
            return Utiles.generateResponse(
                HttpStatus.OK, "Success creating Billing Info.", createdBillingInfo);

         } catch (Exception error) {
            
            // Return ERROR Response 500 Internal Server Error
            return Utiles.generateResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage(), null);
         }
    }
    
}
