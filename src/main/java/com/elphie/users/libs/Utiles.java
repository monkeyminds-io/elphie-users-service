package com.elphie.users.libs;
// =============================================================================
// File Name: libs/Utiles.java
// File Description:
// This file contains handy methods to help increase development.
// =============================================================================

import java.util.ArrayList;
// =============================================================================
// Imports
// =============================================================================
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elphie.users.models.BillingInfo;
import com.elphie.users.models.User;

// =============================================================================
// Class
// =============================================================================
public class Utiles {

    /**
     * Used to generate custome JSON Http Responses
     * @param status type HttpStatus -> The Response status 200 OK | 400 Client Error | 404 Not Found | 500 Server Error
     * @param message type String -> The Response message
     * @param data type Object | null -> If Success add Data Object else set as null
     * @return 
     */
    public static ResponseEntity<Object> generateResponse(HttpStatus status, String message, Object data) {

        // Create the Response Object as a Map
        Map<String, Object> responseMap = new HashMap<String, Object>();

        // Populate Response
        responseMap.put("timestamp", new Date());
        responseMap.put("status", status.value());
        responseMap.put("ok", status.value() == 200 ? true : false);
        responseMap.put("message", message);
        if(data != null) responseMap.put("data", data);

        // Return Response Object
        return new ResponseEntity<Object>(responseMap, status);
    }
    
    /**
     * Used to validate email data types against regular expresion pattern
     * @param email type String
     * @return boolean TRUE | FALSE
     */
    public static boolean validateEmail(String email) {

        // Regular Expresion Pattern for Email
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        // If email matches regex pattern returns TRUE else returns FALSE
        return Pattern.compile(regex).matcher(email).matches();
    }

    /**
     * Used to validate eircode data types against regular expresion pattern
     * @param eircode type String
     * @return boolean TRUE | FALSE
     */
    public static boolean validateEircode(String eircode) {

        // Regular Expresion Pattern for Eircode
        String regex = "(?:^[AC-FHKNPRTV-Y][0-9]{2}|D6W)[ -]?[0-9AC-FHKNPRTV-Y]{4}$";

        // If eircode matches regex pattern returns TRUE else returns FALSE
        return Pattern.compile(regex).matcher(eircode).matches(); 
    }

    /**
     * Used to validate card expiry data types against regular expresion pattern
     * @param cardExpiry type String
     * @return boolean TRUE | FALSE
     */
    public static boolean validateCardExpiry(String cardExpiry) {

        // Regular Expresion Pattern for Eircode
        String regex = "^(0[1-9]|1[0-2])[ ]\\/[ ]?([0-9]{4}|[0-9]{2})$";

        // If eircode matches regex pattern returns TRUE else returns FALSE
        return Pattern.compile(regex).matcher(cardExpiry).matches(); 
    }

    /**
     * Used to validate card CVC data types against regular expresion pattern
     * @param cardCVC type String
     * @return boolean TRUE | FALSE
     */
    public static boolean validateCardCVC(String cardCVC) {

        // Regular Expresion Pattern for Eircode
        String regex = "^[0-9]{3,4}$";

        // If eircode matches regex pattern returns TRUE else returns FALSE
        return Pattern.compile(regex).matcher(cardCVC).matches(); 
    }

    /**
     * Used to validate USer Object Data as per DB Policies.
     *    1 -> NOT NULL Data Policy for: email, password and account_type.
     *    2 -> NOT VALID Data Policy for: email and account_type.
     * @param user type User
     * @return errors type ArrayList<String>
     */
    public static ArrayList<String> validateUser(User user) {

        // Local variable errors
        ArrayList<String> errors = new ArrayList<>();

        // Validate NOT NULL Policy violations
        if(user.getEmail() == null) errors.add("Email cannot be NULL.");
        if(user.getPassword() == null) errors.add("Password cannot be NULL.");
        if(user.getAccountType() == null) errors.add("Account Type cannot be NULL.");

        // Validate DATA TYPE Policy Violations
        if(!validateEmail(user.getEmail())) errors.add("Invalid Email data.");
        if(!user.getAccountType().equals("calphie") && !user.getAccountType().equals("elphie")) errors.add("Invalid Account Type data.");

        // Return isValid value
        return errors;
    }

    public static ArrayList<String> validateBillingInfo(BillingInfo billingInfo) {
        
        // Local variable errors
        ArrayList<String> errors = new ArrayList<>();

        // Validate NOT NULL Policy violations
        if(billingInfo.getUserId() == null) errors.add("User Id cannot be NULL.");
        if(billingInfo.getAddressLine1() == null) errors.add("Address Line 1 cannot be NULL.");
        if(billingInfo.getCounty() == null) errors.add("County cannot be NULL.");
        if(billingInfo.getCardName() == null) errors.add("Card Name cannot be NULL.");
        if(billingInfo.getCardNumber() == null) errors.add("Card Number cannot be NULL.");
        if(billingInfo.getCardExpiry() == null) errors.add("Card Expiry cannot be NULL.");
        if(billingInfo.getCardCVC() == null) errors.add("Card CVC cannot be NULL.");

        // Validate DATA TYPE Policy Violations
        if(billingInfo.getEircode() != null) {
            if(!validateEircode(billingInfo.getEircode())) errors.add("Invalid Eircode data.");
        }
        if(!validateCardExpiry(billingInfo.getCardExpiry())) errors.add("Invalid Card Expiry data.");
        if(!validateCardCVC(billingInfo.getCardCVC())) errors.add("Invalid Card CVC data.");


        // Return isValid value
        return errors;
    }
}
