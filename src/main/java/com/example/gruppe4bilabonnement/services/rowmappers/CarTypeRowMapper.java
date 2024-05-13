package com.example.gruppe4bilabonnement.services.rowmappers;

import com.example.gruppe4bilabonnement.models.enums.CarType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarTypeRowMapper implements RowMapper<CarType> {
    @Override
    public CarType mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CarType.valueOf(rs.getString("car_type"));
    }
}
