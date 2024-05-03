package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class SalesPersonRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert(String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
        String query = "INSERT INTO customer(first_name, last_name, phone_number, email, address, zip_code)" +
                "VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(query, firstName, lastName, phoneNumber, email, address, zipCode);
    }

    public Customer getCustomer(int id) {
        String query = "SELECT * FROM movie WHERE id = ?;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return jdbcTemplate.queryForObject(query, rowMapper, id);
    }

    public void update(int id, String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
        String query = "UPDATE customer " +
                "SET id = ?," +
                "first_name = ?," +
                "last_name = ?," +
                "phone_number = ?, " +
                "email = ?, " +
                "address = ?, " +
                "zip_code = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(query, id, firstName, lastName, phoneNumber, email, address, zipCode, id);
    }
}
