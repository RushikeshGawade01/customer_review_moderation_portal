package com.crmp.model;

/**
 * Represents the lifecycle states of a customer review.
 *
 * State Transition:
 *   PENDING --> APPROVED
 *   PENDING --> REJECTED
 *   PENDING --> FLAGGED
 *   FLAGGED --> APPROVED
 *   FLAGGED --> REJECTED
 */
public enum ReviewStatus {
    PENDING,    // Newly submitted, awaiting moderation
    APPROVED,   // Approved by a moderator — visible to public
    REJECTED,   // Rejected by a moderator — not published
    FLAGGED     // Flagged for further review or escalation
}
