package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.services.BusinessdeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class BusinessdeveloperController {

    @Autowired
    private BusinessdeveloperService businessdeveloperService;
        // Gennemsnitlig betalingstid
    @GetMapping("/average_payment_time")
    public String getAveragePaymentTime(Model model) {
        double averagePaymentTime = businessdeveloperService.getAveragePaymentTime();
        model.addAttribute("averagePaymentTime", averagePaymentTime);
        return "businessdeveloper/average_payment_time";
    }
        // gennemsnitlig transporttid(hvordan fuck gør jeg lige det, gps or some shit, samme med co2 etc)
    @GetMapping("/average_transport_time")
    public String getAverageTransportTime(Model model) {
        double averageTransportTime = businessdeveloperService.getAverageTransportTime();
        model.addAttribute("averageTransportTime", averageTransportTime);
        return "businessdeveloper/average_transport_time";
    }
        //gennemsnitlig Leasetid

    @GetMapping("/leasing_speed")
    public String getLeasingSpeedForCar(@RequestParam long carId, Model model) {
        double leasingSpeed = businessdeveloperService.getLeasingSpeedForCar(carId);
        model.addAttribute("carId", carId);
        model.addAttribute("leasingSpeed", leasingSpeed);
        return "businessdeveloper/leasing_speed";
    }


    //totale antal biler udlejet på givent tidspunkt
    @GetMapping("/rented_cars_count")
    public String getRentedCarsCount(Model model) {
        int rentedCarsCount = businessdeveloperService.getNumberOfRentedCars();
        model.addAttribute("rentedCarsCount", rentedCarsCount);
        return "businessdeveloper/rented_cars_count";
    }
        // Pris på udlejede biler på givent tidspunkt
    @GetMapping("/total_price_of_rented_cars")
    public String getTotalPriceOfRentedCars(Model model) {
        double totalPrice = businessdeveloperService.getTotalPriceOfRentedCars();
        model.addAttribute("totalPrice", totalPrice);
        return "businessdeveloper/total_price_of_rented_cars";
    }
    @GetMapping("/business_developer_dashboard")
    public String showBusinessDeveloperDashboard() {
        return "businessdeveloper/business_developer_dashboard";
    }
}
