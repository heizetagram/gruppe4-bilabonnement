package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Employee;
import com.example.gruppe4bilabonnement.services.AdminService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String login() {
        return "home/login";
    }

    // Attempt to find a user with given prompts
    @PostMapping("/login_attempt")
    public String loginAttempt(@RequestParam String email, @RequestParam String employeePassword, Model model, HttpServletResponse response) {
        model.addAttribute("invalidEmployeeInfo", "E-mail eller adgangskode er forkert");
        Employee employee = adminService.getEmployeeByEmailAndPassword(email, employeePassword);

        Cookie cookie = new Cookie("employeeRole", employee.getEmployeeRole());
        response.addCookie(cookie);

        // Returns an invalid login message if the user-login doesn't exist or if the email doesn't include a '.'
        if (employee.getEmployeeRole().equals("N/A") || !email.contains(".")) {
            return "home/login";
        } else {
            return "redirect:/employee_frontpage";
        }
    }

    // Delete 'employeeRole' cookie and log out
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("employeeRole", null);
        cookie.setMaxAge(0); // Tell browser to delete cookie
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/employee_frontpage")
    public String employeeFrontpage(@CookieValue(name = "employeeRole") String cookieValue) {
        String employeeView;
        switch (cookieValue) {
            case "SALESPERSON" -> employeeView = "/salesperson/frontpage";
            case "MECHANIC" -> employeeView = "/mechanic/frontpage";
            case "BUSINESS_DEV" -> employeeView = "/business_dev/frontpage";
            case "ADMIN" -> employeeView = "/admin/frontpage";
            default -> employeeView = "redirect:/";
        }
        return employeeView;
    }
}