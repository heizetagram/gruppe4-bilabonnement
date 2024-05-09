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

    public void createCar(CarBrand carBrand, String carModelName, FuelType fuelType,
                          String licensePlate, String vin, String equipmentLevel,
                          BigInteger km, double registrationFee, double steelPrice,
                          int co2Emission) {
        // Find car model id by its brand and model
        int carModelId = getCarModelIdByBrandAndModelName(carBrand, carModelName);

        // Make isRented = false by default
        if (carModelId != 0) {
            testCarRepository.createCar(carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, false);
        }
    }

    public int getCarModelIdByBrandAndModelName(CarBrand carBrand, String carModelName) {
        // Get carModelId by Brand and Model
        try {
            return testCarRepository.getCarModelIdByBrandAndModelName(carBrand, carModelName);
        } catch (NullPointerException e) {
            return 0;
        }
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

    public List<Car> getAllCars() {
        return testCarRepository.getAllCars();
    }
}
