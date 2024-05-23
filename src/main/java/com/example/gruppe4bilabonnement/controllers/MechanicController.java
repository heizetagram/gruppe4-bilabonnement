package com.example.gruppe4bilabonnement.controllers;

import com.example.gruppe4bilabonnement.models.DamageReport;
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

    @PostMapping("/damage_report")
    public String createDamageReport(@ModelAttribute DamageReport damageReport) {
        mechanicService.saveDamageReport(damageReport);
        return "redirect:/damage_reports";
    }

    @GetMapping("/mechanic_dashboard")
    public String showMechanicDashboard(@CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("MECHANIC")) {
            return "mechanic/mechanic_dashboard";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/damage_reports")
    public String getAllDamageReports(Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("MECHANIC")) {
            List<DamageReport> damageReports = mechanicService.getAllDamageReports();
            model.addAttribute("damageReports", damageReports);
            return "mechanic/damage_reports";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/damage_reports/{id}/delete")
    public String deleteDamageReport(@PathVariable("id") Long id) {
        mechanicService.deleteDamageReport(id);
        return "redirect:/damage_reports";
    }

    @PostMapping("/damage_reports/{id}/update")
    public String updateDamageReport(@PathVariable("id") Long id, @ModelAttribute DamageReport damageReport) {
        damageReport.setId(id);
        mechanicService.updateDamageReport(damageReport);
        return "redirect:/damage_reports";
    }

    @GetMapping("/damage_reports/{id}/edit")
    public String showEditDamageReportForm(@PathVariable("id") Long id, Model model, @CookieValue(name = "employeeRole") String cookieValue) {
        if (cookieValue.equals("MECHANIC")) {
            DamageReport damageReport = mechanicService.getDamageReportById(id);
            model.addAttribute("damageReport", damageReport);
            return "mechanic/edit_damage_report";
        } else {
            return "redirect:/";
        }
    }
}
