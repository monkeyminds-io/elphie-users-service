package com.elphie.users.repositories;

// =============================================================================
// File Name: repositories/BillingInfoRepository.java
// File Description:
// This file contains the code of the Billing Info Repository
// that handles the queries to the billing_info table in the DB
// =============================================================================

// =============================================================================
// Repository Imports
// =============================================================================
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elphie.users.models.BillingInfo;

// =============================================================================
// Repository Interface
// =============================================================================
@Repository
public interface IBillingInfoRepository extends JpaRepository<BillingInfo, Long> {
    
}
