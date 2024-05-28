package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.Employee;
import com.example.gruppe4bilabonnement.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/frontpage")
    public String frontpage() {
        return "/admin/frontpage";
    }

    @GetMapping("/prepare_new_employee")
    public String prepareNewEmployee(@CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("ADMIN")) {
            return "admin/create_employee";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/create_employee")
    public String createEmployee(@RequestParam String email, @RequestParam String employeePassword, @RequestParam int employeeRoleKey, Model model) {
        boolean isEmailRegistered = adminService.isEmailRegistered(email);

        if (isEmailRegistered) {
            // Send an invalid message if e-mail is already registered in the system
            model.addAttribute("emailAlreadyRegistered", "E-mail er allerede i brug");
        } else if (!email.contains(".")) {
            // Send an invalid message if e-mail doesn't contain "."
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
        } else {
            adminService.createEmployee(email, employeePassword, employeeRoleKey);
            return "redirect:/admin/employee_overview";
        }
        return "admin/create_employee";
    }

    // Show all employees
    @GetMapping("/employee_overview")
    public String showEmployees(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("ADMIN")) {
            List<Employee> employees = adminService.getAllEmployees();
            model.addAttribute("employees", employees);
            return "admin/employee_overview";
        } else {
            return "redirect:/";
        }
    }

    // Prepare employee update
    @GetMapping("/prepare_employee_update")
    public String prepareEmployeeUpdate(@RequestParam int employeeId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("ADMIN")) {
            Employee employee = adminService.getEmployeeById(employeeId);
            model.addAttribute("employee", employee);
            return "admin/update_employee";
        } else {
            return "redirect:/";
        }
    }

    // Update employee
    @PostMapping("/update_employee")
    public String updateEmployee(@RequestParam int employeeId, @RequestParam String email, @RequestParam String employeePassword, @RequestParam String employeeRole, Model model) {
        // Send an error message if email format is incorrect
        if (!email.contains(".")) {
            Employee employee = adminService.getEmployeeById(employeeId);
            model.addAttribute("employee", employee);
            model.addAttribute("invalidInfo", "E-mail skal indeholde \".\"");
            return "admin/update_employee";
        } else {
            // Else update the employee
            adminService.updateEmployee(employeeId, email, employeePassword, employeeRole);
            return "redirect:/";
        }
    }

    // Prepare employee deletion - 'Are you sure?'
    @GetMapping("/prepare_employee_deletion")
    public String prepareEmployeeDeletion(@RequestParam int employeeId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("ADMIN")) {
            Employee employee = adminService.getEmployeeById(employeeId);
            model.addAttribute("employee", employee);
            return "admin/delete_employee";
        } else {
            return "redirect:/";
        }
    }

    // Delete employee
    @PostMapping("/delete_employee")
    public String deleteEmployee(@RequestParam int employeeId) {
        adminService.deleteEmployeeById(employeeId);
        return "redirect:/admin/employee_overview";
    }
}

