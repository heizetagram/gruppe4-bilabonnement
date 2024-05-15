package com.example.gruppe4bilabonnement.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class LeaseAgreement {
    private int id;
    private int customerId;
    private int carModelId;
    private String licensePlate;
    private String vin;
    private String brand;
    private String model;
    private String equipmentLevel;
    private double steelPrice;
    private double registrationFee;
    private double co2Emission;
    private boolean isRented;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private long boughtKm;
    private long startKm;
    private double price;
    private int paymentTime;
    private int transportTime;
    private int carId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public int getCarModelId() {
        return carModelId;
    }
    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
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
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public String getEquipmentLevel() {
        return equipmentLevel;
    }
    public void setEquipmentLevel(String equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }
    public double getSteelPrice() {
        return steelPrice;
    }
    public void setSteelPrice(double steelPrice) {
        this.steelPrice = steelPrice;
    }
    public double getRegistrationFee() {
        return registrationFee;
    }
    public void setRegistrationFee(double registrationFee) {
        this.registrationFee = registrationFee;
    }
    public double getCo2Emission() {
        return co2Emission;
    }
    public void setCo2Emission(double co2Emission) {
        this.co2Emission = co2Emission;
    }
    public boolean isRented() {
        return isRented;
    }
    public void setRented(boolean rented) {
        isRented = rented;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public long getBoughtKm() {
        return boughtKm;
    }
    public void setBoughtKm(long boughtKm) {
        this.boughtKm = boughtKm;
    }
    public long getStartKm() {
        return startKm;
    }
    public void setStartKm(long startKm) {
        this.startKm = startKm;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getPaymentTime() {
        return paymentTime;
    }
    public void setPaymentTime(int paymentTime) {
        this.paymentTime = paymentTime;
    }
    public int getTransportTime() {
        return transportTime;
    }
    public void setTransportTime(int transportTime) {
        this.transportTime = transportTime;
    }
    public int getCarId() {
        return carId;
    }
    public void setCarId(int carId) {
        this.carId = carId;
    }
}
