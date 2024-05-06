package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.services.SalesPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/salesperson")

public class SalesPersonController {
    @Autowired
    private SalesPersonService salesPersonService;

    @GetMapping("/new_customer")
    public String insert(){
        return "salesperson/new_customer";
    }
    @PostMapping("/insert")
    public String insert(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String phoneNumber,
                         @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {
        salesPersonService.insert(firstName, lastName, phoneNumber, email, address, zipCode);
        return "redirect:/new_lease_agreement";
    }
    @GetMapping("/prepare_update/{id}")
    public String prepareUpdate(@PathVariable int id, Model model) {
        model.addAttribute("customer", salesPersonService.prepareUpdate(id));
        return "salesperson/update_customer";
    }
    @PostMapping("/update_customer")
    public String update(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {
        salesPersonService.update(id, firstName, lastName, phoneNumber, email, address, zipCode);
        return "redirect:/";
    }
    @GetMapping("/customer_overview")
    public String showCustomers(Model model) {
        List<Customer> customers = salesPersonService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "salesperson/customer_overview";
    }



}
