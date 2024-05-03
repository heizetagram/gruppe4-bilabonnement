package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/prepare_new_employee")
    public String prepareNewEmployee() {
        return "admin/create_employee";
    }

    @PostMapping("/create_employee")
    public String createEmployee(@RequestParam String email, @RequestParam String employeePassword, @RequestParam String employeeRole) {
        adminService.createEmployee(email, employeePassword, employeeRole);
        return "redirect:/";
    }
}

