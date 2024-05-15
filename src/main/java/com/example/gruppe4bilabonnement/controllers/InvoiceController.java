package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.models.LeaseAgreement;
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

    @GetMapping("/prepare_new_invoice")
    public String prepareNewInvoice(@RequestParam int leaseAgreementId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            LeaseAgreement leaseAgreement = salesPersonService.getLeaseAgreementById(leaseAgreementId);
            model.addAttribute("leaseAgreement", leaseAgreement);

            double totalPrice = invoiceService.calculatePriceForLeaseAgreement(leaseAgreement);
            model.addAttribute("totalPrice", totalPrice);

            long carId = leaseAgreement.getCarId();
            List<DamageReport> damageReports = mechanicService.getAllDamageReportsByCarId(carId);
            model.addAttribute("damageReports", damageReports);

            return "salesperson/invoice/create_invoice";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/create_invoice")
    public String createInvoice(@RequestParam int leaseAgreementId, @RequestParam double price) {
        LocalDate createdAt = LocalDate.now();
        invoiceService.createInvoice(leaseAgreementId, price, createdAt);
        return "redirect:/salesperson/customer_overview";
    }
}
