package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Employee> beanPropertyRowMapper = new BeanPropertyRowMapper<>(Employee.class);

    // Creates a new Employee
    public void createEmployee(String email, String employeePassword, String employeeRole) {
        String query = "INSERT INTO employee(email, employee_password, employee_role)" +
                "VALUES (?, ?, ?);";
        jdbcTemplate.update(query, email, employeePassword, employeeRole);
    }

    public Employee getEmployeeByEmailAndPassword(String email, String employeePassword) {
        String query = "SELECT * FROM employee WHERE email = ? and employee_password = ?;";
        return jdbcTemplate.queryForObject(query, beanPropertyRowMapper, email, employeePassword);
    }

    public Employee getEmployeeByEmail(String email) {
        String query = "SELECT * FROM employee WHERE email = ?;";
        return jdbcTemplate.queryForObject(query, beanPropertyRowMapper, email);
    }

}
