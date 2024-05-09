package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.services.TestCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class TestCarController {
    @Autowired
    private TestCarService testCarService;

    @GetMapping("/prepare_new_car")
    public String prepareNewCar(@CookieValue(name = "employeeRole") String cookieValue, Model model) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarBrand> carBrands = testCarService.getAllCarBrands();
            model.addAttribute("carBrands", carBrands);
            return "salesperson/create_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/select_car_brand")
    public String selectCarBrand(Model model, @RequestParam CarBrand carBrand) {
        List<CarBrand> carBrands = testCarService.getAllCarBrands();
        List<CarModel> carModels = testCarService.getAllCarModelsByBrand(carBrand);
        List<FuelType> fuelTypes = testCarService.getAllFuelTypes();
        model.addAttribute("carBrands", carBrands);
        model.addAttribute("carModels", carModels);
        model.addAttribute("fuelTypes", fuelTypes);
        model.addAttribute("selectedCarBrand", carBrand);
        return "/salesperson/create_car";
    }

    @PostMapping("/create_car")
    public String createCar(@RequestParam int carModelId, @RequestParam FuelType fuelType,
                            @RequestParam String licensePlate, @RequestParam String vin, @RequestParam String equipmentLevel,
                            @RequestParam long km, @RequestParam double registrationFee, @RequestParam double steelPrice,
                            @RequestParam int co2Emission, @RequestParam String isRented) {
        testCarService.createCar(carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
        return "redirect:/salesperson/car_overview";
    }

    @GetMapping("/prepare_new_car_model")
    public String prepareNewCarModel(Model model, @RequestParam CarBrand carBrand, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarType> carTypes = testCarService.getAllCarTypes();
            model.addAttribute("carTypes", carTypes);
            model.addAttribute("selectedCarBrand", carBrand.getBrand());
            return "/salesperson/create_car_model";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/create_car_model")
    public String createNewCarModel(@RequestParam CarBrand carBrand, @RequestParam String carModelName, @RequestParam CarType carType) {
        testCarService.createCarModel(carBrand, carModelName, carType);
        return "redirect:/salesperson/prepare_new_car";
    }

    @GetMapping("/car_overview")
    public String showCars(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<Car> cars = testCarService.getAllCars();
            model.addAttribute("cars", cars);
            model.addAttribute("testCarService", testCarService);
            return "salesperson/car_overview";
        } else {
            return "redirect:/";
        }
    }
}
