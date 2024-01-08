package com.elphie.users.utiles;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// =============================================================================
// File Name: utiles/ResponseHandler.java
// File Description:
// This file contains the code to manage the creation of 
// Success and Error JSON Responses
// =============================================================================
public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean isOk, String message, Object data) {

        Map<String, Object> map = new HashMap<String, Object>();

        try {

            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("ok", isOk);
            map.put("message", message);
            map.put("data", data);

        } catch (Exception error) {

            map.clear();
            map.put("timestamp", new Date());
            map.put("status", status.value());
            map.put("ok", isOk);
            map.put("message", error.getMessage());
            map.put("data", data);

        }

        return new ResponseEntity<Object>(map, status);

    }
    
}
