package com.elphie.users.models;

// =============================================================================
// File Name: model/User.java
// File Description:
// This file contains the User Entity Class
// =============================================================================

// =============================================================================
// IMPORTS
// =============================================================================
import java.sql.Date;

import jakarta.persistence.*;

// =============================================================================
// CLASS DECLARATION
// =============================================================================
@Entity
@Table(name = "users")
public class User {
    
    // PROPERTIES ////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "plan")
    private String plan;

    @Column(name = "avatar_url")
    private String avatar_url;

    @Column(name = "created_at")
    private Date created_at;

    @Column(name = "updated_at")
    private Date updated_at;

    // DEFAULT CONSTRUCTOR ////////////////
    public User() {}

    // CONSTRUCTORS ////////////////
    public User(String email, String password, String plan) {
        super();
        this.email = email;
        this.password = password;
        this.plan = plan;
    }

    public User(String firstname, String lastname, String email, String password, String plan) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.plan = plan;
    }

    // GETTERS & SETTERS ////////////////
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getAvatar_url() {
        return this.avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
    


}
