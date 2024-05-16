package com.example.gruppe4bilabonnement.models;

public class DamageReport {
    public DamageReport() {
    }

    public DamageReport(int id, int carId, String damageText, Double price) {
        this.id = id;
        this.carId = carId;
        this.damageText = damageText;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getDamageText() {
        return damageText;
    }

    public void setDamageText(String damageText) {
        this.damageText = damageText;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    private int id;
    private int carId;
    private String damageText;
    private Double price;

}
