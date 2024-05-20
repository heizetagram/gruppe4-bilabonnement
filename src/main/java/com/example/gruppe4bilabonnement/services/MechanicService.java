package com.example.gruppe4bilabonnement.services;

import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.models.Workshop;
import com.example.gruppe4bilabonnement.repositories.MechanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MechanicService {

    @Autowired
    private MechanicRepository mechanicRepository;
    @Autowired
    private CarService carService;

    public void saveDamageReport(DamageReport damageReport) {
        mechanicRepository.saveDamageReport(damageReport);
    }

    public List<DamageReport> getAllDamageReports() {
        return mechanicRepository.getAllDamageReports();
    }

    public DamageReport getDamageReportById(int id) {
        return mechanicRepository.getDamageReportById(id);
    }

    public List<DamageReport> getAllDamageReportsByCarId(int carId) {
        return mechanicRepository.getAllDamageReportsByCarId(carId);
    }

    public void updateDamageReport(DamageReport damageReport) {
        mechanicRepository.updateDamageReport(damageReport);
    }

    public void deleteDamageReport(int id) {
        mechanicRepository.deleteDamageReport(id);
    }

    public void addCarToWorkshop(int carId) {
        mechanicRepository.addCarToWorkshop(carId);
    }

    public boolean checkIfCarIsInService(int carId) {
        boolean isCarInService;
        try {
            mechanicRepository.getWorkshopByCarId(carId);
            isCarInService = true;
        } catch (EmptyResultDataAccessException e) {
            isCarInService = false;
        }
        return isCarInService;
    }

    public List<Workshop> getAllCarsInWorkshop() {
        return mechanicRepository.getAllCarsInWorkshop();
    }

    public void returnCar(int carId) {
        carService.makeCarAvailable(carId);
        mechanicRepository.removeCarFromWorkshop(carId);
    }
}
