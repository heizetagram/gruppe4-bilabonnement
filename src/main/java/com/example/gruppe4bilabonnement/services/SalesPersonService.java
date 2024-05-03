package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.repositories.SalesPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesPersonService {
    @Autowired
    SalesPersonRepository salesPersonRepository;

    public void insert(String firstName, String lastName, String phoneNumber, String email, String address, int zipCode) {
    salesPersonRepository.insert(firstName, lastName, phoneNumber, email, address, zipCode);
    }

    public Customer prepareUpdate(int id) {
    return salesPersonRepository.getCustomer(id);
    }

    public void update(int id, String firstNamee, String lastName, String phoneNumber, String email, String address, int zipCode) {
    salesPersonRepository.update(id, firstNamee, lastName, phoneNumber, email, address, zipCode);
    }
}
