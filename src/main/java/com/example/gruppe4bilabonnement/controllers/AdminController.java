package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Employee;
import com.example.gruppe4bilabonnement.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
            adminService.createEmployee(email, employeePassword, employeeRole);
            return "redirect:/admin/employee_overview";
        }
        return "admin/create_employee";
    }

    @GetMapping("/employee_overview")
    public String showEmployees(Model model) {
        List<Employee> employees = adminService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "admin/employee_overview";
    }

    @GetMapping("/prepare_employee_update")
    public String prepareEmployeeUpdate(@RequestParam int employeeId, Model model) {
        Employee employee = adminService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "admin/update_employee";
    }

    @PostMapping("/update_employee")
    public String updateEmployee(@RequestParam int employeeId, @RequestParam String email, @RequestParam String employeePassword, @RequestParam String employeeRole, Model model) {
        if (!email.contains(".")) {
            Employee employee = adminService.getEmployeeById(employeeId);
            model.addAttribute("employee", employee);
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
            return "admin/update_employee";
        } else {
            adminService.updateEmployee(employeeId, email, employeePassword, employeeRole);
            return "redirect:/admin/employee_overview";
        }
    }

    @GetMapping("/prepare_employee_deletion")
    public String prepareEmployeeDeletion(@RequestParam int employeeId, Model model) {
        Employee employee = adminService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "admin/delete_employee";
    }

    @PostMapping("/delete_employee")
    public String deleteEmployee(@RequestParam int employeeId) {
        adminService.deleteEmployeeById(employeeId);
        return "redirect:/admin/employee_overview";
    }
}

