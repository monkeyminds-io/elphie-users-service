package com.elphie.users.exceptions;

// =============================================================================
// File Name: exceptions/ResourceNotFoundException.java
// File Description:
// This file contains the code of the Resource Not Found Exception
// =============================================================================

// =============================================================================
// Imports
// =============================================================================
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// =============================================================================
// Exception
// =============================================================================
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
