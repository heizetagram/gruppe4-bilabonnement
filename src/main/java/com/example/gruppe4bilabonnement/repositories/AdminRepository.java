package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Employee> getAllEmployees() {
        String query = "SELECT * FROM employee;";
        return jdbcTemplate.query(query, beanPropertyRowMapper);
    }

    public Employee getEmployeeById(int id) {
        String query = "SELECT * FROM employee WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, beanPropertyRowMapper, id);
    }

    public void updateEmployee(int id, String email, String employeePassword, String employeeRole) {
        String query = "UPDATE employee SET email = ?, employee_password = ?, employee_role = ? WHERE id = ?;";
        jdbcTemplate.update(query, email, employeePassword, employeeRole, id);
    }

    public void deleteEmployeeById(int id) {
        String query ="DELETE FROM employee WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }
}
