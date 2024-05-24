package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.models.ZipCode;
import com.example.gruppe4bilabonnement.repositories.SalesPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesPersonService {
    @Autowired
    SalesPersonRepository salesPersonRepository;

    public void insert(String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
        salesPersonRepository.insert(firstName, lastName, phoneNumber, email, address, zipCode);
    }

    // Checks if zipcode exits in the databse
    public boolean isZipCodeValid(int zipCode) {
        boolean isValid;
        try {
            salesPersonRepository.getZipCodeByZipCode(zipCode);
            isValid = true;
        } catch (EmptyResultDataAccessException e) {
            isValid = false;
        }
        return isValid;
    }

    public Customer prepareUpdate(int id) {
        return salesPersonRepository.getCustomer(id);
    }

    public void update(int id, String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
        salesPersonRepository.update(id, firstName, lastName, phoneNumber, email, address, zipCode);
    }

    public List<Customer> getAllCustomers() {
        return salesPersonRepository.getAllCustomers();
    }

    public boolean isEmailRegistered(String email) {
        boolean isEmailRegistered;
        try {
            salesPersonRepository.getCustomerByEmail(email);
            isEmailRegistered = true; // Email exists, return true
        } catch (EmptyResultDataAccessException e) {
            isEmailRegistered = false; // Email doesn't exist, return false
        }
        return isEmailRegistered;
    }

    public void deleteCustomerById(int id) {
    salesPersonRepository.deleteCustomerById(id);
    }

    public Customer getCustomerById(int id) {
       return salesPersonRepository.getCustomerById(id);
    }

    public Customer getCustomerByEmail(String email) {
        return salesPersonRepository.getCustomerByEmail(email);
    }

    public LeaseAgreement getLeaseAgreementByCustomerId(int customerId) {
        return salesPersonRepository.getLeaseAgreementCustomerId(customerId);
    }

    public LeaseAgreement getLeaseAgreementById(int leaseAgreementId) {
        return salesPersonRepository.getLeaseAgreementById(leaseAgreementId);
    }

    public ZipCode getZipCodeByZipCode(int zipCode) {
        return salesPersonRepository.getZipCodeByZipCode(zipCode);
    }
}
