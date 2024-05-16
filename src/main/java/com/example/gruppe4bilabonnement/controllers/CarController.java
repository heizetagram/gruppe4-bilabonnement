package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.*;
import com.example.gruppe4bilabonnement.models.enums.CarType;
import com.example.gruppe4bilabonnement.models.enums.FuelType;
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
                            @RequestParam int co2Emission, @RequestParam String isRented, Model model) {
        // IN PROGRESS
        /*boolean licensePlateExists = carService.checkIfLicensePlateExists(licensePlate);
        boolean vinExists = carService.checkIfVinExists(vin);

        if (licensePlateExists) {
            model.addAttribute("licensePlateExists", "Nummerplade er allerede registreret i systemet");
        } else if (vinExists) {
            model.addAttribute("vinExists", "VIN er allerede regisreret i systemet");
        } else {*/
            carService.createCar(carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission, isRented);
            return "redirect:/salesperson/car_overview";
        //}

        //return "salesperson/car/create_car";
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
    public String showCar(@RequestParam int carId, @RequestParam int carModelId, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Car car = carService.getCarById(carId);
            CarModel carModel = carService.getCarModelById(carModelId);
            model.addAttribute("car", car);
            model.addAttribute("carModel", carModel);
            if (origin.equals("overview")) {
                return "redirect:/salesperson/car_overview";
            } else {
                return "/salesperson/car/show_car";
            }
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/prepare_car_update")
    public String prepareCarUpdate(@RequestParam int carId, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Car car = carService.getCarById(carId);
            CarModel carModel = carService.getCarModelById(car.getCarModelId());

            List<CarModel> carModels = carService.getAllCarModelsByBrand(carModel.getBrand());
            List<FuelType> fuelTypes = carService.getAllFuelTypes();

            model.addAttribute("origin", origin);
            model.addAttribute("car", car);
            model.addAttribute("carModel", carModel);
            model.addAttribute("carModels", carModels);
            model.addAttribute("fuelTypes", fuelTypes);
            return "salesperson/car/update_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/update_car")
    public String updateCar(@RequestParam int carId, @RequestParam int carModelId, @RequestParam FuelType fuelType,
                            @RequestParam String licensePlate, @RequestParam String vin, @RequestParam String equipmentLevel,
                            @RequestParam long km, @RequestParam double registrationFee, @RequestParam double steelPrice,
                            @RequestParam int co2Emission, @RequestParam String origin) {
        carService.updateCar(carId, carModelId, fuelType, licensePlate, vin, equipmentLevel, km, registrationFee, steelPrice, co2Emission);
        if (origin.equals("overview")) {
            return "redirect:/salesperson/car_overview";
        } else {
            return "redirect:/salesperson/show_car" + "?origin=" + origin + "&carId=" + carId + "&carModelId=" + carModelId;
        }
    }

    @GetMapping("/prepare_car_deletion")
    public String prepareCarDeletion(@RequestParam int carId, @RequestParam String origin, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            Car car = carService.getCarById(carId);
            CarModel carModel = carService.getCarModelById(car.getCarModelId());

            model.addAttribute("origin", origin);
            model.addAttribute("car", car);
            model.addAttribute("carModel", carModel);
            return "salesperson/car/delete_car";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/delete_car")
    public String deleteCar(@RequestParam int carId, @RequestParam String origin) {
        carService.deleteCarById(carId);
        if (origin.equals("overview")) {
            return "redirect:/salesperson/car_overview";
        } else {
            return "/salesperson/car/show_car";
        }
    }

    ///////////////////////
    ////// CAR BRAND //////
    ///////////////////////

    // Prepare car brand CREATION
    @GetMapping("/prepare_new_car_brand")
    public String prepareNewCarBrand(@CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            return "salesperson/car/carbrand/create_car_brand";
        } else {
            return "redirect:/";
        }
    }

    // CREATE car brand
    @PostMapping("/create_car_brand")
    public String createCarBrand(@RequestParam CarBrand carBrand, Model model) {
        boolean carBrandExists = carService.checkIfCarBrandExists(carBrand);
        if (carBrandExists) {
            model.addAttribute("carBrandExists", "Bilmærke findes allerede");
            return "salesperson/car/carbrand/create_car_brand";
        } else {
            carService.createCarBrand(carBrand);
            return "redirect:/salesperson/car_brand_overview";
        }
    }

    // READ car brands
    @GetMapping("/car_brand_overview")
    public String showCarBrands(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarBrand> carBrands = carService.getAllCarBrands();
            model.addAttribute("carBrands", carBrands);
            return "salesperson/car/carbrand/car_brand_overview";
        } else {
            return "redirect:/";
        }
    }

    // Prepare car brand UPDATE
    @GetMapping("/prepare_car_brand_update")
    public String prepareCarBrandUpdate(@RequestParam CarBrand carBrand, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            model.addAttribute("carBrand", carBrand);
            return "salesperson/car/carbrand/update_car_brand";
        } else {
            return "redirect:/";
        }
    }

    // UPDATE car brand
    @PostMapping("/update_car_brand")
    public String updateCarBrand(@RequestParam CarBrand newCarBrand, @RequestParam CarBrand oldCarBrand, Model model) {
        boolean carBrandExists = carService.checkIfCarBrandExists(newCarBrand);

        if (carBrandExists) {
            model.addAttribute("carBrandExists", "Bilmærke findes allerede");
            model.addAttribute("carBrand", oldCarBrand);
            return "salesperson/car/carbrand/update_car_brand";
        } else {
            carService.updateCarBrand(newCarBrand, oldCarBrand);
            return "redirect:/salesperson/car_brand_overview";
        }
    }

    // Prepare car brand DELETION
    @GetMapping("/prepare_car_brand_deletion")
    public String prepareCarBrandDeletion(@RequestParam CarBrand carBrand, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            model.addAttribute("carBrand", carBrand);
            return "salesperson/car/carbrand/delete_car_brand";
        } else {
            return "redirect:/";
        }
    }

    // DELETE car brand
    @PostMapping("/delete_car_brand")
    public String deleteCarBrand(@RequestParam CarBrand carBrand, Model model) {
        boolean doesCarBrandHaveModels = carService.doesCarBrandHaveModels(carBrand);
        if (doesCarBrandHaveModels) {
            model.addAttribute("carBrandHasModelsError", "Bilmærket kunne ikke slettes, fordi at den har tilknyttet bilmodeller");
            model.addAttribute("carBrand", carBrand);
            return "salesperson/car/carbrand/delete_car_brand";
        } else {
            carService.deleteCarBrand(carBrand);
            return "redirect:/salesperson/car_brand_overview";
        }
    }

    ///////////////////////
    ////// CAR MODEL //////
    ///////////////////////

    // Prepare car model CREATION
    @GetMapping("/prepare_new_car_model")
    public String prepareNewCarModel(@RequestParam CarBrand carBrand, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarType> carTypes = carService.getAllCarTypes();
            model.addAttribute("carTypes", carTypes);
            model.addAttribute("carBrand", carBrand);
            return "/salesperson/car/carmodel/create_car_model";
        } else {
            return "redirect:/";
        }
    }

    // CREATE car model
    @PostMapping("/create_car_model")
    public String createNewCarModel(@RequestParam CarBrand carBrand, @RequestParam String carModelName, @RequestParam CarType carType, Model model) {
        boolean doesCarModelExist = carService.checkIfCarModelExists(carBrand, carModelName);
        if (doesCarModelExist) {
            model.addAttribute("carModelExists", "Modellen findes allerede til dette bilmærke");
            List<CarType> carTypes = carService.getAllCarTypes();
            model.addAttribute("carTypes", carTypes);
            model.addAttribute("carBrand", carBrand);
            return "salesperson/car/carmodel/create_car_model";
        } else {
            carService.createCarModel(carBrand, carModelName, carType);
            return "redirect:/salesperson/car_model_overview?carBrand=" + carBrand.getBrand();
        }
    }

    // READ car models
    @GetMapping("/car_model_overview")
    public String showCarModels(CarBrand carBrand, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            List<CarModel> carModels = carService.getAllCarModelsByBrand(carBrand);
            model.addAttribute("carBrand", carBrand);
            model.addAttribute("carModels", carModels);
            return "salesperson/car/carmodel/car_model_overview";
        } else {
            return "redirect:/";
        }
    }

    // Prepare car model UPDATE
    @GetMapping("/prepare_car_model_update")
    public String prepareCarModelUpdate(@RequestParam int carModelId, @RequestParam CarBrand carBrand, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("SALESPERSON")) {
            CarModel carModel = carService.getCarModelById(carModelId);
            List<CarType> carTypes = carService.getAllCarTypes();
            model.addAttribute("carModel", carModel);
            model.addAttribute("carTypes", carTypes);
            model.addAttribute("carBrand", carBrand);
            return "salesperson/car/carmodel/update_car_model";
        } else {
            return "redirect:/";
        }
    }

    // UPDATE car model
    @PostMapping("/update_car_model")
    public String updateCarModel(@RequestParam int carModelId, @RequestParam CarBrand carBrand, @RequestParam String modelName, @RequestParam CarType carType, Model model) {
        boolean doesCarModelExist = carService.checkIfCarModelExists(carBrand, modelName, carType);

        if (doesCarModelExist) {
            model.addAttribute("carModelExists", "Modellen findes allerede til dette bilmærke");
            model.addAttribute("carBrand", carBrand);
            CarModel carModel = carService.getCarModelById(carModelId);
            model.addAttribute("carModel", carModel);

            List<CarType> carTypes = carService.getAllCarTypes();
            model.addAttribute("carTypes", carTypes);
            return "salesperson/car/carmodel/update_car_model";
        } else {
            carService.updateCarModel(carModelId, modelName, carType);
            return "redirect:/salesperson/car_model_overview?carBrand=" + carBrand.getBrand();
        }
    }

    // Prepare car model DELETION
    @GetMapping("/prepare_car_model_deletion")
    public String prepareCarModelDeletion(@RequestParam int carModelId, @RequestParam CarBrand carBrand, Model model) {
        CarModel carModel = carService.getCarModelById(carModelId);
        model.addAttribute("carBrand", carBrand);
        model.addAttribute("carModel", carModel);
        return "salesperson/car/carmodel/delete_car_model";
    }

    // DELETE car model (NO ERROR YET FOR WHEN TRYING TO DELETE A CAR MODEL WHEN IT'S CONNECTED TO A LEASE AGREEMENT)
    @PostMapping("/delete_car_model")
    public String deleteCarModel(@RequestParam int carModelId, @RequestParam CarBrand carBrand) {
        carService.deleteCarModelById(carModelId);
        return "redirect:/salesperson/car_model_overview?carBrand=" + carBrand.getBrand();
    }
}
