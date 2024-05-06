package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String createEmployee(@RequestParam String email, @RequestParam String employeePassword, @RequestParam String employeeRole, Model model) {
        boolean isEmailRegistered = adminService.isEmailRegistered(email);

        if (isEmailRegistered) {
            // Send an invalid message if e-mail is already registered in the system
            model.addAttribute("emailAlreadyRegistered", "E-mail er allerede i brug");
        } else if (!email.contains(".")) {
            // Send an invalid message if e-mail doesn't contain "."
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
        } else {
            // Send a success message if user is created
            adminService.createEmployee(email, employeePassword, employeeRole);
            model.addAttribute("success", "Bruger tilføjet");
        }
        return "admin/create_employee";
    }
}

