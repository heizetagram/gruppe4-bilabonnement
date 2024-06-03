package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.models.enums.CarType;
import com.example.gruppe4bilabonnement.models.enums.FuelType;
import com.example.gruppe4bilabonnement.services.rowmappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CarRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method 1
    // Dependency injection via field
    /*@Autowired
    private CarModelRowMapper carModelRowMapper;*/

    // Method 2
    // Dependency injection via the constructor.
    private final CarModelRowMapper carModelRowMapper;

    public CarRepository(CarModelRowMapper carModelRowMapper) {
        this.carModelRowMapper = carModelRowMapper;
    }

    public CarModel getCarModelByBrandAndModelNameAndCarType(CarBrand carBrand, String carModelName, CarType carType) {
        String query = "SELECT * FROM car_model WHERE brand = ? AND model_name = ? AND car_type = ?;";
        return jdbcTemplate.queryForObject(query, new CarModelRowMapper(), carBrand.getBrand(), carModelName, carType.name());
    }

    public CarModel getCarModelByBrandAndModelName(CarBrand carBrand, String carModelName) {
        String query = "SELECT * FROM car_model WHERE brand = ? AND model_name = ?;";
        return jdbcTemplate.queryForObject(query, new CarModelRowMapper(), carBrand.getBrand(), carModelName);
    }

    public Car getCarByLicensePlate(String licensePlate) {
        String query = "SELECT * FROM car WHERE license_plate = ?;";
        return jdbcTemplate.queryForObject(query, new CarRowMapper(), licensePlate);
    }

    public Car getCarByVin(String vin) {
        String query = "SELECT * FROM car WHERE vin = ?;";
        return jdbcTemplate.queryForObject(query, new CarRowMapper(), vin);
    }

    public void createCar(int carModelId, FuelType fuelType,
                          String licensePlate, String vin, String equipmentLevel,
                          long km, double registrationFee, double steelPrice,
                          int co2Emission, String isRented) {
        String query = "INSERT INTO car(car_model_id, fuel_type, license_plate, vin, equipment_level, km, registration_fee, steel_price, co2_emission, is_rented)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, carModelId, fuelType.name(), licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
    }

    public void updateCar(int carId, int carModelId, FuelType fuelType, String licensePlate,
                           String vin, String equipmentLevel, long km, double registrationFee,
                           double steelPrice, int co2Emission) {
        String query = "UPDATE car SET car_model_id = ?, fuel_type = ?, license_plate = ?, vin = ?, equipment_level = ?," +
                        "km = ?, registration_fee = ?, steel_price = ?, co2_emission = ? WHERE id = ?";
        jdbcTemplate.update(query, carModelId, fuelType.name(), licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, carId);
    }

    public void deleteCarById(int carId) {
        String query = "DELETE FROM car WHERE id = ?;";
        jdbcTemplate.update(query, carId);
    }

    public void createCarModel(CarBrand carBrand, String carModelName, CarType carType) {
        String query = "INSERT INTO car_model(brand, model_name, car_type) " +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(query, carBrand.getBrand(),carModelName, carType.name());
    }

    // High-level dependent on low-level
    // Creating the instance of CarModelRowMapper directly in the method
    /*public CarModel getCarModelById(int carModelId) {
        String query = "SELECT * FROM car_model WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new CarModelRowMapper(), carModelId);
    }*/

    // High-level not dependent on low-level, DIP :-)
    // Using DI to provide carModelRowMapper as a dependency
    public CarModel getCarModelById(int carModelId) {
        String query = "SELECT * FROM car_model WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, carModelRowMapper, carModelId);
    }

    public Car getCarById(int carId) {
        String query = "SELECT * FROM car WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new CarRowMapper(), carId);
    }

    public List<CarModel> getAllCarModelsByBrand(CarBrand carBrand) {
        String query = "SELECT * FROM car_model WHERE brand = ?;";
        return jdbcTemplate.query(query, new CarModelRowMapper(), carBrand.getBrand());
    }

    // Overloaded method
    public List<CarModel> getAllCarModelsByBrand(String carBrand) {
        String query = "SELECT * FROM car_model WHERE brand = ?;";
        return jdbcTemplate.query(query, new CarModelRowMapper(), carBrand);
    }

    public List<CarBrand> getAllCarBrands() {
        String query = "SELECT * FROM car_brand;";
        BeanPropertyRowMapper<CarBrand> carBrandBeanPropertyRowMapper = new BeanPropertyRowMapper<>(CarBrand.class);
        return jdbcTemplate.query(query, carBrandBeanPropertyRowMapper);
    }

    public List<FuelType> getAllFuelTypes() {
        String query = "SELECT * FROM fuel_type;";
        //BeanPropertyRowMapper<FuelType> fuelTypeBeanPropertyRowMapper = new BeanPropertyRowMapper<>(FuelType.class);
        return jdbcTemplate.query(query, new FuelTypeRowMapper());
    }

    public List<CarModel> getAllCarModels() {
        String query = "SELECT * FROM car_model;";
        return jdbcTemplate.query(query, new CarModelRowMapper());
    }

    public List<CarType> getAllCarTypes() {
        String query = "SELECT * FROM car_type;";
        return jdbcTemplate.query(query, new CarTypeRowMapper());
    }

    public List<Car> getAllCars() {
        String query = "SELECT * FROM car;";
        return jdbcTemplate.query(query, new CarRowMapper());
    }

    public List<Car> getAllAvailableCars() {
        String query = "SELECT * FROM car WHERE is_rented = 'false';";
        return jdbcTemplate.query(query, new CarRowMapper());
    }

    public CarBrand getCarBrandByBrand(CarBrand carBrand) {
        String query = "SELECT * FROM car_brand WHERE brand = ?;";
        BeanPropertyRowMapper<CarBrand> carBrandBeanPropertyRowMapper = new BeanPropertyRowMapper<>(CarBrand.class);
        return jdbcTemplate.queryForObject(query, carBrandBeanPropertyRowMapper, carBrand.getBrand());
    }

    public void createCarBrand(CarBrand carBrand) {
        String query = "INSERT INTO car_brand(brand) " +
                "VALUES (?);";
        jdbcTemplate.update(query, carBrand.getBrand());
    }

    public void updateCarBrand(CarBrand newCarBrand, CarBrand oldCarBrand) {
        String query = "UPDATE car_brand SET brand = ? WHERE brand = ?;";
        jdbcTemplate.update(query, newCarBrand.getBrand(), oldCarBrand.getBrand());
    }

    public void deleteCarBrand(CarBrand carBrand) {
        String query ="DELETE FROM car_brand WHERE brand =?;";
        jdbcTemplate.update(query, carBrand.getBrand());
    }

    public void updateCarModel(int carModelId, String modelName, CarType carType) {
        String query = "UPDATE car_model SET model_name = ?, car_type = ? WHERE id = ?;";
        jdbcTemplate.update(query, modelName, carType.name(), carModelId);
    }

    public void deleteCarModelById(int carModelId) {
        String query = "DELETE FROM car_model WHERE id = ?;";
        jdbcTemplate.update(query, carModelId);
    }


    // Rent car
    public void rentCar(int carId) {
        String query = "UPDATE car SET is_rented = 'true' WHERE id = ?;";
        jdbcTemplate.update(query, carId);
    }

    // 'Unrent' car
    public void makeCarAvailable(int carId) {
        String query = "UPDATE car SET is_rented = 'false' WHERE id = ?;";
        jdbcTemplate.update(query, carId);
    }

    // Check if car is rented
    public void getRentedCarById(int carId) {
        String query = "SELECT * FROM car WHERE is_rented = 'true' AND id = ?;";
        jdbcTemplate.queryForObject(query, new CarRowMapper(), carId);
    }
}
