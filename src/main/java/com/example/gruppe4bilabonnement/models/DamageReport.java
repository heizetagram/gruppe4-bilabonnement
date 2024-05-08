package com.example.gruppe4bilabonnement.models;

public class DamageReport {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
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

    private Long id;
    private Long carId;
    private String damageText;
    private Double price;

}
