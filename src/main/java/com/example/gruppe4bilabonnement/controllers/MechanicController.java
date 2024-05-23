package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.models.Workshop;
import com.example.gruppe4bilabonnement.services.CarService;
import com.example.gruppe4bilabonnement.services.MechanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mechanic")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private CarService carService;

    @GetMapping("/cars_in_workshop_overview")
    public String showCarsInWorkshop(Model model) {
        List<Workshop> carsInService = mechanicService.getAllCarsInWorkshop();

        model.addAttribute("carService", carService);
        model.addAttribute("carsInService", carsInService);
        return "mechanic/cars_in_workshop";
    }

    @PostMapping("/damage_report")
    public String createDamageReport(@ModelAttribute DamageReport damageReport) {
        mechanicService.saveDamageReport(damageReport);
        return "redirect:/mechanic/damage_reports/" + damageReport.getCarId();
    }

    @PostMapping("/return_car/{carId}")
    public String returnCar(@PathVariable("carId") int carId) {
        mechanicService.returnCar(carId);
        return "redirect:/mechanic/cars_in_workshop_overview";
    }

    @GetMapping("/mechanic_dashboard/{carId}")
    public String showMechanicDashboard(@PathVariable("carId") int carId, Model model) {
        model.addAttribute("carId", carId);
        return "mechanic/mechanic_dashboard";
    }

    @GetMapping("/damage_reports/{carId}")
    public String getAllDamageReports(@PathVariable("carId") int carId, Model model) {
        List<DamageReport> damageReports = mechanicService.getAllDamageReportsByCarId(carId);
        //List<DamageReport> damageReports = mechanicService.getAllDamageReports();
        model.addAttribute("damageReports", damageReports);
        return "mechanic/damage_reports";
    }

    @PostMapping("/damage_reports/{id}/delete")
    public String deleteDamageReport(@PathVariable("id") int id) {
        mechanicService.deleteDamageReport(id);
        return "redirect:/mechanic/damage_reports";
    }

    @PostMapping("/damage_reports/{id}/update")
    public String updateDamageReport(@PathVariable("id") int id, @ModelAttribute DamageReport damageReport) {
        damageReport.setId(id);
        mechanicService.updateDamageReport(damageReport);
        return "redirect:/mechanic/cars_in_workshop_overview";
    }

    @GetMapping("/damage_reports/{id}/edit")
    public String showEditDamageReportForm(@PathVariable("id") int id, Model model) {
        DamageReport damageReport = mechanicService.getDamageReportById(id);
        model.addAttribute("damageReport", damageReport);
        return "mechanic/edit_damage_report";
    }
}
