package com.example.gruppe4bilabonnement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessdeveloperRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
        // gennemsnitlg betaling
    public double calculateAveragePaymentTime() {
        String query = "SELECT AVG(payment_time) FROM lease_agreements";
        return jdbcTemplate.queryForObject(query, Double.class);
    }
        // gennemsnitlig transport-tid
    public double calculateAverageTransportTime() {
        String query = "SELECT AVG(transport_time) FROM lease_agreements";
        return jdbcTemplate.queryForObject(query, Double.class);
    }
        // Hvor hurtigt bliver biler leaset.
    public double calculateLeasingSpeedForCar(long carId) {
        String query = "SELECT leasing_speed FROM cars WHERE car_id = ?";
        return jdbcTemplate.queryForObject(query, Double.class, carId);
    }

    // hvor mange biler er udlejet
    public int countRentedCars() {
        String query = "SELECT COUNT(*) FROM cars WHERE is_rented = 'TRUE'";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }
        // total pris på udlejede biler
    public double calculateTotalPriceOfRentedCars() {
        String query = "SELECT SUM(price) FROM cars WHERE is_rented = 'TRUE'";
        return jdbcTemplate.queryForObject(query, Double.class);
    }
}
