package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class InvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    SalesPersonService salesPersonService;

    @Autowired
    MechanicService mechanicService;

    @Autowired
    CarService carService;
    @Autowired
    private LeaseAgreementService leaseAgreementService;

    @GetMapping("/prepare_new_invoice")
    public String prepareNewInvoice(@RequestParam int leaseAgreementId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            // Current date
            LocalDate currentDate = invoiceService.getCurrentDate();
            String currentDateFormatted = invoiceService.getDanishFormattedDate(currentDate);
            model.addAttribute("currentDate", currentDateFormatted);

            // Lease agreement
            LeaseAgreement leaseAgreement = salesPersonService.getLeaseAgreementById(leaseAgreementId);
            model.addAttribute("leaseAgreement", leaseAgreement);

            // Customer
            Customer customer = salesPersonService.getCustomerById(leaseAgreement.getCustomerId());
            model.addAttribute("customer", customer);

            // Zip code
            ZipCode zipCode = salesPersonService.getZipCodeByZipCode(customer.getZipCode());
            model.addAttribute("zipCode", zipCode);

            // Car
            Car car = carService.getCarById(leaseAgreement.getCarId());
            model.addAttribute("car", car);

            // Car Model
            CarModel carModel = carService.getCarModelById(car.getCarModelId());
            model.addAttribute("carModel", carModel);

            // Down payment
            double downPayment = invoiceService.getDownPaymentByCarType(carModel.getCarType());
            model.addAttribute("downPayment", downPayment);

            // Start and end date for lease period
            String startDateFormatted = invoiceService.getDanishFormattedDate(leaseAgreement.getStartDate());
            String endDateFormatted = invoiceService.getDanishFormattedDate(leaseAgreement.getEndDate());
            model.addAttribute("startDate", startDateFormatted);
            model.addAttribute("endDate", endDateFormatted);


            // Get lease period
            long period = invoiceService.getLeasePeriodMonths(leaseAgreement);
            model.addAttribute("period", period);

            // Gross price
            double grossPrice = invoiceService.calculateGrossPriceForLeaseAgreement(leaseAgreement);
            model.addAttribute("grossPrice", grossPrice);

            // Net price
            double netPrice = invoiceService.calculateNetPrice(grossPrice, car, carModel);
            model.addAttribute("netPrice", netPrice);

            // Damage reports
            int carId = leaseAgreement.getCarId();
            List<DamageReport> damageReports = mechanicService.getAllDamageReportsByCarId(carId);
            model.addAttribute("damageReports", damageReports);

            return "salesperson/invoice/create_invoice";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/create_invoice")
    public String createInvoice(@RequestParam int leaseAgreementId, @RequestParam double downPayment, @RequestParam double grossPrice, @RequestParam double netPrice, Model model) {
        LocalDate createdAt = invoiceService.getCurrentDate();
        invoiceService.createInvoice(leaseAgreementId, downPayment, grossPrice, netPrice, createdAt);

        LeaseAgreement leaseAgreement = salesPersonService.getLeaseAgreementById(leaseAgreementId);
        // Make car available again after creating invoice
        carService.makeCarAvailable(leaseAgreement.getCarId());
        return "redirect:/salesperson/customer_overview";
    }

    @GetMapping("/show_invoice")
    public String showInvoice(@RequestParam int leaseAgreementId, Model model) {
        LeaseAgreement leaseAgreement = leaseAgreementService.getLeaseAgreementById(leaseAgreementId);
        Customer customer = salesPersonService.getCustomerById(leaseAgreement.getCustomerId());
        ZipCode zipCode = salesPersonService.getZipCodeByZipCode(customer.getZipCode());
        Car car = carService.getCarById(leaseAgreement.getCarId());
        CarModel carModel = carService.getCarModelById(car.getCarModelId());
        Invoice invoice = invoiceService.getInvoiceByLeaseAgreementId(leaseAgreementId);
        List<DamageReport> damageReports = mechanicService.getAllDamageReportsByCarId(car.getId());
        long period = invoiceService.getLeasePeriodMonths(leaseAgreement);

        model.addAttribute("leaseAgreement", leaseAgreement);
        model.addAttribute("period", period);
        model.addAttribute("customer", customer);
        model.addAttribute("zipCode", zipCode);
        model.addAttribute("car", car);
        model.addAttribute("carModel", carModel);
        model.addAttribute("damageReports", damageReports);
        model.addAttribute("invoice", invoice);
        return "salesperson/invoice/show_invoice";
    }
}
