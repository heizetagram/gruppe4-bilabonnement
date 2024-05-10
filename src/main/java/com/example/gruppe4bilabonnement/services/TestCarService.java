package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.repositories.TestCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TestCarService {
    @Autowired
    private TestCarRepository testCarRepository;

    public void createCar(int carModelId, FuelType fuelType,
                          String licensePlate, String vin, String equipmentLevel,
                          long km, double registrationFee, double steelPrice,
                          int co2Emission, String isRented) {
        if (carModelId != 0) {
            testCarRepository.createCar(carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
        }
    }

    public void createCarModel(CarBrand carBrand, String carModelName, CarType carType) {
        testCarRepository.createCarModel(carBrand, carModelName, carType);
    }

    // Get carModelId by Brand and Model
    public int getCarModelIdByBrandAndModelName(CarBrand carBrand, String carModelName) {
        try {
            return testCarRepository.getCarModelIdByBrandAndModelName(carBrand, carModelName);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public CarModel getCarModelByCarModelId(int carModelId) {
        return testCarRepository.getCarModelByCarModelId(carModelId);
    }

    public List<CarModel> getAllCarModelsByBrand(CarBrand carBrand) {
        return testCarRepository.getAllCarModelsByBrand(carBrand);
    }

    public List<CarBrand> getAllCarBrands() {
        return testCarRepository.getAllCarBrands();
    }

    public List<FuelType> getAllFuelTypes() {
        return testCarRepository.getAllFuelTypes();
    }

    public List<CarModel> getAllCarModels() {
        return testCarRepository.getAllCarModels();
    }

    public List<CarType> getAllCarTypes() {
        return testCarRepository.getAllCarTypes();
    }

    public List<Car> getAllCars() {
        return testCarRepository.getAllCars();
    }
}
