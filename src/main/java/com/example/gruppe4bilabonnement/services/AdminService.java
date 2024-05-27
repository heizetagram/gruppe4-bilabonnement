package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.Employee;
import com.example.gruppe4bilabonnement.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public void createEmployee(String email, String employeePassword, int employeeRoleKey) {
        String employeeRole = getEmployeeRoleValueByKey(employeeRoleKey);
        adminRepository.createEmployee(email, employeePassword, employeeRole);
    }

    // Receive a key and return the value from a HashMap
    private String getEmployeeRoleValueByKey(int key) {
        Map<Integer, String> employeeRoles = new HashMap<>();
        employeeRoles.put(1, "SALESPERSON");
        employeeRoles.put(2, "MECHANIC");
        employeeRoles.put(3, "BUSINESS_DEV");
        employeeRoles.put(4, "ADMIN");
        return employeeRoles.get(key);
    }

    // Try to find an existing employee. If the employee doesn't exist, then return an empty employee.
    public Employee getEmployeeByEmailAndPassword(String email, String employeePassword) {
        try {
            return adminRepository.getEmployeeByEmailAndPassword(email, employeePassword);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Rename til checkIfEmailIsRegistered
    public boolean isEmailRegistered(String email) {
        boolean isEmailRegistered;
        try {
            adminRepository.getEmployeeByEmail(email);
            isEmailRegistered = true;
        } catch (EmptyResultDataAccessException e) {
            isEmailRegistered = false;
        }
        return isEmailRegistered;
    }

    public List<Employee> getAllEmployees() {
        return adminRepository.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return adminRepository.getEmployeeById(id);
    }

    public void updateEmployee(int id, String email, String employeePassword, String employeeRole) {
        adminRepository.updateEmployee(id, email, employeePassword, employeeRole);
    }

    public void deleteEmployeeById(int id) {
        adminRepository.deleteEmployeeById(id);
    }
}
