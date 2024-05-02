package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public void createEmployee(String email, String employeePassword, String employeeRole) {
        adminRepository.createEmployee(email, employeePassword, employeeRole);
    }
}
