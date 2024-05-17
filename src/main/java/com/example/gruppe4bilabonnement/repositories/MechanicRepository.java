package com.example.gruppe4bilabonnement.repositories;

import com.example.gruppe4bilabonnement.models.DamageReport;
import com.example.gruppe4bilabonnement.services.rowmappers.DamageReportRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MechanicRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveDamageReport(DamageReport damageReport) {
        String sql = "INSERT INTO damage_report (car_id, damage_text, price) VALUES (?, ?, ?);";
        jdbcTemplate.update(sql, damageReport.getCarId(), damageReport.getDamageText(), damageReport.getPrice());
    }

    public List<DamageReport> getAllDamageReports() {
        String query = "SELECT * FROM damage_report;";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            DamageReport damageReport = new DamageReport();
            damageReport.setId(rs.getInt("id"));
            damageReport.setCarId(rs.getInt("car_id"));
            damageReport.setDamageText(rs.getString("damage_text"));
            damageReport.setPrice(rs.getDouble("price"));
            return damageReport;
        });
    }

    public DamageReport getDamageReportById(int id) {
        String query = "SELECT * FROM damage_report WHERE id = ?;";
        return jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
            DamageReport damageReport = new DamageReport();
            damageReport.setId(rs.getInt("id"));
            damageReport.setCarId(rs.getInt("car_id"));
            damageReport.setDamageText(rs.getString("damage_text"));
            damageReport.setPrice(rs.getDouble("price"));
            return damageReport;
        });
    }

    public void updateDamageReport(DamageReport damageReport) {
        String query = "UPDATE damage_report SET car_id = ?, damage_text = ?, price = ? WHERE id = ?;";
        jdbcTemplate.update(query, damageReport.getCarId(), damageReport.getDamageText(), damageReport.getPrice(), damageReport.getId());
    }

    public void deleteDamageReport(int id) {
        String query = "DELETE FROM damage_report WHERE id = ?;";
        jdbcTemplate.update(query, id);
    }

    public List<DamageReport> getAllDamageReportsByCarId(int carId) {
        String query = "SELECT * FROM damage_report WHERE car_id = ?;";
        return jdbcTemplate.query(query, new DamageReportRowMapper(), carId);
    }
}
