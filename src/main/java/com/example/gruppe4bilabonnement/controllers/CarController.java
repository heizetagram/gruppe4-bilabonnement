package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/salesperson")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/prepare_new_car")
    public String prepareNewCar(@CookieValue(name = "employeeRole") String cookieValue, Model model) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarBrand> carBrands = carService.getAllCarBrands();
            model.addAttribute("carBrands", carBrands);
            return "salesperson/car/create_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/select_car_brand")
    public String selectCarBrand(Model model, @RequestParam CarBrand carBrand) {
        List<CarBrand> carBrands = carService.getAllCarBrands();
        List<CarModel> carModels = carService.getAllCarModelsByBrand(carBrand);
        List<FuelType> fuelTypes = carService.getAllFuelTypes();
        model.addAttribute("carBrands", carBrands);
        model.addAttribute("carModels", carModels);
        model.addAttribute("fuelTypes", fuelTypes);
        model.addAttribute("selectedCarBrand", carBrand);
        return "/salesperson/car/create_car";
    }

    @PostMapping("/create_car")
    public String createCar(@RequestParam int carModelId, @RequestParam FuelType fuelType,
                            @RequestParam String licensePlate, @RequestParam String vin, @RequestParam String equipmentLevel,
                            @RequestParam long km, @RequestParam double registrationFee, @RequestParam double steelPrice,
                            @RequestParam int co2Emission, @RequestParam String isRented) {
        carService.createCar(carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
        return "redirect:/salesperson/car_overview";
    }

    @GetMapping("/prepare_new_car_model")
    public String prepareNewCarModel(Model model, @RequestParam CarBrand carBrand, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarType> carTypes = carService.getAllCarTypes();
            model.addAttribute("carTypes", carTypes);
            model.addAttribute("selectedCarBrand", carBrand.getBrand());
            return "/salesperson/car/create_car_model";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/create_car_model")
    public String createNewCarModel(@RequestParam CarBrand carBrand, @RequestParam String carModelName, @RequestParam CarType carType) {
        carService.createCarModel(carBrand, carModelName, carType);
        return "redirect:/salesperson/prepare_new_car";
    }

    @GetMapping("/car_overview")
    public String showCars(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<Car> cars = carService.getAllCars();
            model.addAttribute("cars", cars);
            model.addAttribute("carService", carService);
            return "salesperson/car/car_overview";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/show_car")
    public String showCar(@RequestParam int carId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Car car = carService.getCarById(carId);
            CarModel carModel = carService.getCarModelById(carId);
            model.addAttribute("car", car);
            model.addAttribute("carModel", carModel);
            return "salesperson/car/show_car";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("prepare_car_update")
    public String prepareCarUpdate(@RequestParam int carId, @RequestParam int carModelId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Car car = carService.getCarById(carId);
            CarModel carModel = carService.getCarModelById(carModelId);

            List<CarModel> carModels = carService.getAllCarModelsByBrand(carModel.getBrand());
            List<FuelType> fuelTypes = carService.getAllFuelTypes();

            model.addAttribute("car", car);
            model.addAttribute("carService", carService);
            model.addAttribute("carModels", carModels);
            model.addAttribute("fuelTypes", fuelTypes);
            return "salesperson/car/update_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("update_car")
    public String updateCar(@RequestParam int carId, @RequestParam int carModelId, @RequestParam FuelType fuelType,
                            @RequestParam String licensePlate, @RequestParam String vin, @RequestParam String equipmentLevel,
                            @RequestParam long km, @RequestParam double registrationFee, @RequestParam double steelPrice,
                            @RequestParam int co2Emission) {
        carService.updateCar(carId, carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission);
        return "redirect:/salesperson/car_overview";
    }

    @GetMapping("prepare_car_deletion")
    public String prepareCarDeletion(@RequestParam int carId, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Car car = carService.getCarById(carId);
            model.addAttribute("car", car);
            model.addAttribute("carService", carService);
            return "salesperson/car/delete_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("delete_car")
    public String deleteCar(@RequestParam int carId) {
        carService.deleteCarById(carId);
        return "redirect:/salesperson/car_overview";
    }
}
