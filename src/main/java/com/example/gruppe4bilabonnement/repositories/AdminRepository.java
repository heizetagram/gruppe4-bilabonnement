package com.example.gruppe4bilabonnement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Creates a new Employee
    public void createEmployee(String email, String employeePassword, String employeeRole) {
        String query = "INSERT INTO employee(email, employee_password, employee_role)" +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(query, email, employeePassword, employeeRole);
    }
}
