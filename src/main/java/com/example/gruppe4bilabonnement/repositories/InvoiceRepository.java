package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public Invoice getInvoiceByLeaseAgreementId(int leaseAgreementId) {
        String query = "SELECT * FROM invoice WHERE lease_agreement_id = ?;";
        RowMapper<Invoice> invoiceRowMapper = new BeanPropertyRowMapper<>(Invoice.class);
        return jdbcTemplate.queryForObject(query, invoiceRowMapper, leaseAgreementId);
    }
}
