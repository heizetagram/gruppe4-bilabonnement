package com.example.gruppe4bilabonnement.services.rowmappers;

import com.example.gruppe4bilabonnement.models.enums.FuelType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FuelTypeRowMapper implements RowMapper<FuelType> {
    @Override
    public FuelType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FuelType.valueOf(rs.getString("fuel_type"));
    }
}
