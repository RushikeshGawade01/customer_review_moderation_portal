package com.crmp.model;

/**
 * Defines the three roles used in the CRMP application.
 */
public enum UserRole {
    ROLE_CUSTOMER,    // Can submit and view own reviews
    ROLE_MODERATOR,   // Can view all reviews and update their status
    ROLE_ADMIN        // Full access: user management + all moderator actions
}
