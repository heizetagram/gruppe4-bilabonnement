package com.example.gruppe4bilabonnement.models;

import com.example.gruppe4bilabonnement.models.enums.CarType;

public class CarModel {
    private int id;
    private CarBrand brand;
    private String modelName;
    private CarType carType;

    public CarModel() {
    }

    public CarModel(int id, CarBrand brand, String modelName, CarType carType) {
        this.id = id;
        this.brand = brand;
        this.modelName = modelName;
        this.carType = carType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand.getBrand();
    }

    public void setBrand(CarBrand brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }
}
