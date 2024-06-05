package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.repositories.BusinessdeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessdeveloperService {

    @Autowired
    private BusinessdeveloperRepository businessdeveloperRepository;
    // gennemsnitlg betaling
    public double getAveragePaymentTime() {
        return businessdeveloperRepository.calculateAveragePaymentTime();
    }
    // gennemsnitlig transport-tid
    public double getAverageTransportTime() {
        return businessdeveloperRepository.calculateAverageTransportTime();
    }
    // Hvor hurtigt bliver biler leaset.
   /* public double getLeasingSpeedForCar(int carId) {
        return businessdeveloperRepository.calculateLeasingSpeedForCar(carId);
    } */
    // hvor mange biler er udlejet
    public int getNumberOfRentedCars() {
        return businessdeveloperRepository.countRentedCars();
    }
    // total pris på udlejede biler
    public double getTotalPriceOfRentedCars() {
        return businessdeveloperRepository.calculateTotalPriceOfRentedCars();
    }
}
