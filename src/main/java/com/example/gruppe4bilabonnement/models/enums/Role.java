package com.example.gruppe4bilabonnement.models.enums;

// BLIVER IKKE BRUGT LIGE NU
public enum Role {
    SALESPERSON("Medarbejder"),
    MECHANIC("Mekaniker"),
    BUSINESS_DEV("Forretningsudvikler"),
    ADMIN("Admin");

    private final String displayValue;

    private Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
