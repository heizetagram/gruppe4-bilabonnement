package com.example.gruppe4bilabonnement;

import com.example.gruppe4bilabonnement.models.Customer;
import com.example.gruppe4bilabonnement.repositories.SalesPersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SalesPersonRepositoryIntegrationTest {

    @Autowired
    private SalesPersonRepository salesPersonRepository;

    @Test
    public void testGetCustomerById() {

        // Given (Arrange)
        String firstName = "Test";
        String lastName = "Customer";
        String phoneNumber = "12345678";
        String email = "test@example.com";
        String address = "Test Address";
        int zipCode = 1000;

        salesPersonRepository.insert(firstName, lastName, phoneNumber, email, address, zipCode);

        // When (Act)
        Customer customer = salesPersonRepository.getCustomerByEmail(email);
        Customer retrievedCustomer = salesPersonRepository.getCustomerById(customer.getId());

        // Then (Assert)
        assertNotNull(retrievedCustomer);
        assertEquals(firstName, retrievedCustomer.getFirstName());
        assertEquals(lastName, retrievedCustomer.getLastName());
        assertEquals(phoneNumber, retrievedCustomer.getPhoneNumber());
        assertEquals(email, retrievedCustomer.getEmail());
        assertEquals(address, retrievedCustomer.getAddress());
        assertEquals(zipCode, retrievedCustomer.getZipCode());

        // Clean up
        salesPersonRepository.deleteCustomerById(retrievedCustomer.getId());
    }
}
