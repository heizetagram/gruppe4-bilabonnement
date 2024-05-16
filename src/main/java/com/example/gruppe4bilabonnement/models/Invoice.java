package com.example.gruppe4bilabonnement.models;

import java.time.LocalDate;

public class Invoice {
    private int id;
    private int leaseAgreementId;
    private double downPayment;
    private double grossPrice;
    private double netPrice;
    private LocalDate createdAt;

    public Invoice() {
    }

    public double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(double downPayment) {
        this.downPayment = downPayment;
    }

    public double getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(double grossPrice) {
        this.grossPrice = grossPrice;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLeaseAgreementId() {
        return leaseAgreementId;
    }

    public void setLeaseAgreementId(int leaseAgreementId) {
        this.leaseAgreementId = leaseAgreementId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
