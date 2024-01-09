package com.elphie.users.models;

import java.sql.Date;
import java.sql.Timestamp;

// =============================================================================
// File Name: models/BillingInfo.java
// File Description:
// This file contains the Billing Info entity object
// to intergact with the PostgreSQL DB
// =============================================================================

// =============================================================================
// Entity Imports
// =============================================================================
import jakarta.persistence.*;

// =============================================================================
// Entity Class
// =============================================================================
@Entity
@Table(name = "billing_info")
public class BillingInfo {
    
    // PROPERTIES ////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "address_line_1")
    private String address_line_1;

    @Column(name = "address_line_2")
    private String address_line_2;

    @Column(name = "county")
    private String county;
    
    @Column(name = "eircode")
    private String eircode;

    @Column(name = "card_full_name")
    private String card_full_name;

    @Column(name = "card_number")
    private String card_number;

    @Column(name = "card_expiry")
    private String card_expiry;

    @Column(name = "card_cvc")
    private String card_cvc;

    @Column(name = "created_on")
    private Timestamp created_on;

    @Column(name = "updated_on")
    private Timestamp updated_on;

    // DEFAULT CONSTRUCTOR ////////////////
    public BillingInfo() {}

    // CONSTRUCTORS ////////////////

    public BillingInfo(long user_id, String address_line_1, String address_line_2, String county, String eircode, String card_full_name, String card_number, String card_expiry, String card_cvc) {
        this.user_id = user_id;
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.county = county;
        this.eircode = eircode;
        this.card_full_name = card_full_name;
        this.card_number = card_number;
        this.card_expiry = card_expiry;
        this.card_cvc = card_cvc;
    }

    // GETTERS & SETTERS ////////////////

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    public String getAddressLine1() {
        return this.address_line_1;
    }

    public void setAddressLine1(String address_line_1) {
        this.address_line_1 = address_line_1;
    }

    public String getAddressLine2() {
        return this.address_line_2;
    }

    public void setAddressLine2(String address_line_2) {
        this.address_line_2 = address_line_2;
    }

    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getEircode() {
        return this.eircode;
    }

    public void setEircode(String eircode) {
        this.eircode = eircode;
    }

    public String getCardName() {
        return this.card_full_name;
    }

    public void setCardName(String card_full_name) {
        this.card_full_name = card_full_name;
    }

    public String getCardNumber() {
        return this.card_number;
    }

    public void setCardNumber(String card_number) {
        this.card_number = card_number;
    }

    public String getCardExpiry() {
        return this.card_expiry;
    }

    public void setCardExpiry(String card_expiry) {
        this.card_expiry = card_expiry;
    }

    public String getCardCVC() {
        return this.card_cvc;
    }

    public void setCardCVC(String card_cvc) {
        this.card_cvc = card_cvc;
    }

    public Timestamp getCreatedOn() {
        return this.created_on;
    }

    public void setCreatedOn(Timestamp created_on) {
        this.created_on = created_on;
    }

    public Timestamp getUpdatedOn() {
        return this.updated_on;
    }

    public void setUpdated_on(Timestamp updated_on) {
        this.updated_on = updated_on;
    }

}
