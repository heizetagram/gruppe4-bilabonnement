package com.example.gruppe4bilabonnement;

import com.example.gruppe4bilabonnement.models.Employee;
import com.example.gruppe4bilabonnement.repositories.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AdminRepositoryIntegrationTest {

    @Autowired
    private AdminRepository adminRepository;

    // Make sure to have a fresh DB before you run the following

    @Test
    public void testGetEmployeeByEmail() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        String role = "ADMIN";
        adminRepository.createEmployee(email, password, role);

        // Act
        // Employee employee = adminRepository.getEmployeeById(1); We use email because id gets auto incremented in the DB
        Employee employee = adminRepository.getEmployeeByEmail(email);

        // Assert
        assertNotNull(employee);
        assertEquals(email, employee.getEmail());
        assertEquals(password, employee.getEmployeePassword());
        assertEquals(role, employee.getEmployeeRole());

        // Delete the employee
        adminRepository.deleteEmployeeById(employee.getId());
    }
}