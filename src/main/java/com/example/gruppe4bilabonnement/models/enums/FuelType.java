package com.example.gruppe4bilabonnement.models.enums;

/*
public class FuelType {
    private String fuelType;

    public FuelType() {
    }

    public FuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
 */
public enum FuelType {
    GASOLINE("Benzin"),
    DIESEL("Diesel"),
    HYBRID("Hybrid"),
    ELECTRIC("Elektrisk");

    private final String displayValue;

    FuelType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}