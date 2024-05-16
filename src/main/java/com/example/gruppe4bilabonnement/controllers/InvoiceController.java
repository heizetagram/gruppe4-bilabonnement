package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Car;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.services.CarService;
import com.example.gruppe4bilabonnement.services.InvoiceService;
import com.example.gruppe4bilabonnement.services.MechanicService;
import com.example.gruppe4bilabonnement.services.SalesPersonService;
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

    @GetMapping("/prepare_new_invoice")
    public String prepareNewInvoice(@RequestParam int leaseAgreementId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            // Lease agreement
            LeaseAgreement leaseAgreement = salesPersonService.getLeaseAgreementById(leaseAgreementId);
            model.addAttribute("leaseAgreement", leaseAgreement);

            // Down payment
            Car car = carService.getCarById(leaseAgreement.getCarId());
            CarModel carModel = carService.getCarModelById(car.getCarModelId());
            double downPayment = invoiceService.getDownPaymentByCarType(carModel.getCarType());
            model.addAttribute("downPayment", downPayment);

            // Get lease period
            long period = invoiceService.getLeasePeriodMonths(leaseAgreement);
            model.addAttribute("period", period);

            // Gross price
            double grossPrice = invoiceService.calculateGrossPriceForLeaseAgreement(leaseAgreement);
            model.addAttribute("grossPrice", grossPrice);

            // Net price
            double netPrice = invoiceService.calculateNetPrice(grossPrice, carModel);
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
    public String createInvoice(@RequestParam int leaseAgreementId, @RequestParam double downPayment, @RequestParam double grossPrice, @RequestParam double netPrice) {
        LocalDate createdAt = invoiceService.getCurrentDate();
        invoiceService.createInvoice(leaseAgreementId, downPayment, grossPrice, netPrice, createdAt);
        return "redirect:/salesperson/customer_overview";
    }
}
