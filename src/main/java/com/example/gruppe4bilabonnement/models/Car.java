package com.example.gruppe4bilabonnement.models;

public class Car {
    private int id;
    private int carModelId;
    private FuelType fuelType;
    private String licensePlate;
    private String vin;
    private String equipmentLevel;
    private long km;
    private double registrationFee;
    private double steelPrice;
    private int co2Emission;
    private boolean isRented;

    public Car() {
    }

    public Car(int id, int carModelId, FuelType fuelType, String licensePlate, String vin, String equipmentLevel, long km, double registrationFee, double steelPrice, int co2Emission, boolean isRented) {
        this.id = id;
        this.carModelId = carModelId;
        this.fuelType = fuelType;
        this.licensePlate = licensePlate;
        this.vin = vin;
        this.equipmentLevel = equipmentLevel;
        this.km = km;
        this.registrationFee = registrationFee;
        this.steelPrice = steelPrice;
        this.co2Emission = co2Emission;
        this.isRented = isRented;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public long getKm() {
        return km;
    }

    public void setKm(long km) {
        this.km = km;
    }

    public double getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }

    public double getSteelPrice() {
        return steelPrice;
    }

    public void setSteelPrice(double steelPrice) {
        this.steelPrice = steelPrice;
    }

    public int getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(int co2Emission) {
        this.co2Emission = co2Emission;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }
}
