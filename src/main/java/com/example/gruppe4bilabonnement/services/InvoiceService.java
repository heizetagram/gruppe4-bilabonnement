package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.models.enums.CarType;
import com.example.gruppe4bilabonnement.repositories.CarRepository;
import com.example.gruppe4bilabonnement.repositories.InvoiceRepository;
import com.example.gruppe4bilabonnement.repositories.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    MechanicRepository mechanicRepository;

    @Autowired
    CarRepository carRepository;

    public void createInvoice(int leaseAgreementId, double downPayment, double grossPrice, double netPrice, LocalDate createdAt) {
        invoiceRepository.createInvoice(leaseAgreementId, downPayment, grossPrice, netPrice, createdAt);
    }

    public double calculateGrossPriceForLeaseAgreement(LeaseAgreement leaseAgreement) {
        double grossPrice = 0;

        // Get the car and its car model
        int carId = leaseAgreement.getCarId();
        Car car = carRepository.getCarById(leaseAgreement.getCarId());

        // Get period of lease agreement in months
        long leasePeriodInMonths = getLeasePeriod(leaseAgreement.getStartDate(), leaseAgreement.getEndDate());
        // Calculate price of lease period
        grossPrice += leaseAgreement.getPrice() * leasePeriodInMonths;

        // Calculate damage report prices
        grossPrice += calculateTotalDamageReportPriceForCarId(carId);

        return grossPrice;
    }

    // Calculate net price (25% VAT)
    public double calculateNetPrice(double grossPrice, CarModel carModel) {
        // Add 25% VAT
        grossPrice = grossPrice * 1.25;

        // Calculate down payment by car type
        return grossPrice += getDownPaymentByCarType(carModel.getCarType());
    }

    private double calculateTotalDamageReportPriceForCarId(int carId) {
        double totalPrice = 0;
        List<DamageReport> damageReports = mechanicRepository.getAllDamageReportsByCarId(carId);

        for (DamageReport damageReport : damageReports) {
            totalPrice += damageReport.getPrice();
        }
        return totalPrice;
    }

    // Calculate steel price fees
    private double calculateSteelPriceFees(double steelPrice) {
        double fees;
        if (steelPrice <= 65000) {
            fees = steelPrice * 1.25;
        } else if (steelPrice <= 202200) {
            fees = steelPrice * 1.85;
        } else {
            fees = steelPrice * 2.5;
        }
        return fees;
    }

    private long getLeasePeriod(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.MONTHS.between(startDate, endDate);
    }

    public long getLeasePeriodMonths(LeaseAgreement leaseAgreement) {
        return getLeasePeriod(leaseAgreement.getStartDate(), leaseAgreement.getEndDate());
    }

    public double getDownPaymentByCarType(CarType carType) {
        return switch (carType) {
            case FAMILY -> 5000;
            case SPORT -> 10000;
            case LUXURY -> 15000;
        };
    }

    public String getDanishFormattedDate(LocalDate date) {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM-yyyy");
        return date.format(dateTimeFormatter);
    }

    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
