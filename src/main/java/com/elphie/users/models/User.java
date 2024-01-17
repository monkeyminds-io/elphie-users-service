package com.elphie.users.models;

// =============================================================================
// File Name: model/User.java
// File Description:
// This file contains the User Entity Class
// =============================================================================

// =============================================================================
// Entity Imports
// =============================================================================
import java.sql.Timestamp;

import jakarta.persistence.*;

// =============================================================================
// Entity Class
// =============================================================================
@Entity
@Table(name = "users")
public class User {
    
    // PROPERTIES ////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "account_type", nullable = false)
    private String account_type;

    @Column(name = "created_on")
    private Timestamp created_on;

    @Column(name = "updated_on")
    private Timestamp updated_on;

    // DEFAULT CONSTRUCTOR ////////////////
    public User() {}

    // CONSTRUCTORS ////////////////
    public User(String first_name, String last_name, String email, String password, String account_type) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.account_type = account_type;
    }
    
    // GETTERS & SETTERS ////////////////
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public String getLastName() {
        return this.last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountType() {
        return this.account_type;
    }

    public void setAccountType(String account_type) {
        this.account_type = account_type;
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

    public void setUpdatedOn(Timestamp updated_on) {
        this.updated_on = updated_on;
    }
    


}
