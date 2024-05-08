package com.example.gruppe4bilabonnement.services.rowmappers;

import com.example.gruppe4bilabonnement.models.CarBrand;
import com.example.gruppe4bilabonnement.models.CarModel;
import com.example.gruppe4bilabonnement.models.CarType;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarModelRowMapper implements RowMapper<CarModel> {
    // Create a row mapper for car model, that sets the car brand and car types as instances of their classes
    public CarModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        CarBrand brand = new CarBrand(rs.getString("brand"));
        String modelName = rs.getString("model_name");
        CarType carType = new CarType(rs.getString("car_type"));
        return new CarModel(id, brand, modelName, carType);
    }
}
