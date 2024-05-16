package com.example.gruppe4bilabonnement.services.rowmappers;

import com.example.gruppe4bilabonnement.models.*;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DamageReportRowMapper implements RowMapper<DamageReport> {
    @Override
    public DamageReport mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        int carId = rs.getInt("car_id");
        String damageText = rs.getString("damage_text");
        double price = rs.getDouble("price");
        return new DamageReport(id, carId, damageText, price);
    }
}
