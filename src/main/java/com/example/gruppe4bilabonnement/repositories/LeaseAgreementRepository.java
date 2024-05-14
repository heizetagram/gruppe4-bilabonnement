package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.services.rowmappers.LeaseAgreementRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaseAgreementRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void createLeaseAgreement(int customerId, LeaseAgreement leaseAgreement) {
        String query = "INSERT INTO lease_agreement (customer_id, car_model_id, license_plate, vin, brand, model, " +
                "equipment_level, steel_price, registration_fee, co2_emission, is_rented) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(query, customerId, leaseAgreement.getCarModelId(), leaseAgreement.getLicensePlate(),
                leaseAgreement.getVin(), leaseAgreement.getBrand(), leaseAgreement.getModel(),
                leaseAgreement.getEquipmentLevel(), leaseAgreement.getSteelPrice(),
                leaseAgreement.getRegistrationFee(), leaseAgreement.getCo2Emission(),
                leaseAgreement.isRented() ? "true" : "false");
    }
    public void updateLeaseAgreement(LeaseAgreement leaseAgreement) {
        String query = "UPDATE lease_agreement SET customer_id = ?, car_model_id = ?, license_plate = ?, " +
                "vin = ?, brand = ?, model = ?, equipment_level = ?, steel_price = ?, registration_fee = ?, " +
                "co2_emission = ?, is_rented = ? WHERE id = ?;";
        jdbcTemplate.update(query, leaseAgreement.getCustomerId(), leaseAgreement.getCarModelId(),
                leaseAgreement.getLicensePlate(), leaseAgreement.getVin(), leaseAgreement.getBrand(),
                leaseAgreement.getModel(), leaseAgreement.getEquipmentLevel(), leaseAgreement.getSteelPrice(),
                leaseAgreement.getRegistrationFee(), leaseAgreement.getCo2Emission(),
                leaseAgreement.isRented() ? "true" : "false", leaseAgreement.getId());
    }
    public void deleteById(int id) {
        String query = "DELETE FROM lease_agreement WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }
    public LeaseAgreement findById(int id) {
        String query = "SELECT * FROM lease_agreement WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new LeaseAgreementRowMapper(), id);
    }
    public List<LeaseAgreement> findAll() {
        String query = "SELECT * FROM lease_agreement;";
        return jdbcTemplate.query(query, new LeaseAgreementRowMapper());
    }

}
