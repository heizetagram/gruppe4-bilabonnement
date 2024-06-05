package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.LeaseAgreement;
import com.example.gruppe4bilabonnement.repositories.LeaseAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaseAgreementService {

    @Autowired
    private LeaseAgreementRepository leaseAgreementRepository;

    public void createLeaseAgreement(int customerId, LeaseAgreement leaseAgreement) {
        // Validate that the end date is after the start date
        validateLeaseDates(leaseAgreement);
        leaseAgreementRepository.createLeaseAgreement(customerId, leaseAgreement);
    }
    public LeaseAgreement getLeaseAgreementById(int id) {
        return leaseAgreementRepository.findById(id);
    }
    public List<LeaseAgreement> getAllLeaseAgreements() {
        return leaseAgreementRepository.findAll();
    }
    public void updateLeaseAgreement(LeaseAgreement leaseAgreement) {
        validateLeaseDates(leaseAgreement);
        leaseAgreementRepository.updateLeaseAgreement(leaseAgreement);
    }
    public void deleteLeaseAgreement(int id) {
        leaseAgreementRepository.deleteById(id);
    }

    public List<LeaseAgreement> getAllLeaseAgreementsByCustomerId(int customerId) {
        return leaseAgreementRepository.getAllLeaseAgreementsByCustomerId(customerId);
    }
    // Method to ensure the end date is after the start date
    private void validateLeaseDates(LeaseAgreement leaseAgreement) {
        if (leaseAgreement.getEndDate().isBefore(leaseAgreement.getStartDate())) {
            throw new IllegalArgumentException("Slutdato skal være efter startdato");
        }
    }
}
