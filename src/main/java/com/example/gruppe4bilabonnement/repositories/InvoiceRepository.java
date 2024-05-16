package com.example.gruppe4bilabonnement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class InvoiceRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createInvoice(int leaseAgreementId, double downPayment, double grossPrice, double netPrice, LocalDate createdAt) {
        String query = "INSERT INTO invoice(lease_agreement_id, down_payment, gross_price, net_price, created_at) " +
                "VALUES (?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, leaseAgreementId, downPayment, grossPrice, netPrice, createdAt);
    }
}
