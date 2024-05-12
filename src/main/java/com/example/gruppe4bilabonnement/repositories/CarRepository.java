package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.services.rowmappers.CarModelRowMapper;
import com.example.gruppe4bilabonnement.services.rowmappers.CarRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CarRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int getCarModelIdByBrandAndModelName(CarBrand carBrand, String carModelName) {
        String query = "SELECT id FROM car_model WHERE brand = ? AND model = ?;";
        BeanPropertyRowMapper<Integer> carModelBeanPropertyRowMapper = new BeanPropertyRowMapper<>(Integer.class);
        return jdbcTemplate.queryForObject(query, carModelBeanPropertyRowMapper, carBrand.getBrand(), carModelName);
    }

    public CarModel getCarModelById(int carModelId) {
        String query = "SELECT * FROM car_model WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new CarModelRowMapper());
    }

    public void createCar(int carModelId, FuelType fuelType,
                          String licensePlate, String vin, String equipmentLevel,
                          long km, double registrationFee, double steelPrice,
                          int co2Emission, String isRented) {
        String query = "INSERT INTO car(car_model_id, fuel_type, license_plate, vin, equipment_level, km, registration_fee, steel_price, co2_emission, is_rented)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, carModelId, fuelType.getFuelType(), licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
    }

    public void createCarModel(CarBrand carBrand, String carModelName, CarType carType) {
        String query = "INSERT INTO car_model(brand, model_name, car_type) " +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(query, carBrand.getBrand(),carModelName, carType.getCarType());
    }

    public CarModel getCarModelByCarModelId(int carModelId) {
        String query = "SELECT * FROM car_model WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new CarModelRowMapper(), carModelId);
    }

    public List<CarModel> getAllCarModelsByBrand(CarBrand carBrand) {
        String query = "SELECT * FROM car_model WHERE brand = ?;";
        return jdbcTemplate.query(query, new CarModelRowMapper(), carBrand.getBrand());
    }

    public List<CarBrand> getAllCarBrands() {
        String query = "SELECT * FROM car_brand;";
        BeanPropertyRowMapper<CarBrand> carBrandBeanPropertyRowMapper = new BeanPropertyRowMapper<>(CarBrand.class);
        return jdbcTemplate.query(query, carBrandBeanPropertyRowMapper);
    }

    public List<FuelType> getAllFuelTypes() {
        String query = "SELECT * FROM fuel_type;";
        BeanPropertyRowMapper<FuelType> fuelTypeBeanPropertyRowMapper = new BeanPropertyRowMapper<>(FuelType.class);
        return jdbcTemplate.query(query, fuelTypeBeanPropertyRowMapper);
    }

    public List<CarModel> getAllCarModels() {
        String query = "SELECT * FROM car_model;";
        return jdbcTemplate.query(query, new CarModelRowMapper());
    }

    public List<CarType> getAllCarTypes() {
        String query = "SELECT * FROM car_type;";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(CarType.class));
    }

    public List<Car> getAllCars() {
        String query = "SELECT * FROM car;";
        return jdbcTemplate.query(query, new CarRowMapper());
    }
}
