package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SalesPersonRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
        String query = "INSERT INTO customer (first_name, last_name, phone_number, email, address, zip_code)" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, firstName, lastName, phoneNumber, email, address, zipCode);
    }

    public Customer getCustomer(int id) {
        String query = "SELECT * FROM customer WHERE id = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public void update(int id, String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
        String query = "UPDATE customer " +
                "SET first_name= ?," +
                "last_name = ?," +
                "phone_number = ?, " +
                "email = ?, " +
                "address = ?, " +
                "zip_code = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(query, firstName, lastName, phoneNumber, email, address, zipCode, id);
    }
    public List<Customer> getAllCustomers() {
        String query = "SELECT * FROM customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.query(query, rowMapper);
    }

    public Customer getCustomerByEmail(String email) {
        String query = "SELECT * FROM customer WHERE email = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.queryForObject(query, rowMapper, email);
    }

    public void deleteCustomerById(int id) {
        String query ="DELETE FROM customer WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }

    public Customer getCustomerById(int id) {
        String query = "SELECT * FROM customer WHERE id = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }
}
