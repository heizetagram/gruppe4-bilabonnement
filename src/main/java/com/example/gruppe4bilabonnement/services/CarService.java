package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public void createCar(int carModelId, FuelType fuelType,
                          String licensePlate, String vin, String equipmentLevel,
                          long km, double registrationFee, double steelPrice,
                          int co2Emission, String isRented) {
        if (carModelId != 0) {
            carRepository.createCar(carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
        }
    }

    public void createCarModel(CarBrand carBrand, String carModelName, CarType carType) {
        carRepository.createCarModel(carBrand, carModelName, carType);
    }

    // Get carModelId by Brand and Model
    public int getCarModelIdByBrandAndModelName(CarBrand carBrand, String carModelName) {
        try {
            return carRepository.getCarModelIdByBrandAndModelName(carBrand, carModelName);
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public CarModel getCarModelByCarModelId(int carModelId) {
        return carRepository.getCarModelByCarModelId(carModelId);
    }

    public List<CarModel> getAllCarModelsByBrand(CarBrand carBrand) {
        return carRepository.getAllCarModelsByBrand(carBrand);
    }

    public List<CarBrand> getAllCarBrands() {
        return carRepository.getAllCarBrands();
    }

    public List<FuelType> getAllFuelTypes() {
        return carRepository.getAllFuelTypes();
    }

    public List<CarModel> getAllCarModels() {
        return carRepository.getAllCarModels();
    }

    public List<CarType> getAllCarTypes() {
        return carRepository.getAllCarTypes();
    }

    public List<Car> getAllCars() {
        return carRepository.getAllCars();
    }
}
