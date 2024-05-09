package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.services.TestCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class TestCarController {
    @Autowired
    private TestCarService testCarService;

    @GetMapping("/prepare_new_car")
    public String prepareNewCar(@CookieValue(name = "employeeRole") String cookieValue, Model model) {
        if (cookieValue.equals("SALESPERSON")) {
            List<FuelType> fuelTypes = testCarService.getAllFuelTypes();
            List<CarBrand> carBrands = testCarService.getAllCarBrands();
            List<CarModel> carModels = testCarService.getAllCarModels();
            model.addAttribute("carBrands", carBrands);
            model.addAttribute("carModels", carModels);
            model.addAttribute("fuelTypes", fuelTypes);
            return "salesperson/create_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/select_car_brand")
    public String prepareNewCarBrand(Model model, @RequestParam String carBrand) {
        CarBrand carBrandSelected = new CarBrand(carBrand);
        model.addAttribute("carBrand", carBrandSelected);
        return "redirect:/salesperson/create_car?car_brand=" + carBrandSelected.getBrand();
    }

    @GetMapping("/show_car_models")
    public String showCarModels(Model model, @RequestParam String carBrand) {
        CarBrand carBrandSelected = new CarBrand(carBrand);
        List<CarModel> carModels = testCarService.getAllCarModelsByBrand(carBrandSelected);
        model.addAttribute("carModels", carModels);
        return "/salesperson/create_car?car_brand=" + carBrand;
    }


    @PostMapping("/create_car")
    public String createCar(@RequestParam CarBrand carBrand, @RequestParam String carModelName, @RequestParam CarType carType, @RequestParam FuelType fuelType,
                            @RequestParam String licensePlate, @RequestParam String vin, @RequestParam String equipmentLevel,
                            @RequestParam BigInteger km, @RequestParam double registrationFee, @RequestParam double steelPrice,
                            @RequestParam int co2Emission) {
        testCarService.createCar(carBrand, carModelName, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission);
        return "redirect:/salesperson/car_overview";
    }

    @GetMapping("/car_overview")
    public String showCars(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<Car> cars = testCarService.getAllCars();
            model.addAttribute("cars", cars);
            return "salesperson/car_overview";
        } else {
            return "redirect:/";
        }
    }
}
