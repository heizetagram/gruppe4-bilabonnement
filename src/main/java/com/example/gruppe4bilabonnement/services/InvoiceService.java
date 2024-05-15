package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.repositories.InvoiceRepository;
import com.example.gruppe4bilabonnement.repositories.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    MechanicRepository mechanicRepository;

    public void createInvoice(int leaseAgreementId, double price, LocalDate createdAt) {
        invoiceRepository.createInvoice(leaseAgreementId, price, createdAt);
    }

    public double calculatePriceForLeaseAgreement(LeaseAgreement leaseAgreement) {
        double totalPrice = 0;
        // Inconsistency at ID-værdierne er Long i DamageReport. Måske burde vi lave alle ID'er til long i stedet
        long carId = leaseAgreement.getCarId();

        totalPrice += leaseAgreement.getPrice();
        totalPrice += leaseAgreement.getSteelPrice();
        totalPrice += calculateTotalDamageReportPriceForCarId(carId);

        return totalPrice;
    }

    private double calculateTotalDamageReportPriceForCarId(long carId) {
        double totalPrice = 0;
        List<DamageReport> damageReports = mechanicRepository.getAllDamageReportsByCarId(carId);

        for (DamageReport damageReport : damageReports) {
            totalPrice += damageReport.getPrice();
        }
        return totalPrice;
    }
}
