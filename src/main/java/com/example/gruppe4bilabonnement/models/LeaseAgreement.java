package com.example.gruppe4bilabonnement.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class LeaseAgreement {
    private int id;
    private int customerId;
    private int carId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private long boughtKm;
    private long startKm;
    private double price;
    private int paymentTime;
    private int transportTime;

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

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
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
