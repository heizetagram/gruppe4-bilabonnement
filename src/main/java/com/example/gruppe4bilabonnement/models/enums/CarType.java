package com.example.gruppe4bilabonnement.models.enums;

/*public class CarType {
    private String carType;

    public CarType() {
    }

    public CarType(String carType) {
        this.carType = carType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }
}*/

public enum CarType {
    FAMILY("Familie"),
    LUXURY("Luksus"),
    SPORT("Sport");

    private final String displayValue;

    CarType(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
