package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.services.SalesPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/prepare_update")
    public String prepareUpdate(@RequestParam int id, Model model) {
        model.addAttribute(salesPersonService.prepareUpdate(id));
        return "home/update";
    }
    @PostMapping("/update")
    public String update(@RequestParam int id, @RequestParam String firstName, @RequestParam String lastName,
                         @RequestParam String phoneNumber, @RequestParam String email, @RequestParam String address, @RequestParam int zipCode) {
        salesPersonService.update(id, firstName, lastName, phoneNumber, email, address, zipCode);
        return "redirect:/";
    }
}