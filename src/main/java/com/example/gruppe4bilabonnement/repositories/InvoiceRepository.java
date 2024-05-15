package com.example.gruppe4bilabonnement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class InvoiceRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createInvoice(int leaseAgreementId, double price, LocalDate createdAt) {
        String query = "INSERT INTO invoice(lease_agreement_id, price, created_at) " +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(query, leaseAgreementId, price, createdAt);
    }


}
