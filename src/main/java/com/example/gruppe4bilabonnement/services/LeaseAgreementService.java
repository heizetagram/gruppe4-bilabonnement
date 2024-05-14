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
        leaseAgreementRepository.createLeaseAgreement(customerId, leaseAgreement);
    }
    public LeaseAgreement getLeaseAgreementById(int id) {
        return leaseAgreementRepository.findById(id);
    }
    public List<LeaseAgreement> getAllLeaseAgreements() {
        return leaseAgreementRepository.findAll();
    }
    public void updateLeaseAgreement(LeaseAgreement leaseAgreement) {
        leaseAgreementRepository.updateLeaseAgreement(leaseAgreement);
    }
    public void deleteLeaseAgreement(int id) {
        leaseAgreementRepository.deleteById(id);
    }
}
