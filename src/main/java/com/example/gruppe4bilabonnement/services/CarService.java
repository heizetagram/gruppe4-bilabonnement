package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.models.enums.CarType;
import com.example.gruppe4bilabonnement.models.enums.FuelType;
import com.example.gruppe4bilabonnement.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public boolean checkIfLicensePlateExists(String licensePlate) {
        boolean licensePlateExists;
        try {
            carRepository.getCarByLicensePlate(licensePlate);
            licensePlateExists = true;
        } catch (EmptyResultDataAccessException e) {
            licensePlateExists = false;
        }
        return licensePlateExists;
    }

    public boolean checkIfVinExists(String vin) {
        boolean vinExists;
        try {
            carRepository.getCarByVin(vin);
            vinExists = true;
        } catch (EmptyResultDataAccessException e) {
            vinExists = false;
        }
        return vinExists;
    }

    public void updateCar(int carId, int carModelId, FuelType fuelType, String licensePlate, String vin, String equipmentLevel,
                          long km, double registrationFee, double steelPrice, int co2Emission) {
        if (carModelId != 0) {
            carRepository.updateCar(carId, carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission);
        }
    }

    public void createCarModel(CarBrand carBrand, String carModelName, CarType carType) {
        carRepository.createCarModel(carBrand, carModelName, carType);
    }

    public CarModel getCarModelById(int carModelId) {
        return carRepository.getCarModelById(carModelId);
    }

    public Car getCarById(int carId) {
        return carRepository.getCarById( carId);
    }

    public void deleteCarById(int carId) {
        carRepository.deleteCarById(carId);
    }

    public List<CarModel> getAllCarModelsByBrand(CarBrand carBrand) {
        return carRepository.getAllCarModelsByBrand(carBrand);
    }

    // Overloaded method
    public List<CarModel> getAllCarModelsByBrand(String carBrand) {
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

    public List<Car> getAllAvailableCars() {
        return carRepository.getAllAvailableCars();
    }

    public boolean checkIfCarBrandExists(CarBrand carBrand) {
        boolean carBrandExists;
        try {
            carRepository.getCarBrandByBrand(carBrand);
            carBrandExists = true;
        } catch (EmptyResultDataAccessException e) {
            carBrandExists = false;
        }
        return carBrandExists;
    }

    public void createCarBrand(CarBrand carBrand) {
        carRepository.createCarBrand(carBrand);
    }

    public void updateCarBrand(CarBrand newCarBrand, CarBrand oldCarBrand) {
        carRepository.updateCarBrand(newCarBrand, oldCarBrand);
    }

    public boolean doesCarBrandHaveModels(CarBrand carBrand) {
        List<CarModel> carModels = carRepository.getAllCarModelsByBrand(carBrand);
        return !carModels.isEmpty();
    }

    public void deleteCarBrand(CarBrand carBrand) {
        carRepository.deleteCarBrand(carBrand);
    }

    public boolean checkIfCarModelExists(CarBrand carBrand, String modelName, CarType carType) {
        boolean carModelNameExists;
        try {
            carRepository.getCarModelByBrandAndModelNameAndCarType(carBrand, modelName, carType);
            carModelNameExists = true;
        } catch (EmptyResultDataAccessException e) {
            carModelNameExists = false;
        }
        return carModelNameExists;
    }

    // Overloaded method
    public boolean checkIfCarModelExists(CarBrand carBrand, String modelName) {
        boolean carModelNameEXists;
        try {
            carRepository.getCarModelByBrandAndModelName(carBrand, modelName);
            carModelNameEXists = true;
        } catch (EmptyResultDataAccessException e) {
            carModelNameEXists = false;
        }
        return carModelNameEXists;
    }

    public void updateCarModel(int carModelId, String modelName, CarType carType) {
        carRepository.updateCarModel(carModelId, modelName, carType);
    }

    public void deleteCarModelById(int carModelId) {
        carRepository.deleteCarModelById(carModelId);
    }

    public void rentCar(int carId) {
        carRepository.rentCar(carId);
    }

    public void makeCarAvailable(int carId) {
        carRepository.makeCarAvailable(carId);
    }

    public boolean checkIfCarIsRented(int carId) {
        boolean isCarRented;
        try {
            carRepository.getRentedCarById(carId);
            isCarRented = true;
        } catch (EmptyResultDataAccessException e) {
            isCarRented = false;
        }
        return isCarRented;
    }
}
