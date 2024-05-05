package com.example.gruppe4bilabonnement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BusinessdeveloperRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
        // gennemsnitlg betaling
        public Double calculateAveragePaymentTime() {
            String query = "SELECT AVG(payment_time) FROM lease_agreement";
            Double averagePaymentTime = jdbcTemplate.queryForObject(query, Double.class);
            return averagePaymentTime != null ? averagePaymentTime : 0.0; // Returner 0.0 hvis null
        }

    // gennemsnitlig transport-tid
    public Double calculateAverageTransportTime() {
        String query = "SELECT AVG(transport_time) FROM lease_agreement";
        Double averageTransportTime = jdbcTemplate.queryForObject(query, Double.class);
        return averageTransportTime != null ? averageTransportTime : 0.0; // Return 0.0 if null
    }

    // Hvor hurtigt bliver biler leaset.
    public Double calculateLeasingSpeedForCar(long carId) {
        String query = "SELECT leasing_speed FROM cars WHERE car_id = ?";
        Double leasingSpeed = jdbcTemplate.queryForObject(query, Double.class, carId);
        return leasingSpeed != null ? leasingSpeed : 0.0; // Return 0.0 if null
    }

    // hvor mange biler er udlejet
    public int countRentedCars() {
        String query = " SELECT COUNT(*) FROM car WHERE is_rented = 'TRUE'";
        Integer rentedCarsCount = jdbcTemplate.queryForObject(query, Integer.class);
        return rentedCarsCount != null ? rentedCarsCount : 0; // Return 0 if null
    }
        // total pris på udlejede biler
        public Double calculateTotalPriceOfRentedCars() {
            String query = "SELECT SUM(price) FROM lease_agreement WHERE car_id IN (SELECT id FROM car WHERE is_rented = 'TRUE')\n";
            Double totalPrice = jdbcTemplate.queryForObject(query, Double.class);
            return totalPrice != null ? totalPrice : 0.0; // Return 0.0 if null
        }
}
