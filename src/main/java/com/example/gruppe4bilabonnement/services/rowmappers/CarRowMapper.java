package com.example.gruppe4bilabonnement.services.rowmappers;

import com.example.gruppe4bilabonnement.models.*;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {
    // Create a row mapper for car model, that sets the car brand and car types as instances of their classes
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        int carModelId = rs.getInt("car_model_id");
        FuelType fuelType = new FuelType(rs.getString("fuel_type"));
        String licensePlate = rs.getString("license_plate");
        String vin = rs.getString("vin");
        String equipmentLevel = rs.getString("equipment_level");
        long km = rs.getLong("km");
        double registrationFee = rs.getDouble("registration_fee");
        double steelPrice = rs.getDouble("steel_price");
        int co2Emission = rs.getInt("co2_emission");
        boolean isRented = rs.getBoolean("is_rented");
        return new Car(id, carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
    }
}
